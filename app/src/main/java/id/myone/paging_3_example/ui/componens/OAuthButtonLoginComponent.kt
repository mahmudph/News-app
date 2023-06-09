/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.componens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.myone.paging_3_example.R
import id.myone.paging_3_example.common.VoidCallback
import id.myone.paging_3_example.ui.theme.Paging3exampleTheme
import id.myone.paging_3_example.ui.theme.plusJakartaSans

@Composable
fun OAuthButtonLoginComponent(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    @StringRes name: String,
    backgroundColor: Color,
    textColor: Color,
    onClick: VoidCallback,
) {
    Button(onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp).align(Alignment.CenterStart)
                )

                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    fontFamily = plusJakartaSans,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = textColor,
                )
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xfff)
@Composable
private fun OAuthButtonLoginComponentPreview() {
    Paging3exampleTheme {
        OAuthButtonLoginComponent(
            icon = R.drawable.google_icon,
            name = "Google",
            backgroundColor = Color.White,
            textColor = Color.Black,
            onClick = {}

        )
    }
}