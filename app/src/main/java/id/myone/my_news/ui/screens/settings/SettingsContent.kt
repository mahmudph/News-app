/**
 * Created by Mahmud on 08/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import id.myone.my_news.R
import id.myone.my_news.ui.componens.ListSettingItem
import id.myone.my_news.ui.theme.Paging3exampleTheme
import my.nanihadesuka.compose.ColumnScrollbar
import my.nanihadesuka.compose.ScrollbarSelectionMode

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    currentLanguage: String,
    listAvailableLang: List<Map<String, String>>,
    idShowDialogChangeLang: Boolean = false,
    changeShowDialog: (value: Boolean) -> Unit,
    setPreferenceLanguage: (lang: Map<String, String>) -> Unit,
) {

    val scrollState = rememberScrollState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            ListSettingItem(
                label = stringResource(R.string.preference_article_lang),
                leadingDrawableIcon = R.drawable.ic_baseline_language_24,
                subLabel = currentLanguage,
                onClick = {
                    changeShowDialog(true)
                }
            )
        }

        if (idShowDialogChangeLang) {
            Dialog(
                onDismissRequest = {
                    changeShowDialog(false)
                }
            ) {
                Surface(
                    modifier = Modifier
                        .wrapContentSize()
                        .height(350.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {

                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            stringResource(R.string.select_language),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W600,
                            modifier = Modifier.padding(16.dp),
                        )

                        Box(
                            modifier = Modifier.padding(vertical = 12.dp)
                        ) {
                            ColumnScrollbar(
                                state = scrollState,
                                selectionMode = ScrollbarSelectionMode.Full
                            ) {
                                Column(
                                    modifier = Modifier.verticalScroll(scrollState)
                                ) {

                                    listAvailableLang.forEach {
                                        ListItem(
                                            modifier = Modifier.clickable { setPreferenceLanguage(it) },
                                            leadingContent = {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.ic_baseline_language_24),
                                                    contentDescription = null,
                                                )
                                            },
                                            headlineContent = {
                                                Text(text = it.keys.first())
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xfff)
@Composable
private fun SettingsContentPreview() {
    Paging3exampleTheme {
        SettingsContent(
            idShowDialogChangeLang = true,
            setPreferenceLanguage = {},
            changeShowDialog = {},
            listAvailableLang = emptyList(),
            currentLanguage = "En"
        )
    }
}