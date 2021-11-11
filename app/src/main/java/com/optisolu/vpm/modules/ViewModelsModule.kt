package com.optisolu.vpm.modules

import com.optisolu.vpm.view_models.FeedsViewModel
import com.optisolu.vpm.view_models.VideosViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val viewModelsModule = Kodein.Module("viewModels") {
    bind() from singleton {
        VideosViewModel(instance())
    }
    bind() from singleton {
        FeedsViewModel(instance())
    }
}