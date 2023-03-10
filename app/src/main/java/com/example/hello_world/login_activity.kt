package com.example.hello_world

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class login_activity : AppCompatActivity() {
  private val file_name = "session_file.txt"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // set the user interface layout for this activity
    // the layout file is defined in the project res/layout/main_activity.xml file
    setContentView(R.layout.activity_login)

    run {
      val toolbar = findViewById<Toolbar>(R.id.toolbar)
      setSupportActionBar(toolbar)
      val appbar_text = toolbar.findViewById<TextView>(R.id.view_title)
      appbar_text.text = resources.getString(R.string.title_account)
      val appbar_back = toolbar.findViewById<ImageButton>(R.id.back)
      appbar_back.setOnClickListener { finish() }
    }

    supportActionBar?.let {
      it.setDisplayShowHomeEnabled(false)
      it.setDisplayShowTitleEnabled(false)
    }

    val username = findViewById<TextView>(R.id.username)
    val password = findViewById<TextView>(R.id.password)

    val button = findViewById<Button>(R.id.login)
    button.setOnClickListener {
      val username_text = username.text.toString()
      val password_text = password.text.toString()
      if (username_text == "admin" && password_text == "admin") {
        Toast.makeText(this, "LOGIN SUCCESSFULLY", Toast.LENGTH_SHORT).show()
        val file_content = simple_serialization.save(mapOf("username" to username_text, "password" to password_text))
        //val file_content = "username=$username_text\npassword=$password_text"
        security.create_file(applicationContext, file_name, file_content)
        val intent = Intent()
        intent.putExtra("username", username_text)
        intent.putExtra("password", password_text)
        setResult(RESULT_OK, intent)
        finish()
      } else {
        Toast.makeText(this, "LOGIN FAILED", Toast.LENGTH_SHORT).show()
      }
    }

    // ?????????? ???? ?????????????????????? ?????? ???? ?????????????????? ???????????? ?? ???????????????????? + ?????????????? ?????????? ???????????? ???????? ???? ????????
    //
  }
}