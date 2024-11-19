import { View, Text, StyleSheet } from "react-native";


export default function MetricDisplay({header, value, unit}) {
    return (
        <View style={styles.card}>
            <Text style={styles.header}>{header}</Text>
            <Text style={styles.value}>{`${value.toFixed(2)} ${unit}`}</Text>
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