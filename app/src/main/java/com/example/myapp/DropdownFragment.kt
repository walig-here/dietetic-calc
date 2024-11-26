package com.example.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.databinding.FragmentDropdownBinding

class DropdownFragment : Fragment(){
    private var _binding: FragmentDropdownBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDropdownBinding.inflate(inflater, container, false)

        val genders = resources.getStringArray(R.array.genders)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, genders)
        binding.autoCompleteTextViewGenders.setAdapter(arrayAdapter)

        binding.autoCompleteTextViewGenders.setOnClickListener{
            binding.genders.hint = null
            validateActivityField()
        }

        validateActivityField()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun validateActivityField(): Boolean {
        val isValid = binding.autoCompleteTextViewGenders.text.isNotEmpty()

        if (isValid) {
            binding.autoCompleteTextViewGenders.setBackgroundResource(R.drawable.custom_input)
        } else {
            binding.autoCompleteTextViewGenders.setBackgroundResource(R.drawable.custom_input_error)
        }

        return isValid
    }

    fun getSelectedGender(): String{
        return binding.autoCompleteTextViewGenders.text.toString()
    }
}