package com.clastic.qrcode

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.clastic.ui.ClasticTopAppBar
import com.clastic.utils.QrUtil

@Composable
fun MyQrCodeScreen(
    qrText: String,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bitmap = remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(qrText) { bitmap.value = QrUtil.createQrBitmap(qrText) }

    bitmap.value?.let { imageBitmap ->
        Scaffold(
            topBar = {
                ClasticTopAppBar(
                    title = stringResource(R.string.my_qr_code),
                    onBackPressed = navigateToHome
                )
            }
        ) { innerPadding ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(R.string.show_qr_to_operator),
                        style = MaterialTheme.typography.h5.copy(
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
}
