import { View, Text, StyleSheet } from "react-native"; 


export default function InterpretedMetricDisplay({header, interpretationFunction, value}) {
    const [color, description] = interpretationFunction(value);

    return (
        <View style={styles.card}>
            <Text style={styles.header}>{header}</Text>
            <Text style={[styles.value, {color: color}]}>{value.toFixed(2)}</Text>
            <Text style={styles.description}>{description}</Text>
        </View>
    )
}


const styles = StyleSheet.create({
    header: {
        fontSize: 20,
        textAlign: 'center'
    },
    value: {
        fontSize: 24,
        fontWeight: "bold"
    },
    description: {
        fontSize: 16
    },
    card: {
        rowGap: 4,
        borderColor: 'gray',
        borderWidth: 0.5,
        width: "100%",
        padding: 4,
        borderRadius: 8,
        alignItems: 'center',
        justifyContent: 'center', 
    }
})