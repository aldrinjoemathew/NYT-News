package com.nyt.nytnews.data.db.models

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "dob") val dob: String
)
