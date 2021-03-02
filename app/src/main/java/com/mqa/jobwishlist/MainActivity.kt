package com.mqa.jobwishlist

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mqa.jobwishlist.core.utils.Constant.Companion.DEEPLINK_SEARCH
import com.mqa.jobwishlist.core.utils.Constant.Companion.DEEPLINK_WISHLIST
import com.mqa.jobwishlist.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val toggle = ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                binding.appBarMain.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.navView.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.nav_favorite -> {
                    val uri = Uri.parse(DEEPLINK_WISHLIST)
                    navController.navigate(uri)
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }
        binding.navView.setupWithNavController(navController)

        binding.appBarMain.ivSearch.setOnClickListener {
            val uri = Uri.parse(DEEPLINK_SEARCH)
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

}