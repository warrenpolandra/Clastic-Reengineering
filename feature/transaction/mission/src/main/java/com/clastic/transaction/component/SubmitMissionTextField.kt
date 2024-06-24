package com.clastic.transaction.component

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.sp
import com.clastic.transaction.mission.R
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanTextField
import com.clastic.utils.TextUtils

@Composable
internal fun SubmitMissionTextField(
    linkSubmission: String,
    linkSubmissionEnabled: Boolean,
    onLinkSubmissionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
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
                disabledBorderColor = Color.Gray,
                errorLeadingIconColor = Color.Red,
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                imeAction = ImeAction.Next
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.link_submission_placeholder),
                    color = Color.Gray
                )
            },
            isError = linkSubmission.isNotEmpty() && !TextUtils.isValidURL(linkSubmission),
            modifier = modifier.fillMaxWidth()
        )
        if (linkSubmission.isNotEmpty() && !TextUtils.isValidURL(linkSubmission)) {
            Text(
                text = stringResource(R.string.link_submission_error),
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SubmitMissionTextFieldPreview() {
    ClasticTheme {
        SubmitMissionTextField(
            linkSubmission = "www",
            linkSubmissionEnabled = true,
            onLinkSubmissionChange = {}
        )
    }
}