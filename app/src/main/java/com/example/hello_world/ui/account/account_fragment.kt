package com.example.hello_world.ui.account

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.hello_world.R
import com.example.hello_world.databinding.FragmentAccountBinding
import com.example.hello_world.login_activity
import com.example.hello_world.security
import com.example.hello_world.simple_serialization

data class userdata_t(val username : String, val password: String)

class account_fragment : Fragment() {
  private val file_name = "session_file.txt"
  private var _binding: FragmentAccountBinding? = null
  private val binding get() = _binding!!

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

  fun make_user_data(map : Map<String, String>) : userdata_t? {
    val n = map["username"]
    val p = map["password"]
    if (n == null || p == null) return null
    return userdata_t(n, p)
  }

  fun load_userdata(ctx: Context) : userdata_t? {
    val file_content = security.load_file_content(ctx, file_name)
    if (file_content == "") return null

    val map = simple_serialization.load(file_content)
    return make_user_data(map)

//    val fields = file_content.split("\n")
//    return make_user_data(fields)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentAccountBinding.inflate(inflater, container, false)
    val root: View = binding.root

//    val dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
//    val textView: TextView = binding.textHome
//    dashboardViewModel.text.observe(viewLifecycleOwner) {
//      textView.text = it
//    }

    // проверяем наличие сессии на диске, если ее нет
    // то отправляем пользователя на страницу логина,
    // наверное здесь просто все загружаем, а отправляем в другой функции (onStart?)
    // наверное лучше данные пользователя не хранить между активити,
    // а просто из файла грузить каждый раз

    var userdata = load_userdata(activity!!.applicationContext)
    if (userdata == null) {
      val result_launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
          val data: Intent = result.data!!
          val username = data.extras!!.getString("username")!!
          val password = data.extras!!.getString("password")!!
          userdata = userdata_t(username, password)
        } else {
          // по идее мы можем из активити получить хост и из него получить контроллер
          val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
          val navController = navHostFragment.navController

          // переходим на начальный экран если нет логина
          navController.navigate(R.id.navigation_home)
        }
      }

      // отправим на страницу логина
      val intent = Intent(activity, login_activity::class.java)
      result_launcher.launch(intent)
      // а дальше как? по идее нам нужно получить данные из активити
      // наверное как то так
    } else {
      Toast.makeText(activity, "LOGIN SUCCESSFULLY", Toast.LENGTH_SHORT).show()
    }

    return root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}