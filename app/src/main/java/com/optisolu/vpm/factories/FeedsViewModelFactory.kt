package com.optisolu.vpm.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.optisolu.vpm.repositories.FeedsRepository
import com.optisolu.vpm.view_models.FeedsViewModel

class FeedsViewModelFactory(private val feedsRepository: FeedsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FeedsViewModel(feedsRepository) as T
    }
}