package chapter1.section4.h.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class CharacterRecognition {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 4; i++) {
            scanner.nextLine();
        }

        String line = scanner.nextLine();
        StringBuilder numbers = new StringBuilder();

        for (int i = 0; i < line.length(); i += 4) {
            String number = line.substring(i, i + 3);

            switch (number) {
                case ".*.":
                    numbers.append(1);
                    break;
                case "*..":
                    numbers.append(2);
                    break;
                default:
                    numbers.append(3);
                    break;
            }
        }
        System.out.println(numbers);
    }

}
