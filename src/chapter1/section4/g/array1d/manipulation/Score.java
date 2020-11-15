package chapter1.section4.g.array1d.manipulation;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class Score {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            String result = scanner.next();
            long score = 0;
            int streak = 0;

            for (int i = 0; i < result.length(); i++) {
                if (result.charAt(i) == 'O') {
                    streak++;
                    score += streak;
                } else {
                    streak = 0;
                }
            }
            System.out.println(score);
        }
    }

}
