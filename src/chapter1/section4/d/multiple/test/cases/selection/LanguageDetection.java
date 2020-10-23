package chapter1.section4.d.multiple.test.cases.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class LanguageDetection {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int test = 1;

        while (scanner.hasNext()) {
            String word = scanner.next();
            if (word.equals("#")) {
                break;
            }

            System.out.print("Case " + test + ": ");

            switch (word) {
                case "HELLO":
                    System.out.println("ENGLISH"); break;
                case "HOLA":
                    System.out.println("SPANISH"); break;
                case "HALLO":
                    System.out.println("GERMAN"); break;
                case "BONJOUR":
                    System.out.println("FRENCH"); break;
                case "CIAO":
                    System.out.println("ITALIAN"); break;
                case "ZDRAVSTVUJTE":
                    System.out.println("RUSSIAN"); break;
                default:
                    System.out.println("UNKNOWN");
            }
            test++;
        }
    }

}
