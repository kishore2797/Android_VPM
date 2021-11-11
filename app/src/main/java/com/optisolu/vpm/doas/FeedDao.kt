package com.optisolu.vpm.doas

import androidx.lifecycle.LiveData
import androidx.room.*
import com.optisolu.vpm.entities.FeedEntity

@Dao
interface FeedDao {
    // select all feeds
    @Query("SELECT * FROM FeedEntity")
    fun getAllFeeds(): LiveData<List<FeedEntity>>

    // select feed by id
    @Query("SELECT * FROM FeedEntity WHERE id=:feedId")
    fun getFeedById(feedId: Int): LiveData<FeedEntity>

    // insert new feed
    @Insert
    fun postFeed(feed: FeedEntity)

    // update existing feed
    @Update
    fun putFeed(feed: FeedEntity)

    // update existing feed
    @Delete
    fun deleteFeed(feed: FeedEntity)
}