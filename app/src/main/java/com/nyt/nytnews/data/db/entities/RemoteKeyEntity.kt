package com.nyt.nytnews.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey val repoId: String,
    val prevKey: Int?,
    val nextKey: Int?
)