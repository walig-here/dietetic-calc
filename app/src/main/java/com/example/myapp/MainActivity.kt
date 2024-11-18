package com.example.myapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapp.ui.theme.Myapp_Theme
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainer


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var btn_calc : Button
    lateinit var et_age : EditText
    lateinit var et_weight : EditText
    lateinit var  et_height :EditText
    lateinit var et_waist :EditText
    lateinit var et_hips :EditText


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

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_genders, DropdownFragment())
                .commit()
        }

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_activity, DropdownFragment_activity())
                .commit()
        }

    }

    override fun onClick(v: View?) {
        if(!validateInput()){
            return
        }

        val genderFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_genders) as? DropdownFragment
        val activityFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_activity) as? DropdownFragment_activity

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

    private fun validateInput(): Boolean{
        var isValid = true

        et_age.setBackgroundResource(R.drawable.custom_input)
        et_weight.setBackgroundResource(R.drawable.custom_input)
        et_height.setBackgroundResource(R.drawable.custom_input)
        et_hips.setBackgroundResource(R.drawable.custom_input)
        et_waist.setBackgroundResource(R.drawable.custom_input)
        val genderFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_genders) as? DropdownFragment
        val activityFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_activity) as? DropdownFragment_activity
        val selectedGender = genderFragment?.getSelectedGender()
        val selectedActivity = activityFragment?.getSelectedActivity()

        if(et_age.text.isEmpty() or (et_age.text.toString().toIntOrNull() == null)){
            et_age.setBackgroundResource(R.drawable.custom_input_error)
            isValid = false
            }

        if(et_weight.text.isEmpty() or (et_weight.text.toString().toIntOrNull() == null)){
            et_weight.setBackgroundResource(R.drawable.custom_input_error)
            isValid = false
        }

        if(et_height.text.isEmpty() or (et_height.text.toString().toIntOrNull() == null)){
            et_height.setBackgroundResource(R.drawable.custom_input_error)
            isValid = false
        }

        if(et_hips.text.isEmpty() or (et_hips.text.toString().toIntOrNull() == null)){
            et_hips.setBackgroundResource(R.drawable.custom_input_error)
            isValid = false
        }

        if(et_waist.text.isEmpty() or (et_waist.text.toString().toIntOrNull() == null)){
            et_waist.setBackgroundResource(R.drawable.custom_input_error)
            isValid = false
        }

        if (selectedGender?.isEmpty() == true) {
            genderFragment.binding.autoCompleteTextViewGenders.setBackgroundResource(R.drawable.custom_input_error)
        } else {
            genderFragment?.binding?.autoCompleteTextViewGenders?.setBackgroundResource(R.drawable.custom_input)
        }

        if (selectedActivity?.isEmpty() == true) {
            activityFragment.binding.autoCompleteTextViewActivity.setBackgroundResource(R.drawable.custom_input_error)
            isValid = false
        } else {
            activityFragment?.binding?.autoCompleteTextViewActivity?.setBackgroundResource(R.drawable.custom_input)
        }

        return isValid
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Myapp_Theme{
        Greeting("Android")
    }
}