package chapter1.section6.h.time.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 14/11/20.
 */
public class MarsWindow {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int year = scanner.nextInt();
        int endMonth = 8 + (year - 2018) * 12;
        int startMonth = endMonth - 12 + 1;

        boolean isOptimalLaunch = false;
        for (int month = startMonth; month <= endMonth; month++) {
            if (month % 26 == 0) {
                isOptimalLaunch = true;
                break;
            }
        }
        System.out.println(isOptimalLaunch ? "yes" : "no");
    }
}
