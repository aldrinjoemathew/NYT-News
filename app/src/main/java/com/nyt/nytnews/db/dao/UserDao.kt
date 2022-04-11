package com.nyt.nytnews.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nyt.nytnews.db.entities.UserEntity
import com.nyt.nytnews.db.models.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity): Long

    @Query("SELECT name, email, dob FROM users WHERE email == :email AND password == :password LIMIT 1")
    suspend fun loginUser(email: String, password: String): User?
}