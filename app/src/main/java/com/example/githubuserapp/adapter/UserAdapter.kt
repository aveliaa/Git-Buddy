package com.example.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuserapp.databinding.ItemUserBinding
import com.example.githubuserapp.response.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    private var onClickItemCallback: OnClickItemCallback? = null
    fun setOnClickItemCallback(onClickItemCallback: OnClickItemCallback){
        this.onClickItemCallback = onClickItemCallback
    }

    private val listUser = ArrayList<User>()
    fun setListUser(users: ArrayList<User>){
        // reset list
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.root.setOnClickListener{
                onClickItemCallback?.onClickItem(user)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade(2))
                    .centerCrop()
                    .into(searchUserAvatar)

                searchUserName.text = user.login
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder((
                ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                ))
    }

    override fun getItemCount(): Int {
       return listUser.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    interface OnClickItemCallback {
        fun onClickItem(user: User)
    }
}