package com.example.hello_world

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hello_world.databinding.ActivityMainBinding
import java.util.Locale

class main : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    println("Current locale:  ${Locale.getDefault()}")
    println("Current locales: ${resources.configuration.locales}")

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val navView: BottomNavigationView = binding.navView

    val navHostFragment = supportFragmentManager.findFragmentById(
      R.id.nav_host_fragment_activity_main
    ) as NavHostFragment
    val navController = navHostFragment.navController

    //val navController = findNavController(R.id.nav_host_fragment_activity_main)
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    val appBarConfiguration = AppBarConfiguration(
      setOf(
        R.id.navigation_home, R.id.navigation_services, R.id.navigation_account, R.id.navigation_settings
      )
    )

    setupActionBarWithNavController(navController, appBarConfiguration)
    navView.setupWithNavController(navController)
  }

  override fun onStart() {
    super.onStart()
    // грузим тут или в onResume? от чего это вообще зависит? не уверен
  }

  override fun onResume() {
    super.onResume()

  }
}