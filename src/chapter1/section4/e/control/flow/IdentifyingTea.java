package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class IdentifyingTea {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            int teaType = scanner.nextInt();
            int correctAnswers = 0;

            for (int i = 0; i < 5; i++) {
                int guess = scanner.nextInt();
                if (teaType == guess) {
                    correctAnswers++;
                }
            }
            System.out.println(correctAnswers);
        }
    }

}
