package chapter1.section6.h.time.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/11/20.
 */
public class JustAMinute {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        double totalSeconds = 0;
        double totalRealSeconds = 0;

        for (int t = 0; t < tests; t++) {
            totalSeconds += scanner.nextInt() * 60;
            totalRealSeconds += scanner.nextDouble();
        }
        double averageLength = totalRealSeconds / totalSeconds;
        System.out.println(averageLength > 1 ? averageLength : "measurement error");
    }
}
