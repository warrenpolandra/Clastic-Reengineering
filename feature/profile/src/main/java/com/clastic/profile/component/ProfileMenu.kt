package com.clastic.profile.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clastic.profile.R
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary

@Composable
internal fun ProfileMenu(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
   Row(
       verticalAlignment = Alignment.CenterVertically,
       modifier = modifier
           .fillMaxWidth()
           .height(40.dp)
           .drawBehind {
               drawLine(
                   color = CyanPrimary,
                   start = Offset(0f, 0f),
                   end = Offset(size.width, 0f),
                   strokeWidth = 1.dp.toPx()
               )
               drawLine(
                   color = CyanPrimary,
                   start = Offset(0f, size.height),
                   end = Offset(size.width, size.height),
                   strokeWidth = 1.dp.toPx() / 2
               )
           }
           .clickable { onClick() }
           .padding(4.dp)
   ) {
       Icon(
           imageVector = icon,
           contentDescription = title,
           tint = CyanPrimary,
           modifier = Modifier
               .padding(horizontal = 10.dp)
               .size(40.dp)
       )
       Text(
           text = title,
           color = Color.Black,
           fontSize = 16.sp,
           fontWeight = FontWeight.Medium
       )
       Spacer(modifier = Modifier.weight(1f))
       Icon(
           imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
           contentDescription = null,
           tint = CyanPrimary,
           modifier = Modifier.size(40.dp)
       )
   }
}

@Preview(showBackground = true)
@Composable
private fun ProfileMenuPreview() {
    ClasticTheme {
        ProfileMenu(
            onClick = {},
            title = stringResource(R.string.plastic_transaction_history),
            icon = Icons.AutoMirrored.Filled.List
        )
    }
}