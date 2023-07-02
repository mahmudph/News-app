/**
 * Created by Mahmud on 29/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.screens.forgot_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.myone.my_news.R
import id.myone.my_news.ui.componens.ButtonComponent
import id.myone.my_news.ui.theme.Paging3exampleTheme
import id.myone.my_news.ui.theme.plusJakartaSans
import id.myone.my_news.ui.utils.NoRippleEffect

@Composable
fun SendForgotPasswordInfoScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    Scaffold { paddingValues ->
        Box(modifier = modifier.padding(paddingValues).padding(16.dp)) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
                    CompositionLocalProvider(
                        LocalRippleTheme provides NoRippleEffect
                    ) {
                        Row(modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable(onClick = onNavigateBack),
                            verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null)
                            Text(text = stringResource(R.string.back),
                                fontFamily = plusJakartaSans,
                                modifier = Modifier.padding(start = 12.dp))
                        }
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.completed),
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight(0.5f)
                        .background(Color.Transparent)
                )

                Text(
                    stringResource(R.string.send_reset_password_success),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = plusJakartaSans,
                    style = MaterialTheme.typography.bodySmall,
                )

                Spacer(modifier = Modifier.height(12.dp))

                ButtonComponent(
                    name = stringResource(R.string.login),
                    isEnabled = true,
                    onClick = onNavigateToLogin
                )
            }
        }
    }
}


@Composable
@Preview
fun SendForgotPasswordInfoScreenPreview() {
    Paging3exampleTheme {
        SendForgotPasswordInfoScreen(
            onNavigateBack = {},
            onNavigateToLogin = {}
        )
    }
}
