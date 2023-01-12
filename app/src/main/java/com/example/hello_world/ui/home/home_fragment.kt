package com.example.hello_world.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.hello_world.databinding.FragmentHomeBinding

class home_fragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val home_view_model = ViewModelProvider(this).[home_view_model::class.java]
//        val textView: TextView = binding.textHome
//        home_view_model.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}