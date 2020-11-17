package chapter1.section4.h.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class ASpecialHappyBirthdaySong {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int namesCount = scanner.nextInt();

        String[] names = new String[namesCount];

        for (int i = 0; i < namesCount; i++) {
            names[i] = scanner.next();
        }

        int words;
        if (namesCount % 16 == 0) {
            words = namesCount;
        } else {
            words = (int) Math.ceil(namesCount / 16.0) * 16;
        }

        String[] song = {"Happy", "birthday", "to", "you"};

        for (int i = 0; i < words; i++) {
            System.out.print(names[i % namesCount] + ": ");
            int wordIndex = (i + 1) % 16;

            String finalWord;
            if (wordIndex % 12 == 0 && wordIndex != 0) {
                finalWord = "Rujia";
            } else {
                finalWord = song[i % 4];
            }
            System.out.println(finalWord);
        }
    }

}
