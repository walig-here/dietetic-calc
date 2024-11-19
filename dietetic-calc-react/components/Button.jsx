import { useState } from "react";
import { Pressable, StyleSheet, Text } from "react-native";


export default function Button({title, onClick}) {
    const [isPressed, setIsPressed] = useState(false);
    
    const buttonStyles = [styles.button];
    if (isPressed)
        buttonStyles.push(styles.pressedButton);

    const handlePress = () => {
        setIsPressed(false);
        onClick();
    }

    return (
        <Pressable style={buttonStyles} onPressOut={event => handlePress()} onPressIn={event => setIsPressed(true)}>
            <Text style={styles.label}>{title}</Text>
        </Pressable>
    );
}


const styles = StyleSheet.create({
    button: {
        backgroundColor: "#2c2c2c",
        borderRadius: 8,
        alignItems: 'center',
        padding: 12,
        width: 240
    },
    pressedButton: {
        backgroundColor: "black"
    },
    label: {
        color: "#f5f5f5",
        fontSize: 14,
        fontWeight: "semibold"
    }
})