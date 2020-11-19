package chapter1.section4.j.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 22/09/20.
 */
public class BenderBRodriguezProblem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();

        while (length != 0) {
            String current = "+x";

            for (int i = 0; i < length - 1; i++) {
                String next = scanner.next();

                if (next.equals("+z")) {
                    switch (current) {
                        case "+x":
                            current = "+z";
                            break;
                        case "-x":
                            current = "-z";
                            break;
                        case "+z":
                            current = "-x";
                            break;
                        case "-z":
                            current = "+x";
                            break;
                    }
                } else if (next.equals("-z")) {
                    switch (current) {
                        case "+x":
                            current = "-z";
                            break;
                        case "-x":
                            current = "+z";
                            break;
                        case "+z":
                            current = "+x";
                            break;
                        case "-z":
                            current = "-x";
                            break;
                    }
                } else if (next.equals("+y")) {
                    switch (current) {
                        case "+x":
                            current = "+y";
                            break;
                        case "-x":
                            current = "-y";
                            break;
                        case "+y":
                            current = "-x";
                            break;
                        case "-y":
                            current = "+x";
                            break;
                    }
                } else if (next.equals("-y")) {
                    switch (current) {
                        case "+x":
                            current = "-y";
                            break;
                        case "-x":
                            current = "+y";
                            break;
                        case "+y":
                            current = "+x";
                            break;
                        case "-y":
                            current = "-x";
                            break;
                    }
                }
            }
            System.out.println(current);
            length = scanner.nextInt();
        }
    }
}
