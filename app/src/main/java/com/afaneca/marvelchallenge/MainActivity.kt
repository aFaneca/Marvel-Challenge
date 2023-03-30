package com.afaneca.marvelchallenge

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.afaneca.marvelchallenge.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(null)
        setContentView(binding.root)
        setupBottomNav()
        binding.navView.itemIconTintList = null
    }

    private fun getNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        return navHostFragment.navController
    }

    private fun setupBottomNav(){
        val navView: BottomNavigationView = binding.navView

        val navController = getNavController()

        navView.setupWithNavController(navController)
    }
    fun showTopError(message: String) {
        binding.tvTopError.text = message
        binding.cvTopError.isVisible = true
    }

    fun hideTopError() {
        binding.cvTopError.isVisible = false
    }

}