import java.util.Stack;
import java.util.Map;
import java.util.HashMap;

public class BracketValidator {

    public static boolean isValid(String s) {
        // Используем стек для отслеживания открывающих скобок
        Stack<Character> stack = new Stack<>();

        // Создаем словарь для быстрого сопоставления закрывающих и открывающих скобок
        Map<Character, Character> bracketPairs = new HashMap<>();
        bracketPairs.put(')', '(');
        bracketPairs.put(']', '[');
        bracketPairs.put('}', '{');

        // Проходим по каждому символу в строке
        for (char currentBracket : s.toCharArray()) {
            // Если текущий символ - закрывающая скобка
            if (bracketPairs.containsKey(currentBracket)) {
                // Проверяем, не пуст ли стек и соответствует ли верхний элемент стека
                // нужной открывающей скобке
                char topElement = stack.empty() ? '#' : stack.pop();
                if (topElement != bracketPairs.get(currentBracket)) {
                    return false; // Несоответствие скобок
                }
            } else {
                // Если это открывающая скобка, помещаем её в стек
                stack.push(currentBracket);
            }
        }

        // Строка валидна, только если стек пуст (все скобки закрыты)
        return stack.isEmpty();
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