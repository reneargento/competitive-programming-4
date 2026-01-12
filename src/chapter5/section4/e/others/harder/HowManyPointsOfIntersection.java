package chapter5.section4.e.others.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/01/26.
 */
public class HowManyPointsOfIntersection {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int pointsA = FastReader.nextInt();
        int pointsB = FastReader.nextInt();
        int caseId = 1;
        while (pointsA != 0 || pointsB != 0) {
            long numberOfIntersections = computeNumberOfIntersections(pointsA, pointsB);
            outputWriter.printLine(String.format("Case %d: %d", caseId, numberOfIntersections));

            caseId++;
            pointsA = FastReader.nextInt();
            pointsB = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeNumberOfIntersections(int pointsA, int pointsB) {
        long aChoose2 = binomialCoefficient(pointsA, 2);
        long bChoose2 = binomialCoefficient(pointsB, 2);
        return aChoose2 * bChoose2;
    }

    private static long binomialCoefficient(int totalNumbers, int numbersToChoose) {
        long result = 1;

        for (int i = 0; i < numbersToChoose; i++) {
            result = result * (totalNumbers - i) / (i + 1);
        }
        return result;
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
