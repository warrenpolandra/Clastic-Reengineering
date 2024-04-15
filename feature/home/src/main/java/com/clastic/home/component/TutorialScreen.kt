package com.clastic.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clastic.home.R
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.ui.theme.CyanPrimaryVariant

@Composable
fun TutorialScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(
                        RoundedCornerShape(
                            bottomEnd = 20.dp,
                            bottomStart = 20.dp
                        )
                    )
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                CyanPrimary,
                                CyanPrimaryVariant
                            ),
                            startY = 0F,
                            endY = 600F
                        )
                    )
            ) {
                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(color = Color.White)
                    ) {
                        Text(
                            text = stringResource(R.string.how_to_get_more_points),
                            style = MaterialTheme.typography.subtitle1.copy(CyanPrimary),
                            modifier = Modifier.padding(
                                horizontal = 12.dp,
                                vertical = 4.dp
                            ),
                        )
                    }
                }
                Box(
                    modifier = modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .padding(12.dp), contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        text = stringResource(R.string.contributing_to_earth),
                        style = MaterialTheme.typography.h5.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.exchange_your_plastic),
                            color = Color.Black,
                            style = MaterialTheme.typography.h5
                        )
                    }
                    Text(
                        text = stringResource(R.string.exchange_your_plastic_and_get_benefit),
                        style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BenefitCard(
                            benefitText = stringResource(R.string.increase_life_quality),
                            cardNumber = 1
                        )
                        BenefitCard(
                            benefitText = stringResource(R.string.get_more_income),
                            cardNumber = 2
                        )
                        BenefitCard(
                            benefitText = stringResource(R.string.increase_environment_health),
                            cardNumber = 3
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth()
                    .background(color = Color.LightGray)
                    .alpha(0.6f)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Text(
                    text = stringResource(R.string.exchange_plastic_steps),
                    color = Color.Black,
                    style = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column {
                    ExchangeStep(
                        stepNumber = 1,
                        stepText = stringResource(R.string.tutorial_step_1)
                    )
                    ExchangeStep(
                        stepNumber = 2,
                        stepText = stringResource(R.string.tutorial_step_2)
                    )
                    ExchangeStep(
                        stepNumber = 3,
                        stepText = stringResource(R.string.tutorial_step_3)
                    )
                    ExchangeStep(
                        stepNumber = 4,
                        stepText = stringResource(R.string.tutorial_step_4)
                    )
                    ExchangeStep(
                        stepNumber = 5,
                        stepText = stringResource(R.string.tutorial_step_5)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TutorialScreenPreview() {
    ClasticTheme {
        TutorialScreen()
    }
}