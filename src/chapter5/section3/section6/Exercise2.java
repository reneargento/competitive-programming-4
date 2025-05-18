package chapter5.section3.section6;

/**
 * Created by Rene Argento on 12/05/25.
 */
public class Exercise2 {

    public static int gcdIterative(int a, int b) {
        while (b != 0) {
            a %= b;
            int aux = a;
            a = b;
            b = aux;
        }
        return a;
    }

    public static void main(String[] args) {
        int gcd1 = gcdIterative(17, 34);
        System.out.println("GCD: " + gcd1);
        System.out.println("Expected: 17");

        int gcd2 = gcdIterative(50, 49);
        System.out.println("\nGCD: " + gcd2);
        System.out.println("Expected: 1");
    }
}
