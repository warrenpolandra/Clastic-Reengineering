package com.clastic.transaction.plastic

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberSmartRecord
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.model.transaction.plastic.PlasticTransactionItem
import com.clastic.transaction.plastic.component.AddRemoveButton
import com.clastic.transaction.plastic.component.PlasticInput
import com.clastic.transaction.plastic.component.TransactionTextField
import com.clastic.ui.ClasticTopAppBar
import com.clastic.ui.SubmitTransactionButton
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimaryVariant
import com.clastic.ui.theme.CyanTextField
import com.clastic.utils.NumberUtil
import com.clastic.utils.TimeUtil

@Composable
fun PlasticTransactionScreen(
    userId: String,
    navigateToHome: () -> Unit,
    navigateToPlasticTransactionDetail: (plasticTransactionId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: PlasticTransactionViewModel = hiltViewModel<PlasticTransactionViewModel>()
    val user by viewModel.user.collectAsState()
    val currentDate by viewModel.currentDate.collectAsState()
    val points by viewModel.points.collectAsState()
    val dropPoint by viewModel.dropPointName.collectAsState()
    val plasticTypeList by viewModel.plasticTypeList.collectAsState()
    val plasticTransactionItemList by viewModel.plasticTransactionItemList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) { viewModel.getUserById(userId) }

    PlasticTransactionScreenContent(
        username = user.username ?: "",
        currentDate = TimeUtil.dateToStringFormat(currentDate),
        dropPointName = dropPoint.name,
        points = points,
        plasticTypeList = plasticTypeList,
        plasticTransactionItemList = plasticTransactionItemList,
        isLoading = isLoading,
        addPlasticRow = { viewModel.addNewTransactionItem() },
        removePlasticRow = { viewModel.removeLastTransactionItem() },
        onValueChanged = { id, newWeight, newPoints ->
            viewModel.changeItemValueAtIndex(id, newWeight, newPoints)
        },
        onTypeSelected = { id, plasticType ->
            viewModel.changeItemTypeAtIndex(id, plasticType)
        },
        onSubmitClick = { viewModel.submitTransaction(
            onPostSuccess = navigateToPlasticTransactionDetail,
            onPostFailed = { error -> showToast(context, error) }
        )},
        navigateToHome = navigateToHome,
        modifier = modifier
    )
}

@Composable
private fun PlasticTransactionScreenContent(
    username: String,
    currentDate: String,
    dropPointName: String,
    points: Int,
    plasticTypeList: List<String>,
    plasticTransactionItemList: List<PlasticTransactionItem>,
    isLoading: Boolean,
    addPlasticRow: () -> Unit,
    removePlasticRow: () -> Unit,
    onValueChanged: (id: Int, weight: Float, points: Int) -> Unit,
    onTypeSelected: (id: Int, plasticType: String) -> Unit,
    onSubmitClick: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ClasticTopAppBar(
                title = stringResource(R.string.plastic_transaction),
                onBackPressed = navigateToHome
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(24.dp)
            ) {
                item {
                    TransactionTextField(
                        text = username,
                        labelId = R.string.user_name,
                        modifier = Modifier.padding(
                            top = 20.dp,
                            bottom = 10.dp
                        )
                    )
                }
                item {
                    TransactionTextField(
                        text = currentDate,
                        labelId = R.string.transaction_time,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
                item {
                    TransactionTextField(
                        text = dropPointName,
                        labelId = R.string.transaction_location,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
                item {
                    Text(
                        text = stringResource(R.string.plastic_transaction_detail),
                        color = CyanTextField,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
                item {
                    plasticTransactionItemList.forEachIndexed { index, _ ->
                        PlasticInput(
                            plasticTypeList = plasticTypeList,
                            onValueChanged = { weight, points ->
                                onValueChanged(index, weight, points)
                            },
                            onTypeSelected = { plasticType -> onTypeSelected(index, plasticType) }
                        )
                    }
                }
                item {
                    AddRemoveButton(
                        isEnabled = !isLoading,
                        onAdd = addPlasticRow,
                        onRemove = removePlasticRow,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
                item {
                    Text(
                        text = stringResource(R.string.total_points_obtained),
                        fontWeight = FontWeight.Medium,
                        fontSize = 25.sp,
                        color = CyanPrimaryVariant
                    )
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.FiberSmartRecord,
                            contentDescription = null,
                            tint = CyanPrimaryVariant,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = stringResource(R.string.amount_pts, NumberUtil.formatNumberToGrouped(points)),
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            color = CyanPrimaryVariant
                        )
                    }
                }
                item {
                    SubmitTransactionButton(
                        onClick = onSubmitClick,
                        isEnabled = !isLoading,
                        modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(64.dp)
                )
            }
        }
    }
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview
@Composable
private fun PlasticTransactionScreenPreview() {
    ClasticTheme {
        PlasticTransactionScreenContent(
            username = "Warren",
            currentDate = "Kamis, 18 April 2024",
            dropPointName = "RPTRA Dahlia",
            points = 7000,
            plasticTypeList = emptyList(),
            plasticTransactionItemList = emptyList(),
            isLoading = false,
            addPlasticRow = {},
            removePlasticRow = {},
            onValueChanged = { _, _, _ -> },
            onTypeSelected = { _, _ -> },
            onSubmitClick = {},
            navigateToHome = {}
        )
    }
}