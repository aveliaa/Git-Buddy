package com.example.githubuserapp.detail

//import android.R
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.PagerAdapter
import com.example.githubuserapp.database.UserFavoriteRepository
import com.example.githubuserapp.databinding.ActivityUserDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserDetailActivity() : AppCompatActivity() {

    private lateinit var activityBinding: ActivityUserDetailBinding
    private lateinit var detailViewModel: UserDetailViewModel
    private lateinit var favoriteRepository: UserFavoriteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)

        val username = intent.getStringExtra(USERNAME)
        val avatar = intent.getStringExtra(AVATAR)
        val id = intent.getIntExtra(ID,0)

        val bundle = Bundle()
        bundle.putString(USERNAME,username)

        setProgressBar(true)
        detailViewModel = ViewModelProvider(this).get(UserDetailViewModel::class.java)
        detailViewModel.setUserDetail(username!!)
        detailViewModel.getUserDetail().observe(this,{
            if(it != null){
                activityBinding.apply {
                    profileUsername.text = it.login
                    profileFollowers.text = resources.getString(R.string.followers_amount, it.followers.toString())
                    profileFollowing.text = resources.getString(R.string.following_amount, it.following.toString())
                    Glide.with(this@UserDetailActivity)
                        .load(it.avatar_url)
                        .centerCrop()
                        .into(profilePicture)

                    var name: String? = null

                    if(it.name != null){
                        name = it.name.toString()
                    }else{
                        name = it.login
                    }

                    profileName.text = name

                    supportActionBar?.title = name
                    setProgressBar(false)

                }
            }
        })


        favoriteRepository = detailViewModel.getUserRepository()

        var checked = false
        CoroutineScope(Dispatchers.IO).launch {
            var existence = favoriteRepository.checkFavorite(id)
            withContext(Dispatchers.Main){
                if(existence != null){

                    if(existence == 0){
                        // not exist yet
                        activityBinding.favoriteToggle.isChecked = false
                        checked = false
                    } else {
                        // already existed
                        activityBinding.favoriteToggle.isChecked = true
                        checked = true
                    }
                }
            }
        }
        activityBinding.favoriteToggle.setOnClickListener {

            checked = !checked
            if(checked){
                Log.d("ADD", "masukin user")
                favoriteRepository.addFavorite(username,id,avatar!!)
            }else{
                Log.d("REMOVE","apus user")
                favoriteRepository.removeFavorite(id)
            }
            activityBinding.favoriteToggle.isChecked = checked
        }

        val pagerAdapter = PagerAdapter(this,supportFragmentManager,bundle)
        activityBinding.apply {
            profilePager.adapter = pagerAdapter
            profileTab.setupWithViewPager(profilePager)
        }

    }

    companion object{
        const val USERNAME = "username"
        const val ID = "id"
        const val AVATAR = "avatar"
    }

    private fun setProgressBar(state: Boolean){
        if(state){
            activityBinding.progress.visibility = View.VISIBLE
        }else{
            activityBinding.progress.visibility = View.GONE
        }
    }


}