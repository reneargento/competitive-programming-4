package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class EmoogleBalance {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = 1;

        while (scanner.hasNext()) {
            int events = scanner.nextInt();

            if (events == 0) {
                break;
            }

            int shouldGive = 0;
            int haveGiven = 0;

            for (int e = 0; e < events; e++) {
                int event = scanner.nextInt();
                if (event > 0) {
                    shouldGive++;
                } else {
                    haveGiven++;
                }
            }
            System.out.printf("Case %d: %d\n", tests, shouldGive - haveGiven);
            tests++;
        }
    }

}
