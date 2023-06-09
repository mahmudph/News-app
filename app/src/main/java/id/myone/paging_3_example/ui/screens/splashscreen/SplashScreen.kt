/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.screens.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import id.myone.paging_3_example.R
import id.myone.paging_3_example.common.VoidCallback

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onNaviagateToHome: VoidCallback,
    onNavigateToLogin: VoidCallback,
) {

    Scaffold {
        Box(
            modifier = modifier.padding(it).fillMaxSize(),
            contentAlignment = Alignment.Center) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = null,
                modifier = Modifier.size(150.dp)

            )
        }
    }
}
