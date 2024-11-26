import 'package:flutter/material.dart';
import 'package:my_first_flutter_app/pages/results_page.dart';
import 'package:provider/provider.dart';

import '../main.dart';
import '../widgets/labeled_number_input.dart';
import '../widgets/header_bar.dart';
import '../widgets/labeled_selection_menu.dart';
import '../widgets/button.dart';


class FormPage extends StatefulWidget {
  @override
  State<FormPage> createState() => _FormPageState();
}

class _FormPageState extends State<FormPage> {
  var genderValid = false;
  var ageValid = false;
  var weightValid = false;
  var heightValid = false;
  var waistValid = false;
  var hipsValid = false;
  var activityValid = false;

  @override
  Widget build(BuildContext context) {
    var appState = context.watch<MyAppState>();

    return Scaffold(
      body: SafeArea(
        child: Container(
          color: Color(0xfff2f2f2),
          child: Column(
            children: [
              HeaderBar(title: 'Uzupełnij dane'),
              Expanded(
                child: Padding(
                  padding: const EdgeInsets.symmetric(horizontal:40, vertical:30),
                  child: ListView(
                    children: [
                      LabeledSelectionMenu(
                        options: GENDERS,
                        label: "Płeć",
                        placeholder: "Wybierz płeć",
                        handleOnSelected: (newGender, valid) {
                          appState.setGender(newGender);
                          setState(() {
                            genderValid = valid;
                          });
                        },
                      ),
                      SizedBox(height: 20),
                      LabeledNumberInput(
                        labelText: 'Wiek (lata)',
                        handleOnChanged: (newAge, valid) {
                          appState.setAge(newAge);
                          setState(() {
                            ageValid = valid;
                          });
                        },
                      ),
                      SizedBox(height: 20),
                      LabeledNumberInput(
                        labelText: 'Waga (kg)', 
                        handleOnChanged: (newWeight, valid) {
                          appState.setWeight(newWeight);
                          setState(() {
                            weightValid = valid;
                          });
                        },
                      ),
                      SizedBox(height: 20),
                      LabeledNumberInput(
                        labelText: 'Wzrost (cm)', 
                        handleOnChanged: (newHeight, valid) {
                          appState.setHeight(newHeight);
                          setState(() {
                            heightValid = valid;
                          });
                        },
                      ),
                      SizedBox(height: 20),
                      LabeledNumberInput(
                        labelText: 'Obwód w tali (cm)', 
                        handleOnChanged: (newWaist, valid) {
                          appState.setWaist(newWaist);
                          setState(() {
                            waistValid = valid;
                          });
                        },
                      ),
                      SizedBox(height: 20),
                      LabeledNumberInput(
                        labelText: 'Obwód w biodrach (cm)', 
                        handleOnChanged: (newHips, valid) {
                          appState.setHips(newHips);
                          setState(() {
                            hipsValid = valid;
                          });
                        },
                      ),
                      SizedBox(height: 20),
                      Container(
                        width: double.infinity,
                        child: LabeledSelectionMenu(
                          options: ACTIVITY, 
                          label: "Aktywność fizyczna", 
                          placeholder: 'Wybierz poziom aktywności',
                          handleOnSelected: (newActivity, valid) {
                            appState.setActivity(newActivity);
                            setState(() {
                              activityValid = valid;
                            });
                          },
                        ),
                      ),
                      SizedBox(height: 20),
                      MyButton(
                        label: 'oblicz raport',
                        handleOnPressed: () {
                          if (_formValid()) {
                            Navigator.push(
                              context,
                              MaterialPageRoute(
                                builder: (context) => ResultsPage()
                              ),
                            );
                          }
                        }
                      ),
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

  bool _formValid() {
    return genderValid && ageValid && weightValid && heightValid && waistValid && activityValid && hipsValid;
  }
}