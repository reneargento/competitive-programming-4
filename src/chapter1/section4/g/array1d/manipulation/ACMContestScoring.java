package chapter1.section4.g.array1d.manipulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class ACMContestScoring {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int time = scanner.nextInt();
        int solved = 0;
        int score = 0;
        Map<String, Integer> attempted = new HashMap<>();

        while (time != -1) {
            String problem = scanner.next();
            String result = scanner.next();

            if (result.equals("wrong")) {
                attempted.put(problem, attempted.getOrDefault(problem, 0) + 1);
            } else {
                int extraScore = attempted.getOrDefault(problem, 0) * 20;
                score += time + extraScore;
                solved++;
            }

            time = scanner.nextInt();
        }
        System.out.printf("%d %d\n", solved, score);
    }

}
