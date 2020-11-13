package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class DivisionOfNlogonia {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int queries = scanner.nextInt();

        while (queries != 0) {
            int xDivision = scanner.nextInt();
            int yDivision = scanner.nextInt();

            for (int q = 0; q < queries; q++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();

                if (x == xDivision || y == yDivision) {
                    System.out.println("divisa");
                } else if (x < xDivision) {
                    if (y < yDivision) {
                        System.out.println("SO");
                    } else {
                        System.out.println("NO");
                    }
                } else {
                    if (y < yDivision) {
                        System.out.println("SE");
                    } else {
                        System.out.println("NE");
                    }
                }
            }
            queries = scanner.nextInt();
        }
    }

}
