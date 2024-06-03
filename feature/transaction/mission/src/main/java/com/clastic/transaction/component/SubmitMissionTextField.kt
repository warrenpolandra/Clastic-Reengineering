package com.clastic.transaction.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clastic.transaction.mission.R
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanTextField

@Composable
internal fun SubmitMissionTextField(
    linkSubmission: String,
    linkSubmissionEnabled: Boolean,
    onLinkSubmissionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = linkSubmission,
        enabled = linkSubmissionEnabled,
        onValueChange = onLinkSubmissionChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Link,
                contentDescription = null
            )
        },
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = CyanTextField,
            unfocusedBorderColor = CyanTextField,
            leadingIconColor = CyanTextField,
            textColor = Color.Black,
            disabledTextColor = Color.Gray,
            disabledLeadingIconColor = Color.Gray,
            disabledBorderColor = Color.Gray
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            imeAction = ImeAction.Next
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.link_submission_placeholder),
                color = Color.Gray
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun SubmitMissionTextFieldPreview() {
    ClasticTheme {
        SubmitMissionTextField(
            linkSubmission = "",
            linkSubmissionEnabled = true,
            onLinkSubmissionChange = {}
        )
    }
}