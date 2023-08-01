package com.example.githubuserapp.api

import com.example.githubuserapp.response.User
import com.example.githubuserapp.response.UserDetailResponse
import com.example.githubuserapp.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}