/**
 * Created by Mahmud on 05/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.paging_3_example.common

import java.text.SimpleDateFormat
import java.util.*

fun String.formatToLocalDate(): String {
    val formatDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    formatDate.timeZone = TimeZone.getTimeZone("UTC")

    val inputDate = formatDate.parse(this)!!
    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return outputFormat.format(inputDate)
}