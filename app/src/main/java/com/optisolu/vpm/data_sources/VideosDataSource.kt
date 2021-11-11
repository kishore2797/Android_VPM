package com.optisolu.vpm.data_sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.optisolu.vpm.models.Data
import com.optisolu.vpm.models.VideoModel
import com.optisolu.vpm.seals.VideosRequestResult
import com.optisolu.vpm.services.VideosApiService

class VideosDataSource(videosApiService: VideosApiService): PagingSource<Int, Data>() {
    private val apiService = videosApiService.invoke()

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        try {
            val currentLoadingPageKey = params.key ?: 1

            val responseData = mutableListOf<Data>()

            when (val response = apiService.getVideosData(currentLoadingPageKey)) {
                is VideosRequestResult.Success -> {
                    responseData.addAll(response.dataList)
                }
            }

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}