/**
 * Created by Mahmud on 05/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.ui.componens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.myone.my_news.R
import id.myone.my_news.ui.theme.Paging3exampleTheme
import id.myone.my_news.ui.theme.plusJakartaSans

@Composable
fun InformationComponent(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    description: String,
    @DrawableRes image: Int? = null,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        image?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .fillMaxWidth(),
                alignment = Alignment.Center
            )
        }

        Text(text = stringResource(id = title),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontFamily = plusJakartaSans,
            fontWeight = FontWeight.Medium)

        Text(text = description,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            fontFamily = plusJakartaSans,
            fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true, backgroundColor = 0xfff)
@Composable
private fun InformationComponentPreview() {
    Paging3exampleTheme {
        InformationComponent(
            title = R.string.error_title,
            description = stringResource(id = R.string.error_description),
            image = R.drawable.not_found
        )
    }
}