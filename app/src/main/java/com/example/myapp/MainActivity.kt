package com.example.myapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var btn_calc: Button
    lateinit var et_age: EditText
    lateinit var et_weight: EditText
    lateinit var et_height: EditText
    lateinit var et_waist: EditText
    lateinit var et_hips: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_calc = findViewById(R.id.btn_calc)
        et_age = findViewById(R.id.et_age)
        et_height = findViewById(R.id.et_height)
        et_weight = findViewById(R.id.et_weight)
        et_waist = findViewById(R.id.et_waist)
        et_hips = findViewById(R.id.et_hips)

        btn_calc.setOnClickListener(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_genders, DropdownFragment())
                .commit()
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_activity, DropdownFragment_activity())
                .commit()
        }

        setupDynamicValidation()
    }

    override fun onClick(v: View?) {
        if (!validateInput()) {
            return
        }

        val genderFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_genders) as? DropdownFragment
        val activityFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_activity) as? DropdownFragment_activity

        val selectedGender = genderFragment?.getSelectedGender()
        val selectedActivity = activityFragment?.getSelectedActivity()

        val intent = Intent(this, ResultsActivity::class.java)
        intent.putExtra("age", et_age.text.toString().toInt())
        intent.putExtra("gender", selectedGender)
        intent.putExtra("activity", selectedActivity)
        intent.putExtra("height", et_height.text.toString().toInt())
        intent.putExtra("weight", et_weight.text.toString().toInt())
        intent.putExtra("waist", et_waist.text.toString().toInt())
        intent.putExtra("hips", et_hips.text.toString().toInt())
        startActivity(intent)
    }

    private fun setupDynamicValidation() {
        val numberFields = listOf(et_age, et_weight, et_height, et_waist, et_hips)

        numberFields.forEach { numberField ->
            numberField.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    validateNumberField(numberField)
                }
            })
            numberField.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    validateNumberField(numberField)
                }
            }
            validateNumberField(numberField)
        }
    }

    private fun validateNumberField(numberField: EditText): Boolean {
        return if (numberField.text.isEmpty() || numberField.text.toString().toIntOrNull() == null) {
            numberField.setBackgroundResource(R.drawable.custom_input_error)
            false
        } else {
            numberField.setBackgroundResource(R.drawable.custom_input)
            true
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true
        val activityFragment = supportFragmentManager.findFragmentById(
            R.id.fragment_container_activity
        ) as? DropdownFragment_activity
        val isActivityValid = activityFragment?.validateActivityField() ?: false
        val genderFragment = supportFragmentManager.findFragmentById(
            R.id.fragment_container_genders
        ) as? DropdownFragment
        val isGenderValid = genderFragment?.validateActivityField() ?: false

        isValid = isGenderValid && isValid
        isValid = isActivityValid && isValid
        isValid = validateNumberField(et_age) && isValid
        isValid = validateNumberField(et_weight) && isValid
        isValid = validateNumberField(et_height) && isValid
        isValid = validateNumberField(et_waist) && isValid
        isValid = validateNumberField(et_hips) && isValid

        return isValid
    }
}
