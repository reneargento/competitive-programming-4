package chapter1.section4.j.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 24/09/20.
 */
public class Brainfuck {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            String commands = scanner.next();

            int[] bytes = new int[100];
            int pointer = 0;

            for (int i = 0; i < commands.length(); i++) {
                switch (commands.charAt(i)) {
                    case '>':
                        pointer++;
                        pointer %= 100;
                        break;
                    case '<':
                        pointer--;
                        if (pointer < 0) {
                            pointer = 99;
                        }
                        break;
                    case '+':
                        bytes[pointer]++;
                        bytes[pointer] %= 256;
                        break;
                    case '-':
                        bytes[pointer]--;
                        if (bytes[pointer] < 0) {
                            bytes[pointer] = 255;
                        }
                }
            }

            System.out.printf("Case %d: ", t);
            for (int i = 0; i < bytes.length; i++) {
                String value = Integer.toHexString(bytes[i]).toUpperCase();
                if (value.length() == 1) {
                    value = "0" + value;
                }
                System.out.print(value);

                if (i < bytes.length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

}
