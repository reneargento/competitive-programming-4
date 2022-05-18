package chapter3.section4.a.classical;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/05/22.
 */
public class TheBusDriverProblem {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int busDrivers = FastReader.nextInt();
        int maxLength = FastReader.nextInt();
        int pricePerOvertimeHour = FastReader.nextInt();

        while (busDrivers != 0 || maxLength != 0 || pricePerOvertimeHour != 0) {
            int[] morningRouteLengths = new int[busDrivers];
            int[] eveningRouteLengths = new int[busDrivers];
            for (int i = 0; i < morningRouteLengths.length; i++) {
                morningRouteLengths[i] = FastReader.nextInt();
            }
            for (int i = 0; i < eveningRouteLengths.length; i++) {
                eveningRouteLengths[i] = FastReader.nextInt();
            }

            long minimumOvertimeAmount = computeMinimumOvertimeAmount(morningRouteLengths, eveningRouteLengths,
                    maxLength, pricePerOvertimeHour);
            outputWriter.printLine(minimumOvertimeAmount);

            busDrivers = FastReader.nextInt();
            maxLength = FastReader.nextInt();
            pricePerOvertimeHour = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeMinimumOvertimeAmount(int[] morningRouteLengths, int[] eveningRouteLengths,
                                                     int maxLength, int pricePerOvertimeHour) {
        long minimumOvertimeAmount = 0;
        Arrays.sort(morningRouteLengths);
        Arrays.sort(eveningRouteLengths);

        for (int i = 0; i < morningRouteLengths.length; i++) {
            int totalLength = morningRouteLengths[i] + eveningRouteLengths[eveningRouteLengths.length - 1 - i];
            minimumOvertimeAmount += Math.max(totalLength - maxLength, 0);
        }
        return minimumOvertimeAmount * pricePerOvertimeHour;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
