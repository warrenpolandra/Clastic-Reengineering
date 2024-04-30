package com.clastic.droppoint

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.model.DropPoint
import com.clastic.ui.ClasticTopAppBar
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DropPointMapScreen(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val viewModel: DropPointMapViewModel = hiltViewModel<DropPointMapViewModel>()
    val cameraPositionState = rememberCameraPositionState()
    val dropPointList by viewModel.dropPointList.collectAsState()
    val boundsCenter by viewModel.boundsCenter.collectAsState()

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasPermission = granted
        }
    )

    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    DropPointMapScreenContent(
        context = context,
        hasPermission = hasPermission,
        dropPointList = dropPointList,
        cameraPositionState = cameraPositionState,
        boundsCenter = boundsCenter,
        navigateToHome = navigateToHome,
        modifier = modifier
    )
}

@Composable
private fun DropPointMapScreenContent(
    context: Context,
    hasPermission: Boolean,
    dropPointList: List<DropPoint>,
    cameraPositionState: CameraPositionState,
    boundsCenter: LatLng,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ClasticTopAppBar(
                title = stringResource(R.string.drop_point_locations),
                onBackPressed = navigateToHome
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (hasPermission) {
                GoogleMap(cameraPositionState = cameraPositionState) {
                    for (dropPoint in dropPointList) {
                        Marker(
                            state = MarkerState(LatLng(dropPoint.lat, dropPoint.long)),
                            title = dropPoint.name,
                            snippet = dropPoint.address,
                            onInfoWindowClick = {
                                openGoogleMap(context, dropPoint.address)
                            }
                        )
                    }
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(boundsCenter, 11f)
                }
            }
        }
    }
}

fun openGoogleMap(context: Context, address: String) {
    val uri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.setPackage("com.google.android.apps.maps")

    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        val encodedAddress = Uri.encode(address)
        val mapsWebsiteUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$encodedAddress")
        val mapsWebsiteIntent = Intent(Intent.ACTION_VIEW, mapsWebsiteUri)
        context.startActivity(mapsWebsiteIntent)
    }
}