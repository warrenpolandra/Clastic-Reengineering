package com.clastic.ui

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.clastic.model.Impact
import com.clastic.model.Mission
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.utils.TimeUtil

@Composable
fun MissionCard(
    mission: Mission,
    onMissionCLick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onMissionCLick(mission.id) },
        elevation = 10.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {
                    AsyncImage(
                        model = mission.imageUrl,
                        contentDescription = mission.title,
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.colorMatrix(
                            ColorMatrix().apply {
                                setToScale(
                                    0.8f,
                                    0.8f,
                                    0.8f,
                                    1f
                                )
                            }
                        ),
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        contentAlignment = Alignment.BottomStart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .padding(18.dp)
                    ) {
                        Text(
                            text = mission.title,
                            style = MaterialTheme.typography.h5.copy(color = Color.White),
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.ExtraBold,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp), contentAlignment = Alignment.TopStart
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            RecycleTag(modifier = Modifier, tag = mission.tags)
                            PointTag(modifier = Modifier, point = mission.reward)
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = stringResource(R.string.mission_days_left, TimeUtil.getRemainingDays(mission.endDate)),
                                style = MaterialTheme.typography.subtitle1.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                ),
                                textAlign = TextAlign.End,
                                modifier = Modifier
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(CyanPrimary),
                ) {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 4.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = stringResource(R.string.do_mission),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.h5.copy(
                                color = Color.White
                            )
                        )
                        Box(
                            modifier = modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_forward_white),
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun MissionCardPreview() {
    ClasticTheme{
        MissionCard(
            mission = Mission(
                id = "JBADLKBDLKABDA",
                title = "Plastic bags diet!",
                description = "Welcome to this mission! In this mission, you have to stop using plastic bags because of its pollution, harmful, high carbon, and other negative impacts!",
                objectives = listOf(
                    "Use reusable bags when buying foods, items, etc.",
                    "Recycle your plastic bags collection if any at home."
                ),
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/clastic-rebuild.appspot.com/o/Mission%2FMission%201%2Fcover.jpg?alt=media&token=d0497786-92b1-4043-8220-72c0a60e3d97",
                tags = listOf("HDPEM"),
                reward = 200,
                impacts = listOf(
                    Impact(
                        numberValue = 12,
                        description = "Baking 11 frozen pizzas",
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/clastic-rebuild.appspot.com/o/Mission%2FMission%201%2Fimpact%201.jpg?alt=media&token=154ca51f-1c52-45e5-af25-d63441222730"
                    ),
                    Impact(
                        numberValue = 12,
                        description = "Charging 1.947 AA batteries",
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/clastic-rebuild.appspot.com/o/Mission%2FMission%201%2Fimpact%202.jpg?alt=media&token=1861864c-2bb9-4d38-b38d-1b2fab9cf2f8"
                    ),
                    Impact(
                        numberValue = 144,
                        description = "Watching 62 hours of TV",
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/clastic-rebuild.appspot.com/o/Mission%2FMission%201%2Fimpact%203.jpg?alt=media&token=569bbad3-8002-4877-af98-927d46f457ec"
                    ),
                    Impact(
                        numberValue = 166,
                        description = "Keeping your refrigerator cold for 4 days",
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/clastic-rebuild.appspot.com/o/Mission%2FMission%201%2Fimpact%204.jpg?alt=media&token=98e8f4d3-334e-40db-9fa2-31eb387e4c19"
                    )
                ),
                endDate = 1724771457
            ),
            onMissionCLick = {}
        )
    }
}