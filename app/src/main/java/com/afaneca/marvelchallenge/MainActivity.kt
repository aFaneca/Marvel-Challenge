package com.afaneca.marvelchallenge

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.transition.Explode
import com.afaneca.marvelchallenge.databinding.ActivityMainBinding
import com.afaneca.marvelchallenge.ui.utils.setVisibilityWithAnimation
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(null)
        setContentView(binding.root)
        setupBottomNav()
    }

    override fun onResume() {
        super.onResume()
        getNavController().addOnDestinationChangedListener(this)
    }

    override fun onPause() {
        super.onPause()
        getNavController().removeOnDestinationChangedListener(this)
    }


    // Control the visibility of the bottom nav by observing navigation changes
    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        // Bottom nav should ONLY be visible in characters and about views
        val shouldBecomeVisible = when (destination.id) {
            R.id.navigation_characters, R.id.navigation_about -> true
            else -> false
        }
        binding.navView.setVisibilityWithAnimation(
            parentView = binding.root as ViewGroup,
            shouldBecomeVisible = shouldBecomeVisible,
            duration = 200L,
            transition = Explode()
        )
    }

    private fun getNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        return navHostFragment.navController
    }

    private fun setupBottomNav() {
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