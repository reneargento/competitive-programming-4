package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class StandOnZanzibar {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int i = 0; i < tests; i++) {
            int turtles = scanner.nextInt();
            int year = 0;
            int previousYearTurtles = 0;
            int foreignTurtles = 0;

            while (turtles != 0) {
                if (year > 0) {
                    int doubleTurtles = previousYearTurtles * 2;
                    if (turtles > doubleTurtles) {
                        foreignTurtles += turtles - doubleTurtles;
                    }
                }

                previousYearTurtles = turtles;
                year++;
                turtles = scanner.nextInt();
            }
            System.out.println(foreignTurtles);
        }
    }

}
