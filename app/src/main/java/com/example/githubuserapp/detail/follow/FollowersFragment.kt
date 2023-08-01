package com.example.githubuserapp.detail.follow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.UserAdapter
import com.example.githubuserapp.detail.UserDetailActivity
import com.example.githubuserapp.databinding.FragmentFollowersBinding

class FollowersFragment: Fragment(R.layout.fragment_followers) {

    private var fragmentFollowersBinding: FragmentFollowersBinding? = null
    private val followersBinding get() = fragmentFollowersBinding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arguments = arguments
        username = arguments?.getString(UserDetailActivity.USERNAME).toString()

        fragmentFollowersBinding = FragmentFollowersBinding.bind(view)

        userAdapter = UserAdapter()


        followersBinding.apply {
            listFollow.setHasFixedSize(true)
            listFollow.layoutManager = LinearLayoutManager(activity)
            listFollow.adapter = userAdapter
        }

        setProgressBar(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowersViewModel::class.java)
        viewModel.setFollowers(username)

        viewModel.getFollowers().observe(viewLifecycleOwner,{
           if(it!=null){
               userAdapter.setListUser(it)
               setProgressBar(false)
           }
        })

        userAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentFollowersBinding = null
    }
    private fun setProgressBar(state: Boolean){
        if(state){
            followersBinding.followProgress.visibility = View.VISIBLE
        }else{
            followersBinding.followProgress.visibility = View.GONE
        }
    }
}