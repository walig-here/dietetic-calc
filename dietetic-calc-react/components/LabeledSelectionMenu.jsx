import { useState } from "react";
import { View, Text, StyleSheet } from "react-native"
import { Dropdown } from 'react-native-element-dropdown';


export default function LabeledSelectionMenu({data, placeholder, label, setUserInput}) {
    const [value, setValue] = useState("");
    const [isFocus, setIsFocus] = useState(false);

    const dropdownStyles = [styles.dropdown];
    if (value === "")
        dropdownStyles.push(styles.invalid);
    if (isFocus)
        dropdownStyles.push(styles.focused);

    const onValueChanged = (newValue) => {
        setValue(newValue);
        setIsFocus(false);
        setUserInput(newValue, true);
    }

    return (
        <View style={styles.panel}>
            <Text style={styles.label}>{label}</Text>
            <Dropdown 
                data={data}
                value={value}
                onChange={item => onValueChanged(item.value)}
                labelField="label"
                valueField="value"
                placeholder={placeholder}
                onFocus={() => setIsFocus(true)}
                onBlur={() => setIsFocus(false)}
                style={dropdownStyles}
            />
        </View>
    );
}


const styles = StyleSheet.create({
    dropdown: {
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
    invalid: {
        borderColor: 'red',
    },
    panel: {
        rowGap: 8,
        padding: 0,
        width: "100%"
    },
    label: {
        fontSize: 16
    }
})