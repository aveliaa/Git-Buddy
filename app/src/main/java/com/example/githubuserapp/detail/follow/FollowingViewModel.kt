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

class FollowingViewModel: ViewModel() {

    val following = MutableLiveData<ArrayList<User>>()

    fun setFollowing(user: String){
        val retrofit = ApiConfig.getRetrofit()
            .getUserFollowing(user)
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                   if(response.isSuccessful)
                       following.postValue(response.body())
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure",t.message!!)
                }

            })
    }

    fun getFollowing(): LiveData<ArrayList<User>>{
        return following
    }
}