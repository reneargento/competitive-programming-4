package chapter1.section4.h.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class HissingMicrophone {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String word = scanner.next();
        boolean containsDoubleS = false;

        for (int i = 0; i < word.length() - 1; i++) {
            if (word.charAt(i) == 's' && word.charAt(i + 1) == 's') {
                containsDoubleS = true;
                break;
            }
        }

        System.out.println(containsDoubleS ? "hiss" : "no hiss");
    }

}
