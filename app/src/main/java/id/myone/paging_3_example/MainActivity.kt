package id.myone.paging_3_example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import id.myone.paging_3_example.ui.route.GraphNavigation
import id.myone.paging_3_example.ui.theme.Paging3exampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Paging3exampleTheme {
                GraphNavigation()
            }
        }
    }
}