package com.clastic.authentication.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clastic.authentication.R
import com.clastic.ui.theme.ClasticTheme

@Composable
fun GoogleSignInButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    stringId: Int,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(19.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White
        ),
        enabled = isEnabled,
        onClick = onClick
    ) {
        Image(
            painter = painterResource(R.drawable.ic_google_logo),
            contentDescription = null
        )
        Text(
            text = stringResource(stringId),
            color = Color.Gray,
            fontSize = 18.sp,
            modifier = Modifier.padding(6.dp)
        )
    }
}

@Preview(showBackground = false)
@Composable
fun GoogleSignInButtonPreview() {
    ClasticTheme {
        GoogleSignInButton(
            isEnabled = true,
            onClick = {},
            stringId = R.string.google_sign_in
        )
    }
}