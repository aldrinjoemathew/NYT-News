package com.nyt.nytnews.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nyt.nytnews.data.db.entities.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>): Void

    @Query("SELECT * FROM remote_keys WHERE repoId = :repoId")
    suspend fun remoteKeysRepoId(repoId: String): RemoteKeys

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys(): Int
}