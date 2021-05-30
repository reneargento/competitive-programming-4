package chapter2.section3.e.hash.table.map.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 29/05/21.
 */
public class GrandpaBernie {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int trips = FastReader.nextInt();
        Map<String, List<Integer>> tripYears = new HashMap<>();

        for (int i = 0; i < trips; i++) {
            String countryName = FastReader.next();
            int year = FastReader.nextInt();

            if (!tripYears.containsKey(countryName)) {
                tripYears.put(countryName, new ArrayList<>());
            }
            tripYears.get(countryName).add(year);
        }

        for (List<Integer> years : tripYears.values()) {
            Collections.sort(years);
        }

        int queries = FastReader.nextInt();
        for (int i = 0; i < queries; i++) {
            String countryName = FastReader.next();
            int tripNumber = FastReader.nextInt();
            int year = tripYears.get(countryName).get(tripNumber - 1);
            outputWriter.printLine(year);
        }
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
