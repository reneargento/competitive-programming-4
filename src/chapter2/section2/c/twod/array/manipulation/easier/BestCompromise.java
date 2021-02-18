package chapter2.section2.c.twod.array.manipulation.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 06/02/21.
 */
public class BestCompromise {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int people = scanner.nextInt();
            int issues = scanner.nextInt();

            String[] answers = new String[people];
            for (int i = 0; i < people; i++) {
                answers[i] = scanner.next();
            }
            String agreement = agree(answers);
            System.out.println(agreement);
        }
    }

    private static String agree(String[] answers) {
        int issues = answers[0].length();
        char[] agreement = new char[issues];

        for (int i = 0; i < issues; i++) {
            int yes = 0;
            int no = 0;

            for (int j = 0; j < answers.length; j++) {
                if (answers[j].charAt(i) == '1') {
                    yes++;
                } else {
                    no++;
                }
            }
            if (yes >= no) {
                agreement[i] = '1';
            } else {
                agreement[i] = '0';
            }
        }
        return new String(agreement);
    }
}
