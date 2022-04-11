package com.nyt.nytnews.db.models

import androidx.room.ColumnInfo

data class User(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "dob") val dob: String
)
