package com.example.hello_world.ui.settings

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.example.hello_world.R
import com.example.hello_world.databinding.FragmentSettingsBinding
import java.util.*

// это пригодится когда я перепишу обновление языка
fun setAppLocale(ctx: Context, resources: Resources, language: String): Context {
  val locale = Locale(language)
  Locale.setDefault(locale)
  val config = resources.configuration
  config.setLocale(locale)
  config.setLayoutDirection(locale)
  return ctx.createConfigurationContext(config)
}

class settings_fragment : Fragment() {
  private var _binding: FragmentSettingsBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentSettingsBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val lang_settings_button = root.findViewById<Button>(R.id.lang_settings)
    lang_settings_button.setOnClickListener {
      // попап надо бы переделать, непонимаю пока как
      show_lang_menu(it)
    }

    return root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  fun get_locale_code_from_id(id: Int) = when(id) {
    R.id.ru -> "ru"
    R.id.kk -> "kk"
    R.id.en -> "en"
    else -> "en"
  }

  fun show_lang_menu(v: View) {
    val config = resources.configuration
    //val current_locale = config.locales[0]!!

    val popupMenu = PopupMenu(activity, v)
    popupMenu.inflate(R.menu.languages_menu)

    popupMenu.setOnMenuItemClickListener {
      val locale = Locale(get_locale_code_from_id(it.itemId))
      println("Chosen locale: $locale, current locale: ${Locale.getDefault()}")
      if (locale == Locale.getDefault()) return@setOnMenuItemClickListener true

      // короч старый вариант, лучше пересобирать контекст, но там нужно много чего написать
      // курить https://stackoverflow.com/questions/54471100/android-setting-locale-programmatically-in-the-new-way-alters-context
      Locale.setDefault(locale)
      config.setLocale(locale)
      config.setLayoutDirection(locale)
      activity?.let {
        it.baseContext?.resources?.updateConfiguration(config, resources.displayMetrics)
        it.finish()
        it.overridePendingTransition(0,0)
        it.startActivity(it.intent)
        it.overridePendingTransition(0,0)
      }

      println("sign in str: ${activity?.baseContext?.resources?.getString(R.string.action_sign_in_short)}")

      return@setOnMenuItemClickListener true
    }

    popupMenu.gravity = Gravity.CENTER
    popupMenu.show()
  }
}