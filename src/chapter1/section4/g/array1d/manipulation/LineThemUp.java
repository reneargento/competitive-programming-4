package chapter1.section4.g.array1d.manipulation;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class LineThemUp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int names = scanner.nextInt();
        boolean increasing = false;
        boolean decreasing = false;
        String previous = "";

        for (int i = 0; i < names; i++) {
            String name = scanner.next();

            if (i > 0) {
                if (name.compareTo(previous) > 0) {
                    increasing = true;

                    if (decreasing) {
                        break;
                    }
                } else {
                    decreasing = true;

                    if (increasing) {
                        break;
                    }
                }
            }

            previous = name;
        }

        if (increasing && !decreasing) {
            System.out.println("INCREASING");
        } else if (!increasing && decreasing) {
            System.out.println("DECREASING");
        } else {
            System.out.println("NEITHER");
        }
    }

}
