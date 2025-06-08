package org.saneforce.productmanager

import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import co.touchlab.kermit.Logger
import org.saneforce.productmanager.ui.ProductMainScreen
import org.saneforce.productmanager.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AppTheme {
        Navigator(ProductMainScreen(), onBackPressed = { currentScreen ->
            Logger.d("app.nav: Navigator: Pop screen #$currentScreen")
            true
        }) { navigator ->
            SlideTransition(navigator)
        }
    }
}