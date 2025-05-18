package chapter5.section3.section6;

/**
 * Created by Rene Argento on 12/05/25.
 */
// Based on https://www.geeksforgeeks.org/steins-algorithm-for-finding-gcd/
public class Exercise3 {

    public static int gcdStein(int a, int b) {
        // gcd(0, 0) = 0
        // gcd(a, 0) = a
        // gcd(0, b) = b
        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }

        // Find k, where k is the greatest power of 2 that divides both a and b
        int k;
        for (k = 0; ((a | b) & 1) == 0; k++) {
            a >>= 1;
            b >>= 1;
        }

        // Divide a by 2 until it becomes odd
        while ((a & 1) == 0) {
            a >>= 1;
        }

        // From here on, a is always odd
        do {
            // If b is even, remove all factors of 2 from it
            while ((b & 1) == 0) {
                b >>= 1;
            }

            // Now a and b are both odd.
            // Swap if necessary so that a <= b, then set b = b - a (which results in an even number)
            if (a > b) {
                int aux = a;
                a = b;
                b = aux;
            }
            b = (b - a);
        } while (b != 0);

        // Restore common factors of 2
        return a << k;
    }

    public static void main(String[] args) {
        int gcd1 = gcdStein(17, 34);
        System.out.println("GCD: " + gcd1);
        System.out.println("Expected: 17");

        int gcd2 = gcdStein(50, 49);
        System.out.println("\nGCD: " + gcd2);
        System.out.println("Expected: 1");
    }
}
