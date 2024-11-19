import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'pages/form_page.dart';

const List<String> ACTIVITY = [
  "Brak",
  "Mała",
  "Umiarkowana",
  "Reguralna",
  "Codzienna",
  "Zawodowa"
];
const List<String> GENDERS = [
  "Mężczyzna", "Kobieta"
];
const int FORM_PAGE = 0;
const int RESULTS_PAGE = 1;


void main() {
  runApp(MyApp());
}


class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (context) => MyAppState(),
      child: MaterialApp(
        title: 'Dietetic Calc',
        home: FormPage(),
      ),
    );
  }
}


class MyAppState extends ChangeNotifier {
  String gender = "";
  int ageYears = -1;
  int weightKg = -1;
  int heightCm = -1;
  int waistCm = -1;
  int hipsCm = -1;
  String activity = "";

  void reset() {
    gender = "";
    ageYears = -1;
    weightKg = -1;
    heightCm = -1;
    waistCm = -1;
    hipsCm = -1;
    activity = "";
  }
  void setGender(String newGender) {
    gender = newGender;
    notifyListeners();
  }
  void setAge(int newAge) {
    ageYears = newAge;
    notifyListeners();
  }
  void setWeight(int newWeight) {
    weightKg = newWeight;
    notifyListeners();
  }
  void setHeight(int newHeight) {
    heightCm = newHeight;
    notifyListeners();
  }
  void setWaist(int newWaist) {
    waistCm = newWaist;
    notifyListeners();
  }
  void setHips(int newHips) {
    hipsCm = newHips;
    notifyListeners();
  }
  void setActivity(String newActivity) {
    activity = newActivity;
  }
}