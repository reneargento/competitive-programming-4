package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class SpeedLimit {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int values = scanner.nextInt();

        while (values != -1) {
            long miles = 0;
            int previousTime = 0;

            for (int v = 0; v < values; v++) {
                int speed = scanner.nextInt();
                int totalTime = scanner.nextInt();
                miles += (totalTime - previousTime) * speed;
                previousTime = totalTime;
            }
            System.out.printf("%d miles\n", miles);
            values = scanner.nextInt();
        }
    }

}
