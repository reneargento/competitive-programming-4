package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/12/24.
 */
public class NeighborhoodWatch {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int houses = FastReader.nextInt();
        int watchHouses = FastReader.nextInt();
        long totalSafetyRating = 0;
        int previousWatchHouse = 0;

        for (int i = 0; i < watchHouses; i++) {
            int watchHouseNumber = FastReader.nextInt();
            long housesToCount = watchHouseNumber - previousWatchHouse - 1;
            totalSafetyRating += housesToCount * previousWatchHouse + watchHouseNumber;
            previousWatchHouse = watchHouseNumber;
        }
        if (watchHouses > 0) {
            long housesToCount = houses - previousWatchHouse;
            totalSafetyRating += housesToCount * previousWatchHouse;
        }
        outputWriter.printLine(totalSafetyRating);
        outputWriter.flush();
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
