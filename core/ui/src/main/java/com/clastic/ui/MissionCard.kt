package com.clastic.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.clastic.model.Impact
import com.clastic.model.Mission
import com.clastic.ui.theme.ClasticTheme

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
            .clickable { onMissionCLick(mission.title) },
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
                    Image(
                        painter = painterResource(mission.image),
                        contentDescription = null,
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
                        )
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
                            RecycleTag(modifier = Modifier, tag = mission.tag)
                            PointTag(modifier = Modifier, point = mission.reward)
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "20 Days Left",
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
                        .background(color = Color("#0097B2".toColorInt())),
                ) {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 4.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Jalankan Misi",
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

@Preview(showBackground = true)
@Composable
fun MissionCardPreview() {
    ClasticTheme {

    }
}