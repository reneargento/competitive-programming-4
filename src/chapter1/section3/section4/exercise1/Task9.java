package chapter1.section3.section4.exercise1;

import java.math.BigInteger;

/**
 * Created by Rene Argento on 29/08/20.
 */
public class Task9 {

    public static void main(String[] args) {
        String baseXNumber = "FF";
        int baseX = 16;
        int baseY1 = 10;
        int baseY2 = 2;

        System.out.println(new BigInteger(baseXNumber, baseX).toString(baseY1));
        System.out.println(new BigInteger(baseXNumber, baseX).toString(baseY2));
    }

}
