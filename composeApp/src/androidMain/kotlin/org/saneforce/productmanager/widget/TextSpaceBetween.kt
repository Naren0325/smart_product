package org.saneforce.productmanager.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.saneforce.productmanager.theme.labelText


@Composable
fun TextSpaceBetween(textOne: String, textTwo: String) {
    Row(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = textOne,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.labelText,
            fontWeight = FontWeight.W600
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = textTwo,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.labelText,
            fontWeight = FontWeight.W500
        )
    }
}
