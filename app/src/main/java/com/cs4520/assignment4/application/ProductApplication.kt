package com.cs4520.assignment4.application

import android.app.Application

/**
 * Custom application instance that holds onto a app container object for dependency injection
 * and instantiation of local and remote sources for the ProductRepository
 */
class ProductApplication: Application() {
    val appContainer: AppContainer = AppContainer()
}