package com.example.hello_world.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hello_world.databinding.FragmentAccountBinding

class account_fragment : Fragment() {
  private var _binding: FragmentAccountBinding? = null
  private val binding get() = _binding!!

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

    return root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}