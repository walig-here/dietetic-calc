import { SafeAreaView } from "react-native-safe-area-context";
import {StyleSheet, ScrollView, View, Text} from "react-native";
import Navbar from "@/components/Navbar";
import MetricDisplay from "@/components/MetricDisplay";
import InterpretedMetricDisplay from "@/components/InterpretedMetricDisplay";
import Button from "@/components/Button";
import { router, useLocalSearchParams } from "expo-router";
import {MALE} from "@/constants/genders"
import {NO_ACTIVITY, SMALL_ACTIVITY, MODERATE_ACTIVITY, REGULAR_ACTIVITY, DAILY_ACTIVITY, PROFESSIONAL_ACTIVITY} from "@/constants/activity"


const PROTEINS_PERCENTAGE = 0.225;
const FATS_PERCENTAGE = 0.275;
const PROTEINS_CALORIES_PER_GRAM = 4;
const FATS_CALORIES_PER_GRAM = 9;
const CARBS_CALORIES_PER_GRAM = 4;


export default function ReportScreen() {
    const {serializedUserData} = useLocalSearchParams();
    const userData = JSON.parse(serializedUserData);

    // source: https://pl.wikipedia.org/wiki/Wska%C5%BAnik_masy_cia%C5%82a
    const calculateBMI = () => {
        return Number(userData.weight.value) / (Number(userData.height.value) ** 2);
    };
    const interpretBMI = (bmi) => {
        if (bmi < 16)
            return ["#082e79", "Wygłodzenie"];
        if (bmi < 17)
            return ["#4169e1", "Wychudzenie"];
        if (bmi < 18.5)
            return ["#ace1af", "Niedowaga"];
        if (bmi < 25.5)
            return ["#cdeba7", "Optymalna masa ciała"];
        if (bmi < 30)
            return ["#ffff99", "Nadwaga"];
        if (bmi < 35)
            return ["#fde456", "Otyłość I stopnia"];
        if (bmi < 40)
            return ["#cf2929", "Otyłość II stopnia"];
        return ["#801818", "Otyłość III stopnia"];
    }

    // source: https://en.wikipedia.org/wiki/Waist%E2%80%93hip_ratio
    const calculateWHR = () => {
        return Number(userData.waist.value) / Number(userData.hips.value);
    }
    const interpretWHR = (whr) => {
        const MAX_NORMAL_WEIGHT = userData.height.gender == MALE ? 0.9 : 0.8;
        const MAX_OVERWEIGHT = userData.height.gender == MALE ? 1 : 0.85;

        if (whr < MAX_NORMAL_WEIGHT)
            return ["#cdeba7", "Optymalna masa ciała"];
        if (whr < MAX_OVERWEIGHT)
            return ["#ffff99", "Nadwaga"];
        return ["#801818", "Otyłość"];
    }

    // source: https://en.wikipedia.org/wiki/Waist-to-height_ratio
    const calculateWHTR = () => {
        return Number(userData.waist.value) / Number(userData.height.value);
    }
    const interpretWHTR = (whtr) => {
        const MAX_HEALTHY = 0.5
        const MAX_TEMPERATE_CENTRAL_ADIPOSITY = 0.6
        
        if (whtr < MAX_HEALTHY)
            return ["#cdeba7", "Brak otyłości brzusznej"];
        if (whtr < MAX_TEMPERATE_CENTRAL_ADIPOSITY)
            return ["#ffff99", "Umiarkowana otyłość brzuszna"];
        return ["#801818", "Zaawansowana otyłość brzuszna"];
    }

    // source: https://receptomat.pl/post/zo/zapotrzebowanie-kaloryczne
    const calculateCPM = () => {
        const PPM = (
            10 * Number(userData.weight.value) +
            625 * Number(userData.height.value) +
            5 * Number(userData.age.value) +
            (userData.gender.value == MALE ? 5 : -161)
        );
        switch (Number(userData.activity.value)) {
            case NO_ACTIVITY:
                return 1.2 * PPM;
            case SMALL_ACTIVITY:
                return 1.4 * PPM;
            case MODERATE_ACTIVITY:
                return 1.6 * PPM;
            case REGULAR_ACTIVITY:
                return 1.75 * PPM;
            case DAILY_ACTIVITY:
                return 2.0 * PPM;
            case PROFESSIONAL_ACTIVITY:
                return 2.4 * PPM;
            default:
                return PPM;
        }
    }
    const cpm = calculateCPM();
    
    // source: https://www.healthline.com/nutrition/how-to-count-macros#step-by-step
    const calculateProteins = (cpm) => (cpm * PROTEINS_PERCENTAGE) / PROTEINS_CALORIES_PER_GRAM;
    const calculateFats = (cpm) =>  (cpm * FATS_PERCENTAGE) / FATS_CALORIES_PER_GRAM;
    const calculateCarbs = (cpm) => (cpm * (1 - FATS_PERCENTAGE - PROTEINS_PERCENTAGE)) / CARBS_CALORIES_PER_GRAM;

    return (
        <SafeAreaView style={{flex: 1}}>
            <Navbar title={"Twoje wyniki"}/>
            <ScrollView style={{flex: 10}}>
                <View style={styles.results}>
                    <InterpretedMetricDisplay
                        header={"BMI"}
                        interpretationFunction={interpretBMI}
                        value={calculateBMI()}
                    />
                    <InterpretedMetricDisplay
                        header={"Wskaźnik talia-biodra"}
                        interpretationFunction={interpretWHR}
                        value={calculateWHR()}
                    />
                    <InterpretedMetricDisplay
                        header={"Wskaźnik talia-wzrost"}
                        interpretationFunction={interpretWHTR}
                        value={calculateWHTR()}
                    />
                    <MetricDisplay
                        header={"Zapotrzebowanie kaloryczne"}
                        value={cpm}
                        unit={"kcal"}
                    />
                    <MetricDisplay
                        header={"Zapotrzebowanie na węglowodany"}
                        value={calculateCarbs(cpm)}
                        unit={"g"}
                    />
                    <MetricDisplay
                        header={"Zapotrzebowanie na białko"}
                        value={calculateProteins(cpm)}
                        unit={"g"}
                    />
                    <MetricDisplay
                        header={"Zapotrzebowanie na tłuszcze"}
                        value={calculateFats(cpm)}
                        unit={"g"}
                    />
                    <Button 
                        title={"ROZPOCZNIJ JESZCZE RAZ"} 
                        onClick={() => router.replace("/")}
                    />
                </View>
            </ScrollView>
        </SafeAreaView>
    )
}

const styles = StyleSheet.create({
    results: {
        rowGap: 16,
        paddingHorizontal: 30,
        paddingTop: 30,
        paddingBottom: 30,
        alignItems: 'center',
        justifyContent: 'center', 
        flexGrow: 1,
        width: "100%"
    }
});