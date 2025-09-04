package chapter5.section3.g.factorial;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/08/25.
 */
public class ICPCTutorial {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int maxOperations = FastReader.nextInt();
        int maxInput = FastReader.nextInt();
        int algorithmType = FastReader.nextInt();

        String result = checkAlgorithm(maxOperations, maxInput, algorithmType);
        outputWriter.printLine(result);
        outputWriter.flush();
    }

    private static String checkAlgorithm(int maxOperations, int maxInput, int algorithmType) {
        long[] factorials = computeFactorials();

        switch (algorithmType) {
            case 1: {
                if (maxInput >= factorials.length) {
                    return "TLE";
                }
                return checkResult(factorials[maxInput], maxOperations);
            }
            case 2: {
                if (maxInput > 30) {
                    return "TLE";
                }
                long operations = (long) Math.pow(2, maxInput);
                return checkResult(operations, maxOperations);
            }
            case 3: {
                long operations = (long) Math.pow(maxInput, 4);
                return checkResult(operations, maxOperations);
            }
            case 4: {
                long operations = (long) Math.pow(maxInput, 3);
                return checkResult(operations, maxOperations);
            }
            case 5: {
                long operations = (long) Math.pow(maxInput, 2);
                return checkResult(operations, maxOperations);
            }
            case 6: {
                double log2 = logBaseX(maxInput, 2);
                double operations = maxInput * log2;
                return checkResult(operations, maxOperations);
            }
            default: {
                return checkResult(maxInput, maxOperations);
            }
        }
    }

    private static String checkResult(double operations, long maxOperations) {
        if (operations <= maxOperations) {
            return "AC";
        } else {
            return "TLE";
        }
    }

    private static long[] computeFactorials() {
        long[] factorials = new long[14];
        factorials[1] = 1;

        for (int i = 2; i < factorials.length; i++) {
            factorials[i] = factorials[i - 1] * i;
        }
        return factorials;
    }

    private static double logBaseX(double number, int base) {
        return Math.log(number) / Math.log(base);
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
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
