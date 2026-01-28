package chapter5.section5.a.probability.theory.easier;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by Rene Argento on 17/01/26.
 */
public class ThrowTheDice {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int faces = Integer.parseInt(data[0]);
            int throwsNumber = Integer.parseInt(data[1]);
            int targetSum = Integer.parseInt(data[2]);

            String sumProbability = computeSumProbability(faces, throwsNumber, targetSum);
            outputWriter.printLine(sumProbability);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String computeSumProbability(int faces, int throwsNumber, int targetSum) {
        // dp[throws remaining][sum remaining]
        BigInteger[][] dp = new BigInteger[throwsNumber + 1][targetSum + 1];
        for (BigInteger[] values : dp) {
            Arrays.fill(values, BigInteger.valueOf(-1));
        }

        BigInteger possibilities = BigInteger.valueOf(faces).pow(throwsNumber);
        BigInteger validThrows = computeSumOnThrows(dp, faces, throwsNumber, targetSum);
        return validThrows + "/" + possibilities;
    }

    private static BigInteger computeSumOnThrows(BigInteger[][] dp, int faces, int throwsRemaining, int sumRemaining) {
        if (sumRemaining < 0) {
            return BigInteger.ZERO;
        }
        if (dp[throwsRemaining][sumRemaining].compareTo(BigInteger.valueOf(-1)) != 0) {
            return dp[throwsRemaining][sumRemaining];
        }

        if (throwsRemaining == 0) {
            if (sumRemaining == 0) {
                return BigInteger.ONE;
            } else {
                return BigInteger.ZERO;
            }
        }

        BigInteger totalValidThrows = BigInteger.ZERO;
        for (int face = 1; face <= faces; face++) {
            BigInteger validThrows = computeSumOnThrows(dp, faces, throwsRemaining - 1, sumRemaining - face);;
            totalValidThrows = totalValidThrows.add(validThrows);
        }
        dp[throwsRemaining][sumRemaining] = totalValidThrows;
        return totalValidThrows;
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