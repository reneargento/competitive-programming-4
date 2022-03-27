package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/03/22.
 */
public class FillTheContainers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int vessels = Integer.parseInt(data[0]);
            int containers = Integer.parseInt(data[1]);
            int[] vesselsCapacity = new int[vessels];
            for (int i = 0; i < vesselsCapacity.length; i++) {
                vesselsCapacity[i] = FastReader.nextInt();
            }

            int minimalMaximalCapacity = computeMinimalMaximalCapacity(vesselsCapacity, containers, 0,
                    1000000000);
            outputWriter.printLine(minimalMaximalCapacity);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMinimalMaximalCapacity(int[] vesselsCapacity, int containers, int low, int high) {
        if (low > high) {
            return -1;
        }

        while (low <= high) {
            int middle = low + (high - low) / 2;
            if (!canFillContainers(vesselsCapacity, containers, middle)) {
                low = middle + 1;
            } else {
                int result = middle;
                int candidateResult = computeMinimalMaximalCapacity(vesselsCapacity, containers, low, middle - 1);
                if (candidateResult != -1) {
                    result = candidateResult;
                }
                return result;
            }
        }
        return -1;
    }

    private static boolean canFillContainers(int[] vesselsCapacity, int containers, int maxCapacity) {
        int containersUsed = 0;
        int currentCapacityFilled = 0;

        for (int vesselCapacity : vesselsCapacity) {
            if (vesselCapacity > maxCapacity) {
                return false;
            }
            if (currentCapacityFilled + vesselCapacity > maxCapacity) {
                containersUsed++;
                currentCapacityFilled = vesselCapacity;
            } else {
                currentCapacityFilled += vesselCapacity;
            }
        }
        containersUsed++;
        return containersUsed <= containers;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
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
