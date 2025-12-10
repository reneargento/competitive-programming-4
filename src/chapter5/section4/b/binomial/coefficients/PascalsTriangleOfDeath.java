package chapter5.section4.b.binomial.coefficients;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 07/12/25.
 */
public class PascalsTriangleOfDeath {

    public static void main(String[] args) throws IOException {
        OutputWriter outputWriter = new OutputWriter(System.out);
        boolean maxValueReached = false;
        BigInteger bigInteger10 = new BigInteger("10");
        BigInteger maxValue = bigInteger10.pow(60);

        List<BigInteger> elements = new ArrayList<>();
        elements.add(BigInteger.ONE);
        outputWriter.printLine(1);

        while (true) {
            List<BigInteger> nextElements = new ArrayList<>();
            nextElements.add(BigInteger.ONE);

            for (int i = 1; i < elements.size(); i++) {
                BigInteger nextElement = elements.get(i).add(elements.get(i - 1));
                if (nextElement.compareTo(maxValue) >= 0) {
                    maxValueReached = true;
                }
                nextElements.add(nextElement);
            }
            nextElements.add(BigInteger.ONE);

            outputWriter.print(nextElements.get(0));
            for (int i = 1; i < nextElements.size(); i++) {
                outputWriter.print(" " + nextElements.get(i));
            }
            outputWriter.printLine();

            if (maxValueReached) {
                break;
            }
            elements = nextElements;
        }
        outputWriter.flush();
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
