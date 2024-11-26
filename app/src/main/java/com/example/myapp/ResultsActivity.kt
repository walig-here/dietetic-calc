package com.example.myapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapp.ui.theme.Myapp_Theme



class ResultsActivity : ComponentActivity() {

    lateinit var results_calories: TextView
    lateinit var results_bmi: TextView
    lateinit var results_bmi_info: TextView
    lateinit var results_whr_info: TextView
    lateinit var results_wthr_info: TextView
    lateinit var results_waistHips: TextView
    lateinit var results_waistHeight: TextView
    lateinit var results_carbo: TextView
    lateinit var results_proteins: TextView
    lateinit var results_fats: TextView
    lateinit var btn_back: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val age = intent.getIntExtra("age", 0)
        val height = intent.getIntExtra("height", 0) / 100.0
        val weight = intent.getIntExtra("weight", 0)
        val gender = intent.getStringExtra("gender") ?: "Nieznana"
        val activity = intent.getStringExtra("activity") ?: "Nieznana"
        val waist =  intent.getIntExtra("waist", 0) / 100.0
        val hips = intent.getIntExtra("hips", 0) / 100.0
        btn_back = findViewById(R.id.btn_back)

        btn_back.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        })


        val bmr = calculateBMR(gender, age, height, weight)
        val totalCalories = calculateTotalKcal(bmr, activity)
        val formatedTotalCalories = "%.2f".format(totalCalories)
        results_calories = findViewById(R.id.results_calories)
        results_calories.text = formatedTotalCalories.toString() + " kcal"

        val bmi = calculateBMI(weight, height)

        val formatedBmi = "%.2f".format(bmi)
        results_bmi = findViewById(R.id.results_bmi)
        results_bmi_info = findViewById(R.id.results_bmi_info)
        results_bmi.text = formatedBmi.toString()
        updateBmiResults(bmi, results_bmi, results_bmi_info)

        val waistHipsRatio = calculateWaistRatio(waist, hips)
        val formatedWaistHipsRatio = "%.2f".format(waistHipsRatio)
        results_waistHips = findViewById(R.id.results_waistHips)
        results_whr_info = findViewById(R.id.results_waistHips_info)
        results_waistHips.text = formatedWaistHipsRatio.toString()
        updateWhrResults(gender, waistHipsRatio, results_waistHips, results_whr_info)

        val waistHeightRatio = calculateWaistRatio(waist, height)
        val formatedWaistHeightRatio = "%.2f".format(waistHeightRatio)
        results_waistHeight = findViewById(R.id.results_waistHeight)
        results_wthr_info = findViewById(R.id.results_waistHeight_info)
        results_waistHeight.text = formatedWaistHeightRatio.toString()
        updateWthrResults(waistHeightRatio, results_waistHeight, results_wthr_info)


        val totalProteins = calculateProteins(totalCalories)
        val formatedtotalProteins = "%.2f".format(totalProteins)
        results_proteins = findViewById(R.id.results_proteins)
        results_proteins.text = formatedtotalProteins.toString() + " g"

        val totalFats = calculateFats(totalCalories)
        val formatedtotalFats = "%.2f".format(totalFats)
        results_fats = findViewById(R.id.results_fats)
        results_fats.text = formatedtotalFats.toString() + " g"

        val totalCarbo = calculateCarbo(totalCalories, totalProteins, totalFats)
        val formatedtotalCarbo = "%.2f".format(totalCarbo)
        results_carbo = findViewById(R.id.results_carbo)
        results_carbo.text = formatedtotalCarbo.toString() + " g"

    }


    private fun calculateBMR(gender: String, age: Int, height: Double, weight: Int): Double{
        return if(gender == "Mężczyzna"){
            10.0 * weight + 625.0 * height + 5.0 * age + 5.0
        }else{
            10.0 * weight + 625.0 * height + 5.0 * age - 161.0
        }
    }

    private fun calculateTotalKcal(bmr: Double, activity: String): Double{
        val activityMultiplier = when(activity){
            "Brak" -> 1.2
            "Mała" -> 1.4
            "Umiarkowana" -> 1.6
            "Reguralna" -> 1.75
            "Codzienna" -> 2.0
            "Zawodowa" -> 2.4
            else -> 1.0
        }
        return bmr * activityMultiplier
    }

    private fun calculateBMI(weight: Int, height: Double): Double{

        return weight / (height * height)
    }

    private fun calculateWaistRatio(waist: Double, other: Double): Double{
        return (waist / other)
    }


    var PROTEINS_PERCENTAGE = 0.225;
    var FATS_PERCENTAGE = 0.275;
    var PROTEINS_CALORIES_PER_GRAM = 4;
    var FATS_CALORIES_PER_GRAM = 9;
    var CARBS_CALORIES_PER_GRAM = 4;

    private fun calculateProteins(totalCalories: Double): Double{
        return (totalCalories * PROTEINS_PERCENTAGE) / PROTEINS_CALORIES_PER_GRAM
    }

    private fun calculateFats(totalCalories: Double): Double{
        return (totalCalories * FATS_PERCENTAGE) / FATS_CALORIES_PER_GRAM
    }

    private fun calculateCarbo(totalCalories: Double, totalProteins: Double, totalFats: Double): Double{
        return (totalCalories * (1 - FATS_PERCENTAGE - PROTEINS_PERCENTAGE)) / CARBS_CALORIES_PER_GRAM
    }

    fun updateBmiResults(bmi: Double, bmiTextView: TextView, descriptionTextView: TextView){
        val (bmiColor, bmiDescription) = when{
            bmi < 16.0 -> Pair(Color.parseColor("#082e79"), "Wygłodzenie")
            bmi >= 16.0 && bmi < 17.0 -> Pair(Color.parseColor("#305BDF"), "Wychudzenie")
            bmi >= 17.0 && bmi < 18.5 -> Pair(Color.parseColor("#2A792C"), "Niedowaga")
            bmi >= 18.5 && bmi < 25.5 -> Pair(Color.parseColor("#50781C"), "Optymalna masa ciała")
            bmi >= 25.5 && bmi < 30.0 -> Pair(Color.parseColor("#6B6B00"), "Nadwaga")
            bmi >= 30.0 && bmi < 35.0 -> Pair(Color.parseColor("#796702"), "Otyłość I stopnia")
            bmi >= 35.0 && bmi < 40.0 -> Pair(Color.parseColor("#CF2929"), "Otyłość II stopnia")
            else -> Pair(Color.parseColor("#801818"), "Otyłość III stopnia")
        }
        bmiTextView.setTextColor(bmiColor)
        descriptionTextView.text = bmiDescription
    }

    fun updateWhrResults(gender: String, whr: Double, whrTextView: TextView, descriptionTextView: TextView){
        var MAX_NORMAL_WEIGHT = 0.0
        var MAX_OVERWEIGHT = 0.0

        if(gender == "Mężczyzna"){
            MAX_NORMAL_WEIGHT = 0.9
            MAX_OVERWEIGHT = 1.0
        }else{
            MAX_NORMAL_WEIGHT = 0.8
            MAX_OVERWEIGHT = 0.85
        }
        val (whrColor, whrDescription) = when{
            whr < MAX_NORMAL_WEIGHT -> Pair(Color.parseColor("#4B701A"), "Optymalna masa ciała")
            whr >= MAX_NORMAL_WEIGHT && whr < MAX_OVERWEIGHT -> Pair(Color.parseColor("#6B6B00"), "Nadwaga")
            else -> Pair(Color.parseColor("#801818"), "Otyłość")
        }
        whrTextView.setTextColor(whrColor)
        descriptionTextView.text = whrDescription
    }

    fun updateWthrResults(wthr: Double, wthrTextView: TextView, descriptionTextView: TextView){
        val (wthrColor, wthrDescription) = when{
            wthr < 0.5 -> Pair(Color.parseColor("#4D741B"), "Brak otyłości brzusznej")
            wthr >= 0.5 && wthr < 0.6 -> Pair(Color.parseColor("#666600"), "Umiarkowana otyłość brzuszna")
            else -> Pair(Color.parseColor("#801818"), "Zaawansowana otyłość brzuszna")
        }
        wthrTextView.setTextColor(wthrColor)
        descriptionTextView.text = wthrDescription
    }
}