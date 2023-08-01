package com.example.githubuserapp.database

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// Connect Database and DAO

class UserFavoriteRepository(application: Application) {
    private val dao: UserFavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init{
        val database = UserFavoriteDatabase.getDatabase(application)
        dao = database.UserFavoriteDao()
    }

    fun addFavorite(username: String, id: Int, avatar: String){
        executorService.execute {
            val user = UserFavorite(id,username,avatar)
            dao?.setFavorite(user)
        }
    }

    fun removeFavorite(id: Int){
        executorService.execute{
            dao.removeFavorite(id)
        }
    }

    fun checkFavorite(id: Int): Int{
        return dao.countUserExitence(id)
    }

    fun getFavorites(): LiveData<List<UserFavorite>>{
        return dao.getFavorite()
    }

}