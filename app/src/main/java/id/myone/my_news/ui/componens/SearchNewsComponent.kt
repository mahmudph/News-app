/**
 * Created by Mahmud on 05/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.componens

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.myone.my_news.ui.theme.Paging3exampleTheme
import id.myone.my_news.R
import id.myone.my_news.ui.theme.plusJakartaSans

@Composable
fun SearchNewsComponent(
    modifier: Modifier = Modifier,
    onChangeText: (value: String) -> Unit,
    onPressBack: () -> Unit,
    @StringRes placeholder: Int,
    value: String,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        value = value,
        onValueChange = onChangeText,
        maxLines = 1,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onBackground
        ),
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onChangeText(value)
            }
        ),
        shape = RoundedCornerShape(8.dp),
        leadingIcon = {
            IconButton(onClick = onPressBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground)
            }
        },
        trailingIcon = {
            IconButton(onClick = { onChangeText(value) }) {
                Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground)
            }
        },
        textStyle = MaterialTheme.typography.bodySmall.copy(
            fontFamily = plusJakartaSans,
        ),
        placeholder = {
            Text(
                stringResource(id = placeholder),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                ),
                fontFamily = plusJakartaSans,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
    )
}


@Preview(showBackground = true, backgroundColor = 0xFFFF)
@Composable
private fun SearchNewsComponentPreview() {
    Paging3exampleTheme {
        SearchNewsComponent(
            onChangeText = {},
            value = "Test",
            placeholder = R.string.search_placheolder,
            onPressBack = {}
        )
    }
}