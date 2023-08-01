package com.example.githubuserapp.response

data class UserResponse(
    val incomplete_results: Boolean,
    val items: ArrayList<User>,
    val total_count: Int
)