package chapter1.section6.e.interesting.real.life.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 30/10/20.
 */
public class RationalGrading {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String number = scanner.next();
        int instructions = scanner.nextInt();

        while (!number.equals("0") || instructions != 0) {
            int value = Integer.decode(number);
            int oldValue = value;
            int score = 0;

            for (int i = 0; i < instructions; i++) {
                boolean increment = false;
                boolean decrement = false;

                String instruction = scanner.next();
                value = scanner.nextInt();

                switch (instruction) {
                    case "++i": oldValue++; break;
                    case "i++": increment = true; break;
                    case "--i": oldValue--; break;
                    case "i--": decrement = true; break;
                }

                if (value == oldValue) {
                    score++;
                }

                if (increment) {
                    value++;
                }
                if (decrement) {
                    value--;
                }
                oldValue = value;
            }
            System.out.println(score);

            number = scanner.next();
            instructions = scanner.nextInt();
        }
    }
}
