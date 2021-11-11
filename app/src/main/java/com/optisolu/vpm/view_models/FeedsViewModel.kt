package com.optisolu.vpm.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.optisolu.vpm.entities.FeedEntity
import com.optisolu.vpm.repositories.FeedsRepository
import kotlinx.coroutines.launch

class FeedsViewModel(private val feedsRepository: FeedsRepository): ViewModel() {
    fun insertFeed(feedEntity: FeedEntity) {
        viewModelScope.launch {
            feedsRepository.insertFeed(feedEntity)
        }
    }

    fun updateFeed(feedEntity: FeedEntity) {
        viewModelScope.launch {
            feedsRepository.updateFeed(feedEntity)
        }
    }

    fun deleteFeed(feedEntity: FeedEntity) {
        viewModelScope.launch {
            feedsRepository.deleteFeed(feedEntity)
        }
    }

    suspend fun getAllFeeds(): LiveData<List<FeedEntity>> {
        return feedsRepository.getAllFeeds()
    }

    suspend fun getFeedById(feedId: Int): LiveData<FeedEntity> {
        return feedsRepository.getFeedById(feedId)
    }


}