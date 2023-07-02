/**
 * Created by Mahmud on 21/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.componens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.myone.my_news.ui.theme.Paging3exampleTheme
import id.myone.my_news.ui.theme.plusJakartaSans


@Composable
fun TextInputField(
    modifier: Modifier = Modifier,
    label: String,
    placeHolder: String? = null,
    initialValue: String,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
) {

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        Text(label, fontSize = 16.sp, fontWeight = FontWeight.W600, fontFamily = plusJakartaSans)
        OutlinedTextField(
            value = initialValue,
            maxLines = 1,
            singleLine = true,
            onValueChange = onValueChange,
            keyboardOptions = keyboardOptions,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = TextFieldDefaults.colors(cursorColor = Color.Red),
            keyboardActions = keyboardActions,
            placeholder = {
                placeHolder?.let { Text(it, fontFamily = plusJakartaSans,) }
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun TextInputFieldPreview() {
    Paging3exampleTheme {
        TextInputField(
            initialValue = "Joko",
            onValueChange = {},
            label = "Name"
        )
    }
}
