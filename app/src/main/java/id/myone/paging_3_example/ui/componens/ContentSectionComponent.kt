/**
 * Created by Mahmud on 07/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.componens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.myone.paging_3_example.R

@Composable
fun ContentSectionComponent(
    modifier: Modifier = Modifier,
    @StringRes title: String,
    @StringRes subtitle: String? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.padding(vertical = 4.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(title, fontWeight = FontWeight.Medium, fontSize = 22.sp)
            subtitle?.let { Text(subtitle, fontSize = 20.sp) }
        }
        IconButton(onClick = onClick, modifier= Modifier.padding(0.dp)) {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_baseline_filter_list_24
                ),
                contentDescription = null
            )
        }
    }
}