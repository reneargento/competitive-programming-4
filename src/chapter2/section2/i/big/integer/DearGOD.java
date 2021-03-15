package chapter2.section2.i.big.integer;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 14/03/21.
 */
public class DearGOD {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        outputWriter.printLine("Dear GOD, Pardon Me");
        int caseNumber = 1;

        String line = FastReader.getLine();

        while (line != null) {
            if (caseNumber > 1) {
                outputWriter.printLine();
            }

            String[] values = line.split(" ");
            int flowerMultiplier = Integer.parseInt(values[0]);
            int sinks = Integer.parseInt(values[1]);
            computeFlowersAndReconciliations(flowerMultiplier, sinks, outputWriter);

            line = FastReader.getLine();
            caseNumber++;
        }

        outputWriter.flush();
    }

    private static void computeFlowersAndReconciliations(int flowerMultiplier, int sinks, OutputWriter outputWriter) {
        BigInteger flowerPower = BigInteger.ONE;
        BigInteger flowers = BigInteger.ONE;

        BigInteger flowerMultiplierBigInteger = BigInteger.valueOf(flowerMultiplier);

        for (int i = 1; i < sinks; i++) {
            flowerPower = flowerPower.multiply(flowerMultiplierBigInteger);
            flowers = flowers.add(flowerPower);
        }

        BigInteger reconciliations = flowerPower.multiply(flowerMultiplierBigInteger);

        String heavenPassFlowers = String.format("X = %s", flowers.toString());
        outputWriter.printLine(heavenPassFlowers);
        String heavenPassReconciliations = String.format("K = %s", reconciliations.toString());
        outputWriter.printLine(heavenPassReconciliations);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
