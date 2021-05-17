package chapter2.section3.c.direct.addressing.table.dat.others;

import java.io.*;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rene Argento on 16/05/21.
 */
public class CanYouDecideItForME {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        Pattern pattern = Pattern.compile("\\?+M\\?+E\\?+\\?");

        for (int t = 0; t < tests; t++) {
            String line = FastReader.next();
            Matcher matcher = pattern.matcher(line);

            if (matcher.matches() && areQuestionMarksOk(line)) {
                outputWriter.printLine("theorem");
            } else {
                outputWriter.printLine("no-theorem");
            }
        }
        outputWriter.flush();
    }

    private static boolean areQuestionMarksOk(String line) {
        int indexOfM = line.indexOf("M");
        int indexOfE = line.indexOf("E");

        int startQuestionMarks = indexOfM;
        int intermediateQuestionMarks = indexOfE - indexOfM - 1;
        int endQuestionMarks = line.length() - indexOfE - 1;

        return endQuestionMarks - startQuestionMarks == intermediateQuestionMarks;
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
