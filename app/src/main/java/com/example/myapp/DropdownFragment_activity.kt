package com.example.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.myapp.databinding.DropdownActivityBinding

class DropdownFragment_activity : Fragment() {
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

        binding.autoCompleteTextViewActivity.setOnClickListener {
            validateActivityField()
            binding.activity.hint = null
        }

        validateActivityField()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getSelectedActivity(): String {
        return binding.autoCompleteTextViewActivity.text.toString()
    }

    fun validateActivityField(): Boolean {
        val isValid = binding.autoCompleteTextViewActivity.text.isNotEmpty()

        if (isValid) {
            binding.autoCompleteTextViewActivity.setBackgroundResource(R.drawable.custom_input)
        } else {
            binding.autoCompleteTextViewActivity.setBackgroundResource(R.drawable.custom_input_error)
        }

        return isValid
    }
}
