package com.nyt.nytnews.domain.models

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "dob") val dob: String,
    @ColumnInfo(name = "image_path") val imagePath: String?,
)
