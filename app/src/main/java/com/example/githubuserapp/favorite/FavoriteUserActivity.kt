package com.example.githubuserapp.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.UserAdapter
import com.example.githubuserapp.database.UserFavorite
import com.example.githubuserapp.databinding.ActivityFavoriteUserBinding
import com.example.githubuserapp.detail.UserDetailActivity
import com.example.githubuserapp.response.User
import java.util.ArrayList

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteUserBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "My Favorite User"

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteUserViewModel::class.java)
        adapter.setOnClickItemCallback(object : UserAdapter.OnClickItemCallback{
            override fun onClickItem(user: User) {
                Intent(this@FavoriteUserActivity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.USERNAME,user.login)
                    it.putExtra(UserDetailActivity.ID, user.id)
                    it.putExtra(UserDetailActivity.AVATAR,user.avatar_url)
                    startActivity(it)
                }
            }

        })

        binding.apply {
            favoriteList.setHasFixedSize(true)
            favoriteList.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
            favoriteList.adapter = adapter
        }

        val repository = viewModel.getUserRepository()
        repository.getFavorites()?.observe(this,{
            if(it != null){
                val favoriteList = getFavoriteList(it)
                adapter.setListUser(favoriteList)
            }
        })
    }

    private fun getFavoriteList(users: List<UserFavorite>): ArrayList<User> {
        val result = ArrayList<User>()
        for(user in users){
            val foundUser = User(
                user.avatar_url,
                user.id,
                user.login
            )
            result.add(foundUser)
        }
        return result
    }
}