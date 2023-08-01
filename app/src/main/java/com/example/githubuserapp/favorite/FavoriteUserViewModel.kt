package com.example.githubuserapp.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.githubuserapp.database.UserFavoriteRepository


class FavoriteUserViewModel(application: Application): AndroidViewModel(application) {

    private var favoriteRepository: UserFavoriteRepository = UserFavoriteRepository(application)
    fun getUserRepository(): UserFavoriteRepository{
        return favoriteRepository
    }
}