/**
 * Created by Mahmud on 07/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.componens

import android.net.Uri
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import id.myone.paging_3_example.ui.theme.Paging3exampleTheme

@Composable
fun WebViewComponent(
    modifier: Modifier = Modifier,
    url: String,
) {
    AndroidView(
        modifier = modifier,
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
                        return true
                    }
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?,
                    ): Boolean {
                        val parse = Uri.parse(url)
                        if (request?.url?.host == parse.host) {
                            return false
                        }
                        return false
                    }
                }
                loadUrl(url)
            }
        }, update = {
            it.loadUrl(url)
        }
    )
}


@Preview(showBackground = true, backgroundColor = 0xfff)
@Composable
private fun WebViewComponentPreview() {
    Paging3exampleTheme {
        WebViewComponent(
            url = "https://www.geeksforgeeks.org/webview-in-android-using-jetpack-compose/"
        )
    }
}