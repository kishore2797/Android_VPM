package com.optisolu.vpm.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.optisolu.vpm.data_sources.VideosDataSource
import com.optisolu.vpm.services.VideosApiService

class VideosViewModel(videosApiService: VideosApiService) : ViewModel() {
    private val apiService = videosApiService.invoke()

    val videosData = Pager(PagingConfig(pageSize = 2)) {
        VideosDataSource(apiService)
    }.liveData.cachedIn(viewModelScope)
}