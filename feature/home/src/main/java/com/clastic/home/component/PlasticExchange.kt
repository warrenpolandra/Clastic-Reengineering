package com.clastic.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.clastic.home.R
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.ui.theme.CyanPrimaryVariant

@Composable
fun PlasticExchange(
    role: String,
    navigateToDropPointMap: () -> Unit,
    navigateToQrCode: () -> Unit,
    navigateToQrCodeScanner: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = /* TODO: String Resource */ "Exchange your plastic",
                style = MaterialTheme.typography.subtitle1.copy(Color.Gray)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                modifier = Modifier
                    .height(100.dp)
            ) {
                if (role == "owner") {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .border(
                                2.dp,
                                color = CyanPrimaryVariant,
                                RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                            .background(Color.White)
                            .fillMaxHeight()
                            .clickable { navigateToQrCodeScanner() }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Text(
                                        text = "Scan QR Code",
                                        style = MaterialTheme.typography.subtitle1.copy(
                                            color = CyanPrimaryVariant,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Icon(
                                        imageVector = Icons.Default.QrCodeScanner,
                                        tint = CyanPrimary,
                                        contentDescription = null
                                    )
                                }
                                Text(
                                    text = "Scan user's QR code to do transaction",
                                    style = MaterialTheme.typography.caption.copy(
                                        color = Color.Black
                                    )
                                )
                                Spacer(modifier = Modifier.height(1.dp))
                            }
                            Icon(
                                painter = painterResource(R.drawable.ic_forward_white),
                                tint = CyanPrimaryVariant,
                                contentDescription = null
                            )
                        }
                    }
                } else {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .border(
                                2.dp, color = Color("#0198B3".toColorInt()),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                            .background(color = Color.White)
                            .clickable { navigateToDropPointMap() }
                            .fillMaxHeight()
                    ) {
                        //Drop Off
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.spacedBy(2.dp),
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Text(
                                        text = "Drop Off Point",
                                        style = MaterialTheme.typography.subtitle1.copy(
                                            color = Color("#0198B3".toColorInt()),
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_location_white),
                                        contentDescription = null,
                                        tint = Color.Red
                                    )
                                }
                                Text(
                                    text = "Pilih Drop Off Point terdekat\ndan tukarkan plastikmu!",
                                    style = MaterialTheme.typography.caption.copy(color = Color.Black)
                                )
                                Spacer(modifier = modifier.height(1.dp))
                            }
                            Icon(
                                painter = painterResource(id = R.drawable.ic_forward_white),
                                contentDescription = null,
                                tint = Color("#0198B3".toColorInt())
                            )
                        }
                    }
                }
                //My Barcode
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .border(
                            2.dp, color = Color("#0198B3".toColorInt()),
                            RoundedCornerShape(8.dp)
                        )
                        .fillMaxHeight()
                        .clickable { navigateToQrCode() }
                        .padding(8.dp)
                        .background(color = Color.White)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "My QR Code",
                            style = MaterialTheme.typography.subtitle1.copy(
                                color = Color("#0198B3".toColorInt()),
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Image(
                            painter = painterResource(id = R.drawable.qrcode),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlasticExchangePreview() {
    ClasticTheme {
        PlasticExchange(
            role = "user",
            navigateToDropPointMap = { /*TODO*/ },
            navigateToQrCode = { /*TODO*/ },
            navigateToQrCodeScanner = { /*TODO*/ })
    }
}