package org.saneforce.productmanager.di

import org.saneforce.productmanager.api.ProductApiImpl
import org.saneforce.productmanager.ui.viewmodel.ProductViewModel
import org.koin.dsl.module

val commonModule = module {
    // service
    single { ProductApiImpl() }

    // ViewModel
    factory { ProductViewModel() }
}