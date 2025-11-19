import java.util.Stack;
import java.util.Map;
import java.util.HashMap;

public class BracketValidator {

    public static boolean isValid(String s) {

    }

    public static void main(String[] args) {
        String testString1 = "({[((([[{[{]}]]}]))])}";
        String testString2 = "()[]{}";
        String testString3 = "({[]})";

        System.out.println(testString1 + " is valid: " + isValid(testString1)); // false
        System.out.println(testString2 + " is valid: " + isValid(testString2)); // true
        System.out.println(testString3 + " is valid: " + isValid(testString3)); // true
    }
}