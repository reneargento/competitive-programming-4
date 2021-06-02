package chapter2.section3.f.hash.table.map.harder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/06/21.
 */
public class DoYourOwnHomework {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int sparrowSubjects = FastReader.nextInt();
            Map<String, Integer> subjectsToDays = new HashMap<>();

            for (int i = 0; i < sparrowSubjects; i++) {
                subjectsToDays.put(FastReader.next(), FastReader.nextInt());
            }

            int daysToFinish = FastReader.nextInt();
            String assignmentSubject = FastReader.next();

            outputWriter.print(String.format("Case %d: ", t));
            if (!subjectsToDays.containsKey(assignmentSubject)) {
                outputWriter.printLine("Do your own homework!");
            } else {
                int daysRequired = subjectsToDays.get(assignmentSubject);
                if (daysRequired <= daysToFinish) {
                    outputWriter.printLine("Yesss");
                } else if (daysRequired <= daysToFinish + 5) {
                    outputWriter.printLine("Late");
                } else {
                    outputWriter.printLine("Do your own homework!");
                }
            }
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
