package chapter1.section6.i.time.harder;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by Rene Argento on 21/11/20.
 */
public class LeapYearOrNotLeapYearAnd {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int caseNumber = 1;

        while (scanner.hasNext()) {
            if (caseNumber > 1) {
                System.out.println();
            }

            BigInteger year = new BigInteger(scanner.next());
            boolean ordinary = true;
            boolean isLeapYear = false;

            BigInteger yearMod400 = year.mod(BigInteger.valueOf(400));
            BigInteger yearMod4 = year.mod(BigInteger.valueOf(4));
            BigInteger yearMod100 = year.mod(BigInteger.valueOf(100));
            BigInteger yearMod15 = year.mod(BigInteger.valueOf(15));
            BigInteger yearMod55 = year.mod(BigInteger.valueOf(55));

            if (yearMod400.equals(BigInteger.ZERO) ||
                    (yearMod4.equals(BigInteger.ZERO) && !yearMod100.equals(BigInteger.ZERO))) {
                System.out.println("This is leap year.");
                isLeapYear = true;
                ordinary = false;
            }
            if (yearMod15.equals(BigInteger.ZERO)) {
                System.out.println("This is huluculu festival year.");
                ordinary = false;
            }
            if (yearMod55.equals(BigInteger.ZERO) && isLeapYear) {
                System.out.println("This is bulukulu festival year.");
                ordinary = false;
            }
            if (ordinary) {
                System.out.println("This is an ordinary year.");
            }
            caseNumber++;
        }
    }
}
