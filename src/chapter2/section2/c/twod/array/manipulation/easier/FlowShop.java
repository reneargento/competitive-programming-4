package chapter2.section2.c.twod.array.manipulation.easier;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 03/02/21.
 */
public class FlowShop {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int swathers = scanner.nextInt();
        int stages = scanner.nextInt();
        int[][] time = new int[swathers + 1][stages + 1];

        for (int row = 1; row < time.length; row++) {
            for (int column = 1; column < time[0].length; column++) {
                time[row][column] = scanner.nextInt() + Math.max(time[row - 1][column], time[row][column - 1]);
            }
        }

        StringJoiner swathersCompletedTime = new StringJoiner(" ");
        for (int row = 1; row < time.length; row++) {
            swathersCompletedTime.add(String.valueOf(time[row][stages]));
        }
        System.out.println(swathersCompletedTime);
    }
}
