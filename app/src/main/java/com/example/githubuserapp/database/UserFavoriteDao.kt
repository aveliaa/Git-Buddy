package com.example.githubuserapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// To store queries

@Dao
interface UserFavoriteDao {

    @Query("SELECT * FROM User_favorite")
    fun getFavorite(): LiveData<List<UserFavorite>>

    @Insert
    fun setFavorite(user: UserFavorite)

    @Query("DELETE FROM User_favorite as user WHERE user.id = :id")
    fun removeFavorite(id: Int): Int

    // to check if user exist in favorite
    @Query("SELECT COUNT(*) FROM User_favorite as user WHERE user.id = :id")
    fun countUserExitence(id: Int): Int

}