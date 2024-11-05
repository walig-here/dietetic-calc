import { StyleSheet, View, ToastAndroid, ScrollView } from "react-native";
import Navbar from "@/components/Navbar";
import LabeledNumberInput from "@/components/LabeledNumberInput";
import LabeledSelectionMenu from "@/components/LabeledSelectionMenu";
import Button from "@/components/Button";
import { SafeAreaView } from "react-native-safe-area-context";
import {GENDERS_OPTIONS} from "@/constants/genders"
import {ACTIVITY_OPTIONS} from "@/constants/activity"
import { useState } from "react";
import { router } from "expo-router";

export default function FormScreen() {
  const [userData, setUserData] = useState({
    gender: {value: "", isValid: false},
    age: {value: "", isValid: false},
    weight: {value: "", isValid: false},
    height: {value: "", isValid: false},
    waist: {value: "", isValid: false},
    activity: {value: "", isValid: false},
  });
  const [isFormValid, setIsFormValid] = useState(false);

  const handleUserDataChange = (changedKey, value, isValid) => {
    setUserData(prevUserData => {
      return {
      ...prevUserData,
      [changedKey]: {
        value: value,
        isValid: isValid
      }
      }
    });
    setIsFormValid(
      Object.keys(userData).filter(
        key => (key === changedKey && !isValid) || (key !== changedKey && !userData[key].isValid)
      ).length === 0);
  }

  return (
    <SafeAreaView style={{flex: 1}}>
      <Navbar title={"Uzupełnij dane"} loaded_icon={require("@/assets/images/form_icon.svg")}/>
      <ScrollView style={{flex: 10}}>
        <View style={styles.form}>
          <LabeledSelectionMenu
            label={"Płeć"}
            data={GENDERS_OPTIONS}
            placeholder={"Wybierz płeć"}
            setUserInput={(value, isValid) => handleUserDataChange("gender", value, isValid)}
          />
          <LabeledNumberInput 
            label={"Wiek (lata)"}
            setUserInput={(value, isValid) => handleUserDataChange("age", value, isValid)}
          />
          <LabeledNumberInput 
            label={"Waga (kg)"}
            setUserInput={(value, isValid) => handleUserDataChange("weight", value, isValid)}
          />
          <LabeledNumberInput 
            label={"Wzrost (cm)"}
            setUserInput={(value, isValid) => handleUserDataChange("height", value, isValid)}
          />
          <LabeledNumberInput 
            label={"Talia (cm)"}
            setUserInput={(value, isValid) => handleUserDataChange("waist", value, isValid)}
          />
          <LabeledSelectionMenu 
            label={"Aktywność fizyczna"}
            data={ACTIVITY_OPTIONS} 
            placeholder={"Wybierz poziom aktywności"}
            setUserInput={(value, isValid) => handleUserDataChange("activity", value, isValid)}
          />
          <Button 
            title={"OBLICZ RAPORT"} 
            onClick={
              isFormValid ?
              () => router.push({pathname: "/report", params: {userData: JSON.stringify(userData)}}) :
              () => ToastAndroid.show("Podane dane nie są poprawne!", ToastAndroid.SHORT)
            }
          />
          <Button title={"WRÓC"} onClick={() => 1}/>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}


const styles = StyleSheet.create({
  form: {
    rowGap: 20,
    paddingHorizontal: 45,
    paddingTop: 30,
    paddingBottom: 30,
    alignItems: 'center',
    justifyContent: 'center', 
    flexGrow: 1,
    width: "100%"
  }
})