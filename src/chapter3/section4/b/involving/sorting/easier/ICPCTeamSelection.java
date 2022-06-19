package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/06/22.
 */
public class ICPCTeamSelection {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int teams = FastReader.nextInt();
            int[] scores = new int[teams * 3];
            for (int i = 0; i < scores.length; i++) {
                scores[i] = FastReader.nextInt();
            }
            int maximumPerformance = computeMaximumPerformance(scores);
            outputWriter.printLine(maximumPerformance);
        }
        outputWriter.flush();
    }

    private static int computeMaximumPerformance(int[] scores) {
        Arrays.sort(scores);
        int maximumPerformance = 0;
        int startIndex = 0;
        int endIndex = scores.length - 2;

        while (startIndex < endIndex) {
            maximumPerformance += scores[endIndex];
            startIndex++;
            endIndex -= 2;
        }
        return maximumPerformance;
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
