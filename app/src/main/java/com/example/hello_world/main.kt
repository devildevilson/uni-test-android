package com.example.hello_world

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hello_world.databinding.ActivityMainBinding

class main : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    //println("Current locale:  ${Locale.getDefault()}")
    //println("Current locales: ${resources.configuration.locales}")

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    run {
      val toolbar = findViewById<Toolbar>(R.id.toolbar)
      setSupportActionBar(toolbar)
    }

    supportActionBar?.let {
      it.setDisplayShowHomeEnabled(false)
      it.setDisplayShowTitleEnabled(false)
    }

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

//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//      window.insetsController?.hide(WindowInsets.Type.statusBars())
//    } else {
//      window.setFlags(
//        WindowManager.LayoutParams.FLAG_FULLSCREEN,
//        WindowManager.LayoutParams.FLAG_FULLSCREEN
//      )
//    }
  }

  override fun onStart() {
    super.onStart()
    // грузим тут или в onResume? от чего это вообще зависит? не уверен
  }

  override fun onResume() {
    super.onResume()

  }
}