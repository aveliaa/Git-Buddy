package com.example.githubuserapp.main
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.detail.UserDetailActivity
import com.example.githubuserapp.adapter.UserAdapter
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.favorite.FavoriteUserActivity
import com.example.githubuserapp.mode.SettingPreferences
import com.example.githubuserapp.mode.SwitchModeActivity
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.githubuserapp.response.User

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity() : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.my_favorite -> {
                Intent(this, FavoriteUserActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.my_mode -> {
                Intent(this, SwitchModeActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        val pref = SettingPreferences.getInstance(dataStore)
        val modeViewModel = ViewModelProvider(this, MainModelFactory(pref)).get(
            MainViewModel::class.java
        )
        setTheme(modeViewModel)
        supportActionBar?.title = "Git Buddy"

        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        userAdapter.setOnClickItemCallback(object : UserAdapter.OnClickItemCallback{
            override fun onClickItem(user: User) {
                Intent(this@MainActivity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.USERNAME,user.login)
                    it.putExtra(UserDetailActivity.ID, user.id)
                    it.putExtra(UserDetailActivity.AVATAR,user.avatar_url)
                    startActivity(it)
                }
            }

        })
        mainViewModel = ViewModelProvider(this,MainModelFactory(pref)).get(
            MainViewModel::class.java)

        activityMainBinding.apply {
            listUser.layoutManager = LinearLayoutManager(this@MainActivity)
            listUser.setHasFixedSize(true)
            listUser.adapter = userAdapter

            searchIconButton.setOnClickListener {
                search()
            }

            searchField.setOnKeyListener { view, i, keyEvent ->
                if(i == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN){
                    search()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false

            }
        }

        mainViewModel.getSearchResult().observe(this,{
            if(it!=null){

                userAdapter.setListUser(it)
                setProgressBar(false)
                setGreeting(false)

                if(it.isEmpty()){
                    setNotFound(true)
                }else{
                    setNotFound(false)
                }
            }
        })

    }

    private fun search(){
        activityMainBinding.apply {
            val input = searchField.text.toString()

            if(input.isNotEmpty()){
                setProgressBar(true)
                mainViewModel.setSearchResult(input)

            }else return
        }
    }

    // visual effect : loading progress bar
    private fun setProgressBar(state: Boolean){
        activityMainBinding.progress.visibility = if (state) View.VISIBLE else View.GONE
    }
    // visual effect : user not found
    private fun setNotFound(state: Boolean){
        activityMainBinding.notFound.visibility = if (state) View.VISIBLE else View.GONE
    }
    // visual effect : greeting
    private fun setGreeting(state: Boolean){
        activityMainBinding.greeting.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun setTheme(model:MainViewModel){
        model.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}