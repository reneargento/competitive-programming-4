package chapter1.section4.j.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 23/09/20.
 */
public class BitsEqualizer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            String stringToAdapt = scanner.next();
            String string = scanner.next();

            int questionMarks = 0;
            int zeroesThatShouldBeOne = 0;
            int onesThatShouldBeZero = 0;
            int questionMarksThatShouldBeOne = 0;

            for (int i = 0; i < stringToAdapt.length(); i++) {
                char numberInStringToAdapt = stringToAdapt.charAt(i);
                char numberInString = string.charAt(i);

                if (numberInStringToAdapt == '?') {
                    questionMarks++;

                    if (numberInString == '1') {
                        questionMarksThatShouldBeOne++;
                    }
                } else if (numberInStringToAdapt == '0' && numberInString == '1') {
                    zeroesThatShouldBeOne++;
                } else if (numberInStringToAdapt == '1' && numberInString == '0') {
                    onesThatShouldBeZero++;
                }
            }

            int initialSwaps = Math.min(onesThatShouldBeZero, zeroesThatShouldBeOne);
            onesThatShouldBeZero -= initialSwaps;
            zeroesThatShouldBeOne -= initialSwaps;

            boolean possible = onesThatShouldBeZero <= questionMarksThatShouldBeOne;
            int moves = initialSwaps + questionMarks + zeroesThatShouldBeOne + onesThatShouldBeZero;
            System.out.printf("Case %d: ", t);
            System.out.println(possible ? moves : -1);
        }
    }

}
