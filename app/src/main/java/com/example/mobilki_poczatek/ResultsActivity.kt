package com.example.mobilki_poczatek

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
import com.example.mobilki_poczatek.ui.theme.Mobilki_poczatekTheme



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
        val height = intent.getIntExtra("height", 0)
        val weight = intent.getIntExtra("weight", 0)
        val gender = intent.getStringExtra("gender") ?: "Nieznana"
        val activity = intent.getStringExtra("activity") ?: "Nieznana"
        val waist =  intent.getIntExtra("waist", 0)
        val hips = intent.getIntExtra("hips", 0)
        btn_back = findViewById(R.id.btn_back)

        btn_back.setOnClickListener({
            finish()
        })


        val bmr = calculateBMR(gender, age, height, weight)
        val totalCalories = calculateTotalKcal(bmr, activity)
        val formatedTotalCalories = "%.2f".format(totalCalories)
        results_calories = findViewById(R.id.results_calories)
        results_calories.text = formatedTotalCalories.toString()

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
        updateWhrResults(waistHipsRatio, results_waistHips, results_whr_info)

        val waistHeightRatio = calculateWaistRatio(waist, height)
        val formatedWaistHeightRatio = "%.2f".format(waistHeightRatio)
        results_waistHeight = findViewById(R.id.results_waistHeight)
        results_wthr_info = findViewById(R.id.results_waistHeight_info)
        results_waistHeight.text = formatedWaistHeightRatio.toString()
        updateWthrResults(waistHeightRatio, results_waistHeight, results_wthr_info)


        val totalProteins = calculateProteins(totalCalories)
        val formatedtotalProteins = "%.2f".format(totalProteins)
        results_proteins = findViewById(R.id.results_proteins)
        results_proteins.text = formatedtotalProteins.toString()

        val totalFats = calculateFats(totalCalories)
        val formatedtotalFats = "%.2f".format(totalFats)
        results_fats = findViewById(R.id.results_fats)
        results_fats.text = formatedtotalFats.toString()

        val totalCarbo = calculateCarbo(totalCalories, totalProteins, totalFats)
        val formatedtotalCarbo = "%.2f".format(totalCarbo)
        results_carbo = findViewById(R.id.results_carbo)
        results_carbo.text = formatedtotalCarbo.toString()

    }


    private fun calculateBMR(gender: String, age: Int, height: Int, weight: Int): Double{
        return if(gender == "Mężczyzna"){
            66.5 + (13.75 * weight) + (5.003 * height) - (6.775 * age)
        }else{
            655.1 + (9.563 * weight) + (1.85 * height) - (4.677 * age)
        }
    }

    private fun calculateTotalKcal(bmr: Double, activity: String): Double{
        val activityMultiplier = when(activity){
            "Brak" -> 1.0
            "Ćwiczenia 1 raz w tygodniu" -> 1.2
            "Ćwiczenia 2 razy w tygodniu" -> 1.375
            "Ćwiczenia kilka razy w tygodniu" -> 1.55
            else -> 1.0
        }
        return bmr * activityMultiplier
    }

    private fun calculateBMI(weight: Int, height: Int): Double{
        val heightInMeters = height / 100.0

        return weight / (heightInMeters * heightInMeters)
    }

    private fun calculateWaistRatio(waist: Int, other: Int): Double{
        return (waist / other.toDouble()) * 100
    }

    private fun calculateProteins(totalCalories: Double): Double{
        return totalCalories * 0.2
    }

    private fun calculateFats(totalCalories: Double): Double{
        return totalCalories * 0.3
    }

    private fun calculateCarbo(totalCalories: Double, totalProteins: Double, totalFats: Double): Double{
        return totalCalories - totalProteins - totalFats
    }

    fun updateBmiResults(bmi: Double, bmiTextView: TextView, descriptionTextView: TextView){
        val (bmiColor, bmiDescription) = when{
            bmi < 18.5 -> Pair(Color.parseColor("#2196F3"), "Niedowaga")
            bmi >= 18.5 && bmi < 25.0 -> Pair(Color.parseColor("#4CAF50"), "Waga prawidłowa")
            bmi >= 25.0 && bmi < 30.0 -> Pair(Color.parseColor("#FFEB3B"), "Nadwaga")
            bmi >= 30.0 && bmi < 35.0 -> Pair(Color.parseColor("#FF9800"), "Otyłość I stopnia")
            bmi >= 35.0 && bmi < 40.0 -> Pair(Color.parseColor("#F44336"), "Otyłość II stopnia")
            else -> Pair(Color.parseColor("#B71C1C"), "Otyłość III stopnia")
        }
        bmiTextView.setTextColor(bmiColor)
        descriptionTextView.text = bmiDescription
    }

    fun updateWhrResults(whr: Double, whrTextView: TextView, descriptionTextView: TextView){
        val (whrColor, whrDescription) = when{
            whr < 90.0 -> Pair(Color.parseColor("#4CAF50"), "Niskie ryzyko chorób metabolicznych")
            whr >= 90.0 && whr < 100.0 -> Pair(Color.parseColor("#FFEB3B"), "Średnie ryzyko chorób metabolicznych")
            else -> Pair(Color.parseColor("#F44336"), "Wysokie ryzyko chorób metabolicznych")
        }
        whrTextView.setTextColor(whrColor)
        descriptionTextView.text = whrDescription
    }

    fun updateWthrResults(wthr: Double, wthrTextView: TextView, descriptionTextView: TextView){
        val (wthrColor, wthrDescription) = when{
            wthr < 50.0 -> Pair(Color.parseColor("#4CAF50"), "Optymalny stosunek talia-wzrost")
            wthr >= 50.0 && wthr < 60.0 -> Pair(Color.parseColor("#FFEB3B"), "Umiarkowane ryzyko zdrowotne")
            else -> Pair(Color.parseColor("#F44336"), "Wysokie ryzyko zdrowotne")
        }
        wthrTextView.setTextColor(wthrColor)
        descriptionTextView.text = wthrDescription
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    Mobilki_poczatekTheme {
        Greeting2("Android")
    }
}