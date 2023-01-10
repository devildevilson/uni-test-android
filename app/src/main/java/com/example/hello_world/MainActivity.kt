package com.example.hello_world

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hello_world.databinding.ActivityMainBinding

data class userdata_t(val username : String, val password: String)

class MainActivity : AppCompatActivity() {
  private val file_name = "session_file.txt"
  private val login_activity_code = 1
  private lateinit var binding: ActivityMainBinding

  fun make_user_data(fields : List<String>) : userdata_t? {
    var un = ""
    var p = ""
    for (field in fields) {
      val pair = field.split("=")
      if (pair[0] == "username") un = pair[1]
      if (pair[0] == "password") p = pair[1]
    }

    if (un == "" || p == "") return null
    return userdata_t(un, p)
  }

  fun load_userdata() : userdata_t? {
    val file_content = security.load_file_content(applicationContext, file_name)
    if (file_content == "") return null

    val fields = file_content.split("\n")
    return make_user_data(fields)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // проверяем наличие сессии на диске, если ее нет
    // то отправляем пользователя на страницу логина,
    // наверное здесь просто все загружаем, а отправляем в другой функции (onStart?)

    var userdata = load_userdata()
    if (userdata == null) {
      val result_launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
          // There are no request codes
          val data: Intent = result.data!!
          val username = data.extras!!.getString("username")!!
          val password = data.extras!!.getString("password")!!
          userdata = userdata_t(username, password)
        }
      }

      // отправим на страницу логина
      val intent = Intent(this, login_activity::class.java)
      result_launcher.launch(intent)
      // а дальше как? по идее нам нужно получить данные из активити
      // наверное как то так
    } else {
      Toast.makeText(this, "LOGIN SUCCESSFULLY", Toast.LENGTH_SHORT).show()
    }

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