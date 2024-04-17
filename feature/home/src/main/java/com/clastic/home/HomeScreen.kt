package com.clastic.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberSmartRecord
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.home.component.PlasticExchange
import com.clastic.home.component.PlasticKnowledgeComponent
import com.clastic.home.component.PlasticMission
import com.clastic.model.PlasticKnowledge
import com.clastic.model.User
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.ui.theme.CyanPrimaryVariant
import com.clastic.ui.theme.CyanPrimaryVariant2

@Composable
fun HomeScreen(
    onPlasticTypeClicked: (String) -> Unit,
    navigateToDropPointMap: () -> Unit,
    navigateToQrCode: () -> Unit,
    navigateToQrCodeScanner: () -> Unit,
    navigateToTutorial: () -> Unit,
    onMissionClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
) {
    val user by viewModel.user.collectAsState()
    val listPlasticKnowledge by viewModel.listPlasticKnowledge.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    HomeScreenContent(
        user = user,
        isLoading = isLoading,
        listPlasticKnowledge = listPlasticKnowledge,
        onPlasticTypeClicked = onPlasticTypeClicked,
        navigateToDropPointMap = navigateToDropPointMap,
        navigateToQrCode = navigateToQrCode,
        navigateToQrCodeScanner = navigateToQrCodeScanner,
        navigateToTutorial = navigateToTutorial,
        onMissionClick = onMissionClick,
        modifier = modifier
    )
}

@Composable
fun HomeScreenContent(
    user: User,
    isLoading: Boolean,
    listPlasticKnowledge: List<PlasticKnowledge>,
    onPlasticTypeClicked: (String) -> Unit,
    navigateToDropPointMap: () -> Unit,
    navigateToQrCode: () -> Unit,
    navigateToQrCodeScanner: () -> Unit,
    navigateToTutorial: () -> Unit,
    onMissionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        CyanPrimaryVariant2,
                        CyanPrimary
                    ),
                    startX = 0F,
                    endX = 800F
                )
            )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ) {
            Box(
                modifier = modifier.padding(horizontal = 2.dp, vertical = 4.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp), contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notification_white),
                        tint = Color.White,
                        contentDescription = null
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.clastic_logo_white),
                        contentDescription = null,
                        modifier = Modifier
                            .width(147.dp)
                            .height(30.dp)
                            .padding(horizontal = 16.dp)
                    )
                    Text(
                        text = stringResource(R.string.hi_user, user.username ?: ""),
                        style = MaterialTheme.typography.h5.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(color = Color.White)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(4.dp),
                        ) {
                            Icon(
                                modifier = Modifier,
                                imageVector = Icons.Default.FiberSmartRecord,
                                tint = CyanPrimaryVariant,
                                contentDescription = null
                            )
                            Text(
                                text = stringResource(R.string.user_points, user.points),
                                style = MaterialTheme.typography.subtitle1.copy(
                                    color = CyanPrimaryVariant,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.align(
                                Alignment.Center
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.want_more_points),
                                style = MaterialTheme.typography.subtitle1.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.clickable{ navigateToTutorial() }
                            )
                        }
                    }
                }
            }
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .height(560.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(color = Color.White)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    PlasticExchange(
                        role = user.role,
                        modifier = modifier,
                        navigateToQrCode = navigateToQrCode,
                        navigateToDropPointMap = navigateToDropPointMap,
                        navigateToQrCodeScanner = navigateToQrCodeScanner
                    )

                    Spacer(
                        modifier = Modifier
                            .height(8.dp)
                            .fillMaxWidth()
                            .background(Color.LightGray)
                            .alpha(0.6f)
                    )

                    PlasticMission(modifier = modifier, onMissionClick = onMissionClick)

                    Spacer(
                        modifier = Modifier
                            .height(8.dp)
                            .fillMaxWidth()
                            .background(color = Color.LightGray)
                            .alpha(0.6f)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                        ) {
                            Text(
                                text = stringResource(R.string.types_of_plastic),
                                style = MaterialTheme.typography.h5.copy(color = Color.Black)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_trash),
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                        Text(
                            text = stringResource(R.string.get_to_know_types_of_plastic),
                            style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
                        )
                        LazyRow(
                            state = rememberLazyListState(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(
                                items = listPlasticKnowledge,
                                key = { it.tag }
                            ) { plasticType ->
                                PlasticKnowledgeComponent(
                                    onTypeClicked = onPlasticTypeClicked,
                                    plasticType = plasticType
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ClasticTheme {
        HomeScreenContent(
            user = User(),
            listPlasticKnowledge = emptyList(),
            onPlasticTypeClicked = {},
            navigateToDropPointMap = {},
            navigateToQrCode = {},
            navigateToQrCodeScanner = {},
            navigateToTutorial = {},
            onMissionClick = {},
            isLoading = true
        )
    }
}