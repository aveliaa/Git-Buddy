package com.example.githubuserapp.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.githubuserapp.R
import com.example.githubuserapp.detail.follow.FollowersFragment
import com.example.githubuserapp.detail.follow.FollowingFragment

class PagerAdapter(private val context: Context, manager: FragmentManager, data: Bundle): FragmentPagerAdapter(manager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private  var bundle: Bundle
    init{
        bundle = data
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var pagerFragment: Fragment? = null
        pagerFragment = if(position == 0){
            FollowersFragment()
        } else{
            FollowingFragment()
        }
        pagerFragment?.arguments = this.bundle
        return pagerFragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> return context.resources.getString(R.string.followers)
            1 -> return context.resources.getString(R.string.following)
        }
        return null
    }


}