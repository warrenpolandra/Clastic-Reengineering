package com.clastic.qrcode.scanner

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.qrcode.R
import com.clastic.qrcode.component.QrCamera
import com.clastic.ui.ClasticTopAppBar

@Composable
fun QrScannerScreen(
    onScannedSuccess: (String) -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QrScannerViewModel = hiltViewModel<QrScannerViewModel>()
) {
    val context = LocalContext.current
    var hasCameraPermission by remember {
        mutableStateOf(ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        }
    )
    LaunchedEffect(key1 = true) { launcher.launch(Manifest.permission.CAMERA) }

    val isUserExist by viewModel.isUserExist.collectAsState()

    QrScannerScreenContent(
        context = context,
        onQrCodeScanned = { userId ->
            viewModel.checkUserById(userId) { onScannedSuccess(userId) }
        },
        hasCameraPermission = hasCameraPermission,
        isUserExist = isUserExist,
        navigateToHome = navigateToHome,
        modifier = modifier
    )
}

@Composable
fun QrScannerScreenContent(
    context: Context,
    onQrCodeScanned: (String) -> Unit,
    navigateToHome: () -> Unit,
    hasCameraPermission: Boolean,
    isUserExist: Boolean?,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ClasticTopAppBar(
                title = stringResource(R.string.scan_qr_code),
                onBackPressed = navigateToHome
            )
        },
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = stringResource(R.string.scan_qr_code_to_start),
                    fontSize = 20.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                if (hasCameraPermission) {
                    QrCamera(
                        modifier = Modifier
                            .size(400.dp)
                            .padding(vertical = 20.dp),
                        onQrCodeScanned = onQrCodeScanned
                    )
                }
                Text(
                    text = stringResource(R.string.scan_qr_warning),
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
            if (isUserExist == false) {
                showToast(context, context.getString(R.string.user_not_exist))
            }
        }
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}