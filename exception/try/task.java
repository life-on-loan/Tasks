import java.util.*;
import java.lang.*;
import java.io.*;

class App {
    public static void main (String[] args) throws java.lang.Exception {
        try {
            try {
		        throw new Exception ("");
            } catch (RuntimeException e) {
                System.out.println("1");
            } finally {
                System.out.println("2");
            }
            System.out.println("3");
        } catch (Exception e) {
            System.out.println("4");
        } finally {
            System.out.println("5");
        }
        System.out.println("6");
    }
}

// Что будет выведено на экран? А если заменить в 9 строке на RuntimeException?