package com.clastic.transaction.plastic.detail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.model.transaction.plastic.PlasticTransaction
import com.clastic.model.transaction.plastic.PlasticTransactionItem
import com.clastic.transaction.plastic.R
import com.clastic.transaction.plastic.component.PlasticTransactionDetailItem
import com.clastic.transaction.plastic.component.PlasticTransactionResultItem
import com.clastic.ui.ClasticTopAppBar
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.ui.theme.CyanPrimaryVariant
import com.clastic.utils.NumberUtil
import com.clastic.utils.TimeUtil

@Composable
fun PlasticTransactionDetailScreen(
    plasticTransactionId: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: PlasticTransactionDetailViewModel = hiltViewModel<PlasticTransactionDetailViewModel>()
    val user by viewModel.user.collectAsState()
    val plasticTransaction by viewModel.plasticTransaction.collectAsState()
    val dropPoint by viewModel.dropPoint.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current
    
    LaunchedEffect(true) {
        viewModel.fetchPlasticTransaction(
            plasticTransactionId = plasticTransactionId,
            onFetchFailed ={ showToast(context, it) }
        )
    }

    PlasticTransactionDetailScreenContent(
        plasticTransaction = plasticTransaction,
        isLoading = isLoading,
        username = user.username ?: "",
        dropPointName = dropPoint.name,
        onBackPressed = onBackPressed,
        modifier = modifier
    )
}

@Composable
private fun PlasticTransactionDetailScreenContent(
    plasticTransaction: PlasticTransaction,
    isLoading: Boolean,
    username: String,
    dropPointName: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ClasticTopAppBar(
                title = stringResource(R.string.plastic_transaction_detail),
                onBackPressed = onBackPressed
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color.White)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(horizontal = 12.dp, vertical = 20.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.transaction_created),
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Icon(
                            imageVector = Icons.Default.Verified,
                            tint = CyanPrimary,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(vertical = 40.dp)
                                .size(150.dp)
                        )
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.transaction_info),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                            PlasticTransactionDetailItem(
                                fieldName = stringResource(R.string.transaction_code),
                                fieldValue = plasticTransaction.id
                            )
                            PlasticTransactionDetailItem(
                                fieldName = stringResource(R.string.receiver),
                                fieldValue = username
                            )
                            PlasticTransactionDetailItem(
                                fieldName = stringResource(R.string.location),
                                fieldValue = dropPointName
                            )
                            PlasticTransactionDetailItem(
                                fieldName = stringResource(R.string.time),
                                fieldValue = TimeUtil.timestampToStringFormat(plasticTransaction.date)
                            )
                            Divider(
                                color = CyanPrimaryVariant,
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .height(2.dp)
                            )
                            Text(
                                text = stringResource(R.string.plastic_transaction_detail),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = CyanPrimary
                            )
                            plasticTransaction.plasticList.forEach { (plasticType, weight, points) ->
                                PlasticTransactionResultItem(
                                    plasticType = plasticType,
                                    weight = weight,
                                    points = points
                                )
                            }
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        ) {
                            Text(
                                text = stringResource(
                                    R.string.total_points_pts,
                                    NumberUtil.formatNumberToGrouped(plasticTransaction.totalPoints)

                                ),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = CyanPrimary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 20.dp)
                            )
                            Button(
                                onClick = onBackPressed,
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = CyanPrimary),
                                modifier = Modifier.heightIn(min = 48.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.back_to_home),
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
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
private fun PlasticTransactionDetailScreenPreview() {
    ClasticTheme {
        PlasticTransactionDetailScreenContent(
            plasticTransaction = PlasticTransaction(
                id = "AKDNPIAUBFOAIBAI",
                date = TimeUtil.getTimestamp(),
                dropPointId = "SADADAFAFA",
                ownerId = "sadjanbfipuabs",
                userId = "ishpfiug9aasd",
                plasticList = listOf(PlasticTransactionItem()),
                totalPoints = 20000
            ),
            isLoading = true,
            username = "Warren",
            dropPointName = "RPTRA Dahlia",
            onBackPressed = {}
        )
    }
}