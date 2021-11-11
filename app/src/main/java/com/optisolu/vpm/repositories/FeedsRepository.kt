package com.optisolu.vpm.repositories

import androidx.lifecycle.LiveData
import com.optisolu.vpm.base.AppDatabase
import com.optisolu.vpm.entities.FeedEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FeedsRepository(private val database: AppDatabase) {
    // get all feeds
    suspend fun getAllFeeds(): LiveData<List<FeedEntity>> {
        return withContext(Dispatchers.IO) {
            database.feedDao().getAllFeeds()
        }
    }

    // get feed by id
    suspend fun getFeedById(feedId: Int): LiveData<FeedEntity> {
        return withContext(Dispatchers.IO) {
            database.feedDao().getFeedById(feedId)
        }
    }

    // insert new feed
    suspend fun insertFeed(feedEntity: FeedEntity) {
        return withContext(Dispatchers.IO) {
            database.feedDao().postFeed(feedEntity)
        }
    }

    // update existing feed
    suspend fun updateFeed(feedEntity: FeedEntity) {
        return withContext(Dispatchers.IO) {
            database.feedDao().putFeed(feedEntity)
        }
    }

    // delete existing feed
    suspend fun deleteFeed(feedEntity: FeedEntity) {
        return withContext(Dispatchers.IO) {
            database.feedDao().deleteFeed(feedEntity)
        }
    }
}