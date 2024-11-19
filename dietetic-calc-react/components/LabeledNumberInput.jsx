import { useState } from "react"
import { View, Text, TextInput, StyleSheet } from "react-native"

export default function LabeledNumberInput({label, setUserInput}) {
    const [value, setValue] = useState("");
    const [isFocus, setIsFocus] = useState(false);

    const isValidValue = (value) => {
        return value !== null && value !== "" && !isNaN(value)
    };

    const inputStyles = [styles.textField];
    if (isFocus)
        inputStyles.push(styles.focused);
    if (!isValidValue(value))
        inputStyles.push(styles.invalid);

    const onValueChanged = newValue => {
        setValue(newValue);
        setUserInput(newValue, isValidValue(newValue));
    };

    return (
        <View style={styles.panel}>
            <Text style={styles.label}>{label}</Text>
            <TextInput
                editable
                onChangeText={text => onValueChanged(text)}
                value={value}
                style={inputStyles}
                keyboardType="numeric"
                onFocus={() => setIsFocus(true)}
                onBlur={() => setIsFocus(false)}
            />
        </View>
    );
}


const styles = StyleSheet.create({
    textField: {
        height: 50,
        borderColor: 'gray',
        borderWidth: 0.5,
        borderRadius: 8,
        paddingLeft: 16,
        paddingRight: 12,
        paddingVertical: 12,
        fontSize: 16
    },
    focused: {
        borderColor: 'blue',
        borderWidth: 1,
    },
    label: {
        fontSize: 16
    },
    invalid: {
        borderColor: 'red',
    },
    panel: {
        rowGap: 8,
        padding: 0,
        width: "100%"
    },
})