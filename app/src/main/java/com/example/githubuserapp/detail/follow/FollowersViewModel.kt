package com.example.githubuserapp.detail.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.api.ApiConfig
import com.example.githubuserapp.response.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {

    private val followers = MutableLiveData<ArrayList<User>>()

    fun setFollowers(user: String){
        val retrofit = ApiConfig.getRetrofit()
            .getUserFollowers(user)
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                   if(response.isSuccessful)
                       followers.postValue(response.body())
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure",t.message!!)
                }

            })
    }

    fun getFollowers(): LiveData<ArrayList<User>>{
        return followers
    }
}