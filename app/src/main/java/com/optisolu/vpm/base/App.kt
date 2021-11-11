package com.optisolu.vpm.base

import android.app.Application
import com.optisolu.vpm.factories.FeedsViewModelFactory
import com.optisolu.vpm.modules.repositoriesModule
import com.optisolu.vpm.modules.viewModelsModule
import com.optisolu.vpm.services.VideosApiService
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class App: Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(androidXModule(this@App))
        bind() from singleton { AppDatabase(instance()) }
        import(repositoriesModule)
        import(viewModelsModule)
        bind() from singleton { VideosApiService() }
        bind() from singleton { FeedsViewModelFactory(instance()) }
    }
}