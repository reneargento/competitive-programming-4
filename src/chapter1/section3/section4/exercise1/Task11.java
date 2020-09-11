package chapter1.section3.section4.exercise1;

import java.math.BigInteger;

/**
 * Created by Rene Argento on 29/08/20.
 */
public class Task11 {

    public static void main(String[] args) {
        BigInteger number = new BigInteger("48112959837082048697");
        System.out.println(number.isProbablePrime(10) ? "Prime" : "Composite");
    }

}
