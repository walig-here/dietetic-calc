import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../main.dart';
import '../widgets/not_interpreted_card.dart';
import '../widgets/interpreted_card.dart';
import '../widgets/header_bar.dart';
import '../widgets/button.dart';
import '../pages/form_page.dart';


class ResultsPage extends StatefulWidget {
  @override
  State<ResultsPage> createState() => _ResultsPageState();
}

class _ResultsPageState extends State<ResultsPage> {
  double bmi = 0;
  double bmr = 0;
  double totalKcal = 0;
  double waistToHeightRatio = 0;
  double waistToHipsRatio = 0;
  double proteinsGrams = 0;
  double fatsGrams = 0;
  double carbsGrams = 0;

  @override
  Widget build(BuildContext context) {
    var appState = context.watch<MyAppState>();

    _calculateBMI(appState.weightKg.toDouble(), appState.heightCm / 100.0);
    _calculateBMR(appState.gender, appState.ageYears.toDouble(), appState.heightCm.toDouble(), appState.weightKg.toDouble());
    _calculateTotalKcal(appState.activity);
    _calculateProteins();
    _calculateFats();
    _calculateCarbo();
    _calculateWaistHeightRation(appState.waistCm / 100.0, appState.heightCm / 100.0);
    _calculateWaistHipsRation(appState.waistCm / 100.0, appState.hipsCm / 100.0);

    return Scaffold(
      body: SafeArea(
        child: Container(
          color: Color(0xfff2f2f2),
          child: Column(
            children: [
              HeaderBar(title: 'Twoje wyniki'),
              Expanded(
                child: Padding(
                  padding: const EdgeInsets.all(30),
                  child: ListView(
                    children: [
                      NotInterpretedCard(
                        header: 'Zapotrzebowanie kaloryczne',
                        unit: "kcal",
                        value: totalKcal.toStringAsFixed(2),
                      ),
                      SizedBox(height: 16),
                      InterpretedCard(
                        header: 'BMI',
                        value: bmi.toStringAsFixed(2),
                        interpretation: _interpretBmi(),
                      ),
                      SizedBox(height: 16),
                      InterpretedCard(
                        header: 'Wskaźnik talia-biodra',
                        value: waistToHipsRatio.toStringAsFixed(2),
                        interpretation: _interpretWaistToHips(appState.gender)
                      ),
                      SizedBox(height: 16),
                      InterpretedCard(
                        header: 'Wskaźnik talia-wzrost',
                        value: waistToHeightRatio.toStringAsFixed(2),
                        interpretation: _interpretWaistToHeight(),
                      ),
                      SizedBox(height: 16),
                      NotInterpretedCard(
                        header: 'Zapotrzebowanie na węglowodany',
                        unit: "g",
                        value: carbsGrams.toStringAsFixed(2),
                      ),
                      SizedBox(height: 16),
                      NotInterpretedCard(
                        header: 'Zapotrzebowanie na białko',
                        unit: "g",
                        value: proteinsGrams.toStringAsFixed(2),
                      ),
                      SizedBox(height: 16),
                      NotInterpretedCard(
                        header: 'Zapotrzebowanie na tłuszcze',
                        unit: "g",
                        value: fatsGrams.toStringAsFixed(2),
                      ),
                      SizedBox(height: 16),
                      MyButton(
                        label: 'rozpocznij jeszcze raz',
                        handleOnPressed: (){
                          Navigator.pop(context);
                          Navigator.pushReplacement(
                            context, 
                            MaterialPageRoute(
                                builder: (context) => FormPage()
                              ),
                          );
                        }, 
                      )
                    ],
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  List _interpretWaistToHeight() {
    const MAX_HEALTHY = 0.5;
    const MAX_TEMPERATE_CENTRAL_ADIPOSITY = 0.6;
        
    if (waistToHeightRatio < MAX_HEALTHY){
        return [Color(0xff4D741B), "Brak otyłości brzusznej"];
    }if (waistToHeightRatio < MAX_TEMPERATE_CENTRAL_ADIPOSITY){
        return [Color(0xff666600), "Umiarkowana otyłość brzuszna"];
    }
    return [Color(0xff801818), "Zaawansowana otyłość brzuszna"];
  }

  List _interpretWaistToHips(String gender) {
    var MAX_NORMAL_WEIGHT = gender == "Mężczyzna" ? 0.9 : 0.8;
    var MAX_OVERWEIGHT = gender == "Mężczyzna" ? 1 : 0.85;

    if (waistToHipsRatio < MAX_NORMAL_WEIGHT){
        return [Color(0xff4B701A), "Optymalna masa ciała"];
    }
    if (waistToHipsRatio < MAX_OVERWEIGHT){
        return [Color(0xff6B6B00), "Nadwaga"];
    }
    return [Color(0xff801818), "Otyłość"];
  }

  List _interpretBmi() {
    if (bmi < 16){
      return [Color(0xff082e79), "Wygłodzenie"];
    }
    if (bmi < 17){
        return [Color(0xff305BDF), "Wychudzenie"];
    }
    if (bmi < 18.5){
        return [Color(0xff2A792C), "Niedowaga"];
    }
    if (bmi < 25.5){
        return [Color(0xff50781C), "Optymalna masa ciała"];
    }
    if (bmi < 30){
        return [Color(0xff6B6B00), "Nadwaga"];
    }
    if (bmi < 35){
        return [Color(0xff796702), "Otyłość I stopnia"];
    }
    if (bmi < 40){
        return [Color(0xffCF2929), "Otyłość II stopnia"];
    }
    return [Color(0xfffc801818), "Otyłość III stopnia"];
  }

  void _calculateBMR(String gender, double age, double heightCm, double weight){
    bmr = 10 * weight + 6.25 * heightCm + 5 * age;
    if(gender == "Mężczyzna"){
      bmr += 5;
    } else {
      bmr -= 161;
    }
  }

  void _calculateTotalKcal(String activity){
    final activityMultiplier = {
      "Brak": 1.2,
      "Mała": 1.4,
      "Umiarkowana": 1.6,
      "Reguralna": 1.75,
      "Codzienna": 2.0,
      "Zawodowa": 2.4
    }[activity] ?? 1.0;
    totalKcal = bmr * activityMultiplier;
  }

  void _calculateBMI(double weight, double heightMeters){
    bmi = weight / (heightMeters * heightMeters);
  }

  void _calculateWaistHeightRation(double waistMeters, double heightMeters){
    waistToHeightRatio = waistMeters / heightMeters;
  }

  void _calculateWaistHipsRation(double waistMeters, double hipsMeters){
    waistToHipsRatio = waistMeters / hipsMeters;
  }

  void _calculateProteins(){
    proteinsGrams = totalKcal * 0.225 / 4;
  }

  void _calculateFats(){
    fatsGrams = totalKcal * 0.275 / 9;
  }

  void _calculateCarbo(){
    carbsGrams = totalKcal * (1 - 0.275 - 0.225) / 4;
  }
}