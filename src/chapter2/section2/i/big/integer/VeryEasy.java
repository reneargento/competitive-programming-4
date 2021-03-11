package chapter2.section2.i.big.integer;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by Rene Argento on 09/03/21.
 */
public class VeryEasy {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            int series = scanner.nextInt();
            BigInteger value = new BigInteger(scanner.next());

            BigInteger sum = BigInteger.ZERO;

            for (int i = 1; i <= series; i++) {
                BigInteger nextTerm = value.pow(i).multiply(BigInteger.valueOf(i));
                sum = sum.add(nextTerm);
            }
            System.out.println(sum);
        }
    }
}
