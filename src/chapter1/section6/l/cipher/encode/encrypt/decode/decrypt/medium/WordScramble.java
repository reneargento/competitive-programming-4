package chapter1.section6.l.cipher.encode.encrypt.decode.decrypt.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 01/12/20.
 */
public class WordScramble {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String[] words = scanner.nextLine().split(" ");

            for (int i = 0; i < words.length; i++) {
                StringBuilder reverseWord = new StringBuilder(words[i]).reverse();
                System.out.print(reverseWord);

                if (i != words.length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
