package com.optisolu.vpm.modules

import com.optisolu.vpm.repositories.FeedsRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val repositoriesModule = Kodein.Module("repositories") {
    bind() from singleton { FeedsRepository(instance()) }
}