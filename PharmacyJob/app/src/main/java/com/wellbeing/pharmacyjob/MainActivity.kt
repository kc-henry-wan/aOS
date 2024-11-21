package com.wellbeing.pharmacyjob

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wellbeing.pharmacyjob.api.SessionManager
import com.wellbeing.pharmacyjob.databinding.ActivityMainBinding
import com.wellbeing.pharmacyjob.view.AvailablejobFragment
import com.wellbeing.pharmacyjob.view.LoginActivity
import com.wellbeing.pharmacyjob.view.MyfavoriteFragment
import com.wellbeing.pharmacyjob.view.MyjobFragment
import com.wellbeing.pharmacyjob.view.NegotiationFragment
import com.wellbeing.pharmacyjob.view.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            // Check session status
            AppLogger.d("MainActivity", "Check session status")
            if (!SessionManager.isLoggedIn(this)) {
                AppLogger.d("MainActivity", "SessionManager.isLoggedIn = false")
                navigateToLogin()
                return
            }

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

//        supportActionBar?.hide()
            setSupportActionBar(binding.actiontoolbar)

            val bottomNavigationView: BottomNavigationView = binding.navView
//        loadFragment(AvailablejobFragment())
            bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                var selectedFragment: Fragment? = null
                when (item.itemId) {
                    R.id.navigation_availablejob -> selectedFragment = AvailablejobFragment()
                    R.id.navigation_myfavorite -> selectedFragment = MyfavoriteFragment()
                    R.id.navigation_myjob -> selectedFragment = MyjobFragment()
                    R.id.navigation_negotiation -> selectedFragment = NegotiationFragment()
                    R.id.navigation_profile -> selectedFragment = ProfileFragment()
                }
                if (selectedFragment != null) {
                    loadFragment(selectedFragment)
                }
                true
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_activity_main, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

        showBackButton(false)
    }

    private fun navigateToLogin() {
        // Clear fragment back stack
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Close MainActivity
    }

    // Call this method when session times out
    fun logoutUser() {
        SessionManager.logout(this)
        navigateToLogin()
    }

    // Inflate the menu to add items to the action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            // Handle back button press
            this.onBackPressed()  // Trigger the default back action
            true
        }

        R.id.action_logout -> {
            logoutUser()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        // Custom back navigation behavior if needed
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()  // Pop the fragment from the stack
        } else {
            super.onBackPressed()  // Default system back action
        }
    }

    fun showBackButton(show: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(show)
    }
}
