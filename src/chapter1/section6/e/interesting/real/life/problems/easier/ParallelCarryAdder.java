package chapter1.section6.e.interesting.real.life.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 31/10/20.
 */
public class ParallelCarryAdder {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long overflowValue = (long) Math.pow(2, 31) - 1;
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                System.out.println();
            }

            String aString = scanner.next();
            String bString = scanner.next();

            long a = stringToBitSet(aString);
            long b = stringToBitSet(bString);

            printBitSets(a, b, overflowValue);
            sum(a, b, overflowValue);
        }
    }

    private static long stringToBitSet(String string) {
        long value = 0;
        int mask = 1;

        for (int i = string.length() - 1; i >= 0; i--) {
            if (string.charAt(i) == '1') {
                value = value | mask;
            }
            mask = mask << 1;
        }
        return value;
    }

    private static void sum(long a, long b, long overflowValue) {
        do {
            long c = a ^ b;
            long d = a & b;

            a = c;
            b = d << 1;

            printBitSets(a, b, overflowValue);
        } while (b <= overflowValue && b > 0);
    }

    private static void printBitSets(long a, long b, long overflowValue) {
        System.out.print(getBitsString(a) + " ");

        if (b > overflowValue) {
            System.out.println("overflow");
        } else {
            System.out.println(getBitsString(b));
        }
    }

    private static String getBitsString(long value) {
        int mask = 1;
        StringBuilder bitsString = new StringBuilder();

        for (int i = 0; i < 31; i++) {
            if ((value & mask) > 0) {
                bitsString.append("1");
            } else {
                bitsString.append("0");
            }
            mask = mask << 1;
        }
        return bitsString.reverse().toString();
    }
}
