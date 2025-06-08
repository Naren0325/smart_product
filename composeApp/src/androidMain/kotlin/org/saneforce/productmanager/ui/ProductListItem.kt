package org.saneforce.productmanager.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.billsplit.project.widget.Quantity
import org.jetbrains.compose.resources.stringResource
import org.saneforce.productmanager.model.Product
import org.saneforce.productmanager.theme.bodyText
import org.saneforce.productmanager.widget.CustomTextField
import org.saneforce.productmanager.widget.SetupLayout
import org.saneforce.productmanager.widget.TextSpaceBetween
import productmanager.composeapp.generated.resources.Res
import productmanager.composeapp.generated.resources.amount
import productmanager.composeapp.generated.resources.delete
import productmanager.composeapp.generated.resources.rate


@Composable
fun ProductListItem(
    product: Product,
    onDelete: () -> Unit,
    onQuantityChange: (Int) -> Unit,
    onRateChange: (Double) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Product Info
            Column(modifier = Modifier.weight(0.4f)) {
                Text(
                    text = product.productName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.bodyText,
                    fontWeight = FontWeight.W600
                )
            }

            // Quantity Controls
            Row(
                modifier = Modifier.weight(0.4f).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Quantity(
                    addButton = {
                        onQuantityChange(product.quantity + 1)
                    },
                    quantity = product.quantity.toString(),
                    removeButton = {
                        onQuantityChange(product.quantity - 1)
                    }
                )
                Spacer(Modifier.width(10.dp))
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ){
//                    Text(
//                        text = stringResource(Res.string.rate),
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.labelText,
//                        fontWeight = FontWeight.W500,
//                        modifier = Modifier.padding(4.dp)
//                    )
//                    Spacer(Modifier.width(10.dp))
                    CustomTextField(
                        value = product.rate.toString(),
                        placeholder = stringResource(Res.string.rate),
                        onValueChange = { newValue ->
                            val filtered = newValue.filter { it.isDigit() || it == '.' }
                            if (filtered != newValue) {
                                onRateChange(filtered.toDouble())
                            } else {
                                onRateChange(newValue.toDouble())
                            }
                        },
                        modifier = Modifier.width(120.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                    )
                }
            }

            Row(
                modifier = Modifier.weight(0.4f).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                SetupLayout(
                    content = {
                        TextSpaceBetween(
                            textOne = stringResource(Res.string.amount),
                            textTwo = "₹${"%.2f".format(product.total)}"
                        )
                    },
                    content2 = {
                        // Delete Button
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(10.dp)
                                .clickable {
                                    onDelete()
                                }
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    shape = MaterialTheme.shapes.small
                                )
                        ) {
                            Text(
                                text = stringResource(Res.string.delete),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                )
            }
        }
        HorizontalDivider()
    }
}
