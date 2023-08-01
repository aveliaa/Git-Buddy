package com.example.githubuserapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserFavorite::class], version = 1)
abstract class UserFavoriteDatabase : RoomDatabase() {
    abstract fun UserFavoriteDao(): UserFavoriteDao

    companion object{
        @Volatile
        private var INSTANCE: UserFavoriteDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserFavoriteDatabase{
            if(INSTANCE == null){
                synchronized(UserFavoriteDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, UserFavoriteDatabase::class.java, "favorite_database")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as UserFavoriteDatabase
        }
    }
}

