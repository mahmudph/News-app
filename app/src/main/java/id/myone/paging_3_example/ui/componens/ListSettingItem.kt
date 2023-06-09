/**
 * Created by Mahmud on 06/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.componens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import id.myone.paging_3_example.ui.theme.plusJakartaSans

@Composable
fun ListSettingItem(
    modifier: Modifier = Modifier,
    label: String,
    @DrawableRes leadingDrawableIcon: Int,
    onClick: () -> Unit,
) {
    Surface(modifier = modifier,color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(16.dp)) {
        Row(modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 16.dp)) {
            Icon(
                painter = painterResource(id = leadingDrawableIcon),
                modifier = Modifier.size(25.dp),
                contentDescription = null)

            Text(
                text = label,
                fontWeight = FontWeight.W700,
                fontFamily = plusJakartaSans,
                modifier = Modifier.padding(start = 12.dp)
            )

            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }
}
