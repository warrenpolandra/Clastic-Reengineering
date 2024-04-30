package com.clastic.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.FiberSmartRecord
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.clastic.model.User
import com.clastic.profile.R
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.ui.theme.CyanPrimaryVariant2
import com.clastic.ui.theme.CyanTextField
import com.clastic.utils.NumberUtil

@Composable
internal fun ProfileCard(
    user: User,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        CyanTextField,
                        CyanPrimary
                    )
                )
            )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .size(100.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                if (user.userPhoto?.isNotEmpty() == true) {
                    AsyncImage(
                        model = user.userPhoto,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.profile_picture),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Text(
                text = user.username ?: "",
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp
            )
            Text(
                text = user.email,
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            PointsButton(
                points = user.points,
                modifier = Modifier.padding(bottom = 15.dp)
            )
        }
    }
}

@Composable
private fun PointsButton(
    points: Int,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
                  /*TODO*/
        },
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White
        ),
        contentPadding = PaddingValues(
            horizontal = 10.dp
        ),
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.FiberSmartRecord,
            tint = CyanPrimaryVariant2,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 10.dp)
                .size(30.dp)
        )
        Text(
            text = stringResource(R.string.points_total, NumberUtil.formatNumberToGrouped(points)),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = CyanPrimaryVariant2,
            modifier = Modifier.padding(end = 10.dp)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = CyanPrimaryVariant2,
            modifier = Modifier.size(30.dp)
        )
    }
}

@Preview
@Composable
private fun ProfileCardPreview() {
    ClasticTheme {
        ProfileCard(
            User(
                username = "Warren",
                email = "warren@gmail.com"
            )
        )
    }
}