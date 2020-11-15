package chapter1.section4.g.array1d.manipulation;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class HotHike {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int days = scanner.nextInt();

        int previous2 = Integer.MAX_VALUE;
        int previous1 = Integer.MAX_VALUE;
        int minMaxTemperature = Integer.MAX_VALUE;
        int startDay = 0;

        for (int i = 1; i <= days; i++) {
            int current = scanner.nextInt();

            if (i >= 3) {
                int currentMaxTemperature = Math.max(previous2, current);
                if (currentMaxTemperature < minMaxTemperature) {
                    minMaxTemperature = currentMaxTemperature;
                    startDay = i - 2;
                }
            }

            previous2 = previous1;
            previous1 = current;
        }
        System.out.printf("%d %d\n", startDay, minMaxTemperature);
    }

}
