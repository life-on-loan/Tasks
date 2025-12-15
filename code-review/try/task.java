import java.util.*;
import java.lang.*;
import java.io.*;

class App {
    public static void main (String[] args) {
        System.out.println(getValue());
    }

    static Integer getValue() {
        try {
            return 1;
        } catch (Exception e) {
            return 2;
        } finally {
            return 3;
        }
    }
}

// Что будет выведено на экран?