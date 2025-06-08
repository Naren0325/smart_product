package org.saneforce.productmanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.saneforce.productmanager.theme.titleText
import org.saneforce.productmanager.widget.HomeLayout
import org.saneforce.productmanager.ui.viewmodel.ProductViewModel
import productmanager.composeapp.generated.resources.Res
import productmanager.composeapp.generated.resources.sane_force_task
import productmanager.composeapp.generated.resources.save


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(viewModel: ProductViewModel) {
    val productList by viewModel.products.collectAsState()
    val loading by viewModel.state.collectAsState()
    val error by viewModel.error.collectAsState()

    // Load products on first composition
    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (loading == ProductState.LOADING) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }

        error?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        HomeLayout(
            content = {
                Column {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        ),
                        title = {
                            Row(
                                Modifier.fillMaxWidth().padding(end = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    stringResource(Res.string.sane_force_task),
                                    color = MaterialTheme.colorScheme.titleText,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    )
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        itemsIndexed(productList) { index, product ->
                            ProductListItem(
                                product = product,
                                onDelete = { viewModel.deleteProduct(product.productCode) },
                                onQuantityChange = { viewModel.updateQuantity(product.productCode, it) },
                                onRateChange = { viewModel.updateRate(product.productCode, it.toString()) }
                            )
                        }
                    }

                    Button(
                        shape = MaterialTheme.shapes.small,
                        onClick = { viewModel.saveProducts() },
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    ) {
                        Text(
                            stringResource(Res.string.save),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            fontWeight = FontWeight.W500,
                        )
                    }
                }
            }
        )
    }
}

