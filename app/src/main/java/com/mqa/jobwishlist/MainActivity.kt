package com.mqa.jobwishlist

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.mqa.jobwishlist.core.utils.Constant.Companion.DEEPLINK_SEARCH
import com.mqa.jobwishlist.databinding.ActivityMainBinding
import com.mqa.jobwishlist.ui.home.HomeFragment
import com.mqa.jobwishlist.ui.wishlist.WishlistFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        binding.appBarMain.content.btnPartTime.visibility = View.GONE
        binding.appBarMain.content.btnFullTime.visibility = View.GONE

        val toggle = ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                binding.appBarMain.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, HomeFragment())
                    .commit()
            supportActionBar?.title = getString(R.string.app_name)
        }

        binding.appBarMain.ivSearch.setOnClickListener {
            val uri = Uri.parse(DEEPLINK_SEARCH)
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        var title = getString(R.string.app_name)
        when (item.itemId) {
            R.id.nav_home -> {
                fragment = HomeFragment()
                binding.appBarMain.content.btnPartTime.visibility = View.GONE
                binding.appBarMain.content.btnFullTime.visibility = View.GONE
                title = getString(R.string.app_name)
            }
            R.id.nav_favorite -> {
                fragment = WishlistFragment()
                binding.appBarMain.content.btnPartTime.visibility = View.GONE
                binding.appBarMain.content.btnFullTime.visibility = View.GONE
                title = getString(R.string.menu_favorite)
            }
        }
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit()
        }
        supportActionBar?.title = title

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}