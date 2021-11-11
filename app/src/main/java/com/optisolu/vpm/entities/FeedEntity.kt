package com.optisolu.vpm.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FeedEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var name: String?,
    var isLive: Boolean?,
    var createdDateTime: Long?
) {
    constructor(name: String?, isLive: Boolean?, createdDateTime: Long?) : this(
        0,
        name,
        isLive,
        createdDateTime
    )
}
