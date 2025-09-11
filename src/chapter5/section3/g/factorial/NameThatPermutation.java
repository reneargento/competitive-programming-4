package chapter5.section3.g.factorial;

import java.io.*;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 03/09/25.
 */
public class NameThatPermutation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        BigInteger[] factorials = computeFactorials();

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int n = Integer.parseInt(data[0]);
            BigInteger k = new BigInteger(data[1]);

            int[] targetPermutation = computePermutation(factorials, n, k);

            outputWriter.print(targetPermutation[0]);
            for (int i = 1; i < targetPermutation.length; i++) {
                outputWriter.print(" " + targetPermutation[i]);
            }
            outputWriter.printLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int[] computePermutation(BigInteger[] factorials, int n, BigInteger k) {
        int[] targetPermutation = new int[n];
        Set<Integer> usedValues = new HashSet<>();
        computePermutation(factorials, usedValues, n, targetPermutation, k, 0);
        return targetPermutation;
    }

    private static void computePermutation(BigInteger[] factorials, Set<Integer> usedValues, int n,
                                           int[] targetPermutation, BigInteger k, int index) {
        if (index == n) {
            return;
        }
        int size = n - index;
        BigInteger factorial = factorials[size];

        BigInteger sectionSize = factorial.divide(BigInteger.valueOf(size));
        int sectionValue = k.divide(sectionSize).intValue() + 1;

        int finalValue = getFinalValue(usedValues, sectionValue);
        usedValues.add(finalValue);
        targetPermutation[index] = finalValue;

        BigInteger nextK = k.mod(sectionSize);
        computePermutation(factorials, usedValues, n, targetPermutation, nextK, index + 1);
    }

    private static int getFinalValue(Set<Integer> usedValues, int sectionValue) {
        int usedValuesCount = 0;
        int notUsedValuesCount = 0;
        int currentValue = 1;

        while (notUsedValuesCount < sectionValue) {
            if (usedValues.contains(currentValue)) {
                usedValuesCount++;
            } else {
                notUsedValuesCount++;
            }
            currentValue++;
        }
        return sectionValue + usedValuesCount;
    }

    private static BigInteger[] computeFactorials() {
        BigInteger[] factorials = new BigInteger[51];
        factorials[0] = BigInteger.ONE;
        for (int i = 1; i < factorials.length; i++) {
            factorials[i] = factorials[i - 1].multiply(BigInteger.valueOf(i));
        }
        return factorials;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        private static String getLine() throws IOException {
            return reader.readLine();
        }
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
