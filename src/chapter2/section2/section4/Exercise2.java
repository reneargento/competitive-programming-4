package chapter2.section2.section4;

import java.math.BigInteger;

/**
 * Created by Rene Argento on 20/01/21.
 */
// The same way as the previous exercise, it is not possible to compute the factorial of 25 using the long data type in java.
public class Exercise2 {

    public static void main(String[] args) {
        BigInteger factorial = factorial(25);
        BigInteger remainder = factorial.remainder(BigInteger.valueOf(9137));
        boolean isDivisible = remainder.compareTo(BigInteger.ZERO) == 0;

        System.out.print("25! is ");
        if (!isDivisible) {
            System.out.print("not ");
        }
        System.out.println("divisible by 9317");
    }

    private static BigInteger factorial(int factorial) {
        BigInteger bigInteger = new BigInteger(String.valueOf(factorial));
        BigInteger multiply = new BigInteger(String.valueOf(factorial - 1));

        for (int i = factorial - 1; i >= 2; i--) {
            bigInteger = bigInteger.multiply(multiply);
            multiply = multiply.subtract(BigInteger.ONE);
        }
        return bigInteger;
    }
}
