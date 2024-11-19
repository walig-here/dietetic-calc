import { Text, StyleSheet } from "react-native"
import {Shadow} from 'react-native-shadow-2';


export default function Navbar({title}) {
    return (
        <Shadow 
            style={styles.navbar} 
            sides={{"bottom": true, "top": false, end: false, start: false}} 
            startColor="#00000050"
            distance={6}
        >
            <Text style={styles.header}>{title}</Text>
        </Shadow>
    )
}


const styles = StyleSheet.create({
    navbar: {
        flexDirection: "row",
        justifyContent: "flex-start",
        paddingHorizontal: 15,
        paddingTop: 8,
        paddingBottom: 4,
        columnGap: 100,
        height: 70,
        width: "100%",
        alignItems: "center",
    },
    header: {
        fontSize: 28,
        flex: 1,
    },
    icon: {
        height: 48,
        width: 48,
        resizeMode: "contain",
    },
})