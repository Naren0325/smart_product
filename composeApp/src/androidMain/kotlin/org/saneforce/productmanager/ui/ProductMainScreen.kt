package org.saneforce.productmanager.ui

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import co.touchlab.kermit.Logger
import org.saneforce.productmanager.ui.viewmodel.ProductViewModel


class ProductMainScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        LifecycleEffect(
            onStarted = { Logger.d("Navigator: Start ProductListScreen") },
            onDisposed = { Logger.d("Navigator: Dispose ProductListScreen") }
        )
        val viewModel = getScreenModel<ProductViewModel>()
        ProductListScreen(viewModel)
    }
}
