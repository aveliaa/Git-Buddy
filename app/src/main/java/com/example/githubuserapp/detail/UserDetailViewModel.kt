package com.example.githubuserapp.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.api.ApiConfig
import com.example.githubuserapp.database.UserFavoriteRepository
import com.example.githubuserapp.response.UserDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class UserDetailViewModel(application: Application): AndroidViewModel(application) {
    val user = MutableLiveData<UserDetailResponse>()
    private var favoriteRepository: UserFavoriteRepository = UserFavoriteRepository(application)

    fun setUserDetail(username: String){
        val retrofit = ApiConfig.getRetrofit()
            .getDetailUser(username)
            .enqueue(object : Callback<UserDetailResponse>{
                override fun onResponse(
                    call: Call<UserDetailResponse>,
                    response: Response<UserDetailResponse>
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                    Log.d("Failure",t.message!!)
                }

            })
    }

    fun getUserRepository(): UserFavoriteRepository{
        return favoriteRepository
    }

    fun getUserDetail(): LiveData<UserDetailResponse>{
        return user
    }
}