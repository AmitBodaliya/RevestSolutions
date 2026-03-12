package com.abapp.revestsolutions.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import com.abapp.revestsolutions.api.createHttpClient
import com.abapp.revestsolutions.api.ProductApi
import com.abapp.revestsolutions.api.ProductRepository
import com.abapp.revestsolutions.api.ProductRepositoryImpl
import com.abapp.revestsolutions.viewmodel.ProductViewModel



//init di
fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            appModule
        )
    }




val appModule = module {

    //http client
    single { createHttpClient() }

    // API Service
    single { ProductApi(get()) }

    // Repository
    single { ProductRepositoryImpl(get()) }

    // ViewModel
    single { ProductViewModel(get()) }

}
