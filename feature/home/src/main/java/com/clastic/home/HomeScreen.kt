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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberSmartRecord
import androidx.compose.runtime.Composable
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
import androidx.core.graphics.toColorInt
import com.clastic.home.component.BottomBar
import com.clastic.home.component.PlasticExchange
import com.clastic.home.component.PlasticMission
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.ui.theme.CyanPrimaryVariant
import com.clastic.ui.theme.CyanPrimaryVariant2

@Composable
fun HomeScreen(
    navigateToHome: () -> Unit,
    navigateToArticle: () -> Unit,
    navigateToStore: () -> Unit,
    navigateToMission: () -> Unit,
    navigateToProfile: () -> Unit,
    onClick: (String) -> Unit,
    navigateToDropPointMap: () -> Unit,
    navigateToQrCode: () -> Unit,
    navigateToQrCodeScanner: () -> Unit,
    navigateToTutorial: () -> Unit,
    onMissionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            BottomBar(
                currentMenu = "Home",
                navigateToHome = navigateToHome,
                navigateToArticle = navigateToArticle,
                navigateToStore = navigateToStore,
                navigateToMission = navigateToMission,
                navigateToProfile = navigateToProfile
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_notification_white),
                        tint = Color.White,
                        contentDescription = null
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.clastic_logo_white),
                        contentDescription = null,
                        modifier = Modifier
                            .width(147.dp)
                            .height(30.dp)
                            .padding(horizontal = 16.dp)
                    )
                    Text(
                        text = stringResource(R.string.hi_user, "Warren"),
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
                            .background(Color.White)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FiberSmartRecord,
                                tint = CyanPrimaryVariant,
                                contentDescription = null
                            )
                            Text(
                                text = /* TODO : Sync Username*/ "0 pts",
                                style = MaterialTheme.typography.subtitle1.copy(
                                    color = CyanPrimaryVariant,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Text(
                                text = "Want to get more points?",
                                style = MaterialTheme.typography.subtitle1.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.clickable{ navigateToTutorial() }
                            )
                        }
                    }
                }
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .height(560.dp)
                            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                            .background(Color.White)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.verticalScroll(rememberScrollState())
                        ) {
                            PlasticExchange(
                                role = "User",
                                navigateToDropPointMap = navigateToDropPointMap,
                                navigateToQrCode = navigateToQrCode,
                                navigateToQrCodeScanner = navigateToQrCodeScanner
                            )

                            Spacer(
                                modifier = Modifier
                                    .height(8.dp)
                                    .fillMaxWidth()
                                    .background(Color.White)
                                    .alpha(0.6f)
                            )
                            PlasticMission(onMissionClick = onMissionClick)

                            Spacer(
                                modifier = Modifier
                                    .height(8.dp)
                                    .fillMaxWidth()
                                    .background(Color.White)
                                    .alpha(0.6f)
                            )

                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Text(
                                        text = "Types of Plastics",
                                        style = MaterialTheme.typography.h5.copy(color = Color.Black)
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_trash),
                                        contentDescription = null,
                                        tint = Color.Gray
                                    )
                                }
                                Text(
                                    text = "Get to know each plastic types!",
                                    style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
                                )

//                                LazyRow(
//                                    state = rememberLazyListState(),
//                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
//                                ) {
//                                    val listColor = listOf(
//                                        Color("#8DCF92".toColorInt()),
//                                        Color("#4D9E3F".toColorInt()),
//                                        Color("#D17021".toColorInt()),
//                                        Color("#47ACD8".toColorInt()),
//                                        Color("#0387B8".toColorInt()),
//                                        Color("#614E9D".toColorInt()),
//                                        Color("#707176".toColorInt()),
//                                    )
//                                    val listIcon = listOf(
//                                        R.drawable.logo_bottle_pet,
//                                        R.drawable.icon_hdpe,
//                                        R.drawable.icon_pp,
//                                        R.drawable.icon_pvc,
//                                        R.drawable.icon_ldpe,
//                                        R.drawable.icon_ps,
//                                        R.drawable.icon_other,
//                                    )
//                                    items(
//                                        items = ProductData.plasticTypes,
//                                        key = { it.tag }) { plasticType ->
//                                        val colorIndex = ProductData.plasticTypes.indexOf(plasticType) %listColor.size
//                                        ProductKnowledgeComponent(
//                                            onClick = onClick,
//                                            plasticType = plasticType,
//                                            backgroundColor = listColor[colorIndex],
//                                            iconId = listIcon[colorIndex]
//                                        )
//                                    }
//                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ClasticTheme {
        HomeScreen(
            onClick = {},
            navigateToDropPointMap = {},
            navigateToQrCode = {},
            navigateToQrCodeScanner = {},
            navigateToTutorial = {},
            onMissionClick = {},
            navigateToHome = {},
            navigateToArticle = {},
            navigateToStore = {},
            navigateToMission = {},
            navigateToProfile = {},
        )
    }
}