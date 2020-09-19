package chapter1.section4.a.io.sequences;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
// Solution description: http://uva.outsbook.com/problemhints/problemdetails/116/11614
public class EtruscanWarriorsNeverPlayChess {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int i = 0; i < tests; i++) {
            long warriorsNumber = scanner.nextLong();

            // O(1) solution
            System.out.println((long) (-1 + Math.sqrt(1 + 8 * warriorsNumber)) / 2);

            // O(lg n) solution
//            BigInteger warriors = BigInteger.valueOf(warriorsNumber);
//
//            long low = 0;
//            long high = Long.MAX_VALUE;
//
//            while (low <= high) {
//                BigInteger middle = BigInteger.valueOf(low + (high - low) / 2);
//                BigInteger nextRow = middle.add(BigInteger.ONE);
//
//                BigInteger warriorsPlaces = (middle.add(BigInteger.ONE)).multiply(middle).divide(BigInteger.valueOf(2));
//                BigInteger warriorsPlacesWithNextRow = (nextRow.add(BigInteger.ONE)).multiply(nextRow).divide(BigInteger.valueOf(2));
//
//                if (warriorsPlaces.compareTo(warriors) > 0) {
//                    high = middle.longValue();
//                } else if (warriorsPlaces.compareTo(warriors) <= 0 && warriors.compareTo(warriorsPlacesWithNextRow) < 0) {
//                    System.out.println(middle);
//                    break;
//                } else {
//                    low = middle.longValue();
//                }
//            }
        }
    }

}
