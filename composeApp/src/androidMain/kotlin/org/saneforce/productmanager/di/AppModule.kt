package org.saneforce.productmanager.di

import org.saneforce.productmanager.di.commonModule
import org.saneforce.productmanager.di.networkModule

fun appModule() = listOf(commonModule, networkModule)
