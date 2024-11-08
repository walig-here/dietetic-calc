package com.example.mobilki_poczatek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mobilki_poczatek.databinding.ActivityMainBinding
import com.example.mobilki_poczatek.databinding.DropdownActivityBinding
import com.example.mobilki_poczatek.databinding.FragmentDropdownBinding

class DropdownFragment_activity : Fragment(){
    private var _binding: DropdownActivityBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DropdownActivityBinding.inflate(inflater, container, false)

        val activity = resources.getStringArray(R.array.activity)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, activity)
        binding.autoCompleteTextViewActivity.setAdapter(arrayAdapter)

        binding.autoCompleteTextViewActivity.setOnClickListener{
            binding.activity.hint = null
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getSelectedActivity(): String{
        return binding.autoCompleteTextViewActivity.text.toString()
    }

}