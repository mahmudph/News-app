/**
 * Created by Mahmud on 06/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.ui.componens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.myone.paging_3_example.common.VoidCallback
import id.myone.paging_3_example.ui.theme.Paging3exampleTheme
import id.myone.paging_3_example.ui.theme.plusJakartaSans

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    name: String,
    isEnabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    disabledBackgroundColor: Color = Color.Gray,
    disabledTextColor: Color = Color.White,
    onClick: VoidCallback,
) {
    Button(onClick = onClick,
        enabled = isEnabled,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            disabledContainerColor = disabledBackgroundColor,
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            color = if (isEnabled) MaterialTheme.colorScheme.onPrimary else disabledTextColor,
            fontFamily = plusJakartaSans
        )
    }
}


@Preview(showBackground = true, widthDp = 460)
@Composable
private fun ButtonComponentPreview() {
    Paging3exampleTheme {
        ButtonComponent(
            name = "Login",
            onClick = {

            },
        )
    }
}