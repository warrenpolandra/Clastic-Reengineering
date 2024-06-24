package com.clastic.transaction.component

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.clastic.transaction.mission.R
import com.clastic.ui.theme.CyanPrimary
import com.clastic.ui.theme.CyanPrimaryVariant

@Composable
internal fun SubmitMissionImageInput(
    imageSubmission: Uri?,
    imageSubmissionEnabled: Boolean,
    onRemoveImage: () -> Unit,
    onImageChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.border(4.dp, CyanPrimary, RoundedCornerShape(16.dp))
        ) {
            if (imageSubmission != null) {
                AsyncImage(
                    model = imageSubmission,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(RoundedCornerShape(16.dp))
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth()
        ) {
            if (imageSubmission != null) {
                Button(
                    enabled = imageSubmissionEnabled,
                    onClick = onRemoveImage,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red
                    )
                ) {
                    Text(
                        text = stringResource(R.string.delete_image),
                        color = Color.White
                    )
                }
            }
            Button(
                enabled = imageSubmissionEnabled,
                onClick = onImageChange,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = CyanPrimaryVariant
                )
            ) {
                Text(
                    text = stringResource(R.string.choose_image),
                    color = Color.White
                )
            }
        }
    }
}