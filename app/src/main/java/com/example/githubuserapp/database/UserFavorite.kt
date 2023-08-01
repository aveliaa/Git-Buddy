package com.example.githubuserapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// To store entity

@Entity("User_favorite")
data class UserFavorite(
    @PrimaryKey
    @ColumnInfo("id")
    val id:Int,
    @ColumnInfo(name = "username")
    val login: String,
    @ColumnInfo(name = "avatar")
    val avatar_url: String
) : Serializable
