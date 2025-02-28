package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 19/02/25.
 */
public class UglyNumbers {

    public static void main(String[] args) throws IOException {
        OutputWriter outputWriter = new OutputWriter(System.out);

        BigInteger uglyNumber1500 = compute1500thUglyNumber();
        outputWriter.printLine("The 1500'th ugly number is " + uglyNumber1500 + ".");
        outputWriter.flush();
    }

    private static BigInteger compute1500thUglyNumber() {
        List<BigInteger> uglyNumbers = new ArrayList<>();
        BigInteger multiples2;
        BigInteger multiples3;
        BigInteger multiples5;

        BigInteger bigInteger2 = new BigInteger("2");
        BigInteger bigInteger3 = new BigInteger("3");
        BigInteger bigInteger5 = new BigInteger("5");

        multiples2 = BigInteger.ONE;
        for (int multiple2Index = 0; multiple2Index < 30; multiple2Index++) {
            multiples3 = BigInteger.ONE;
            for (int multiple3Index = 0; multiple3Index < 20; multiple3Index++) {
                multiples5 = BigInteger.ONE;
                for (int multiple5Index = 0; multiple5Index < 20; multiple5Index++) {
                    BigInteger uglyNumber = multiples2.multiply(multiples3).multiply(multiples5);
                    uglyNumbers.add(uglyNumber);
                    multiples5 = multiples5.multiply(bigInteger5);
                }
                multiples3 = multiples3.multiply(bigInteger3);
            }
            multiples2 = multiples2.multiply(bigInteger2);
        }

        Collections.sort(uglyNumbers);
        return uglyNumbers.get(1499);
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void flush() {
            writer.flush();
        }
    }
}
