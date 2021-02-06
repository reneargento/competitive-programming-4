package chapter2.section2.section4;

import java.math.BigInteger;

/**
 * Created by Rene Argento on 20/01/21.
 */
// In java attempting to compute the factorial of 25 using the long data type and trimming trailing zeroes will still
// overflow because the long data type maximum value is 2^63 - 1 (there is no unsiged long data type as in c and c++).
public class Exercise1 {

    public static void main(String[] args) {
        BigInteger factorial = factorial(25);

        Integer lastNonZeroDigit = getLastNonZeroDigit(factorial);
        if (lastNonZeroDigit != null) {
            System.out.println("Last non-zero digit of 25! = " + lastNonZeroDigit);
        } else {
            System.out.println("All digits are zero");
        }
        System.out.println("Expected: 4");
    }

    private static BigInteger factorial(int factorial) {
        BigInteger bigInteger = BigInteger.ONE;
        BigInteger multiply = BigInteger.TWO;

        for (int i = 2; i <= factorial; i++) {
            bigInteger = bigInteger.multiply(multiply);
            System.out.println(bigInteger);
            multiply = multiply.add(BigInteger.ONE);
        }
        return bigInteger;
    }

    private static Integer getLastNonZeroDigit(BigInteger factorial) {
        String stringValue = factorial.toString();
        for (int i = stringValue.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(stringValue.charAt(i));
            if (digit != 0) {
                return digit;
            }
        }
        return null;
    }
}
