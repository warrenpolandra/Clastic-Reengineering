package com.clastic.leaderboard.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.clastic.leaderboard.R
import com.clastic.model.User
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary

@Composable
internal fun LeaderboardHeader(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.EmojiEvents,
            tint = Color.Yellow,
            contentDescription = stringResource(R.string.leaderboard),
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 4.dp)
        )
        Text(
            text = stringResource(R.string.find_your_name),
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

@Composable
internal fun LeaderboardFooter(
    position: Int,
    user: User,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(CyanPrimary)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = stringResource(R.string.current_rank, position),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(64.dp)
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
                    Column (
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = user.username ?: "",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.width(160.dp)
                        )
                        Text(
                            text = stringResource(R.string.number_transaction_made, user.totalTransaction.toString()),
                            color = Color.White
                        )
                    }
                }
                Text(
                    text = stringResource(R.string.gathered_plastic, "%.2f".format(user.totalPlastic)),
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LeaderboardHeaderPreview() {
    ClasticTheme {
        LeaderboardHeader()
    }
}

@Preview(showBackground = true)
@Composable
private fun LeaderboardFooterPreview() {
    ClasticTheme {
        LeaderboardFooter(
            1,
            User(
                username = "Warren"
            )
        )
    }
}