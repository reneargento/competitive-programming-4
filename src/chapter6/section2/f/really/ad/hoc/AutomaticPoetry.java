package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/07/2026.
 */
public class AutomaticPoetry {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String line1 = FastReader.getLine();
            String line2 = FastReader.getLine();
            completePoem(line1, line2, outputWriter);
        }
        outputWriter.flush();
    }

    private static void completePoem(String line1, String line2, OutputWriter outputWriter) {
        String completedLine1 = line1.replaceAll("[<>]","");
        int s2StartIndex = line1.indexOf("<");
        int s2EndIndex = line1.indexOf(">");
        int s4StartIndex = line1.indexOf("<", s2StartIndex + 1);
        int s4EndIndex = line1.indexOf(">", s2EndIndex + 1);

        String s2 = line1.substring(s2StartIndex + 1, s2EndIndex);
        String s3 = line1.substring(s2EndIndex + 1, s4StartIndex);
        String s4 = line1.substring(s4StartIndex + 1, s4EndIndex);
        String s5 = line1.substring(s4EndIndex + 1);

        line2 = line2.replaceAll("\\.","");
        String completedLine2 = line2 + s4 + s3 + s2 + s5;

        outputWriter.printLine(completedLine1);
        outputWriter.printLine(completedLine2);
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

        public void flush() {
            writer.flush();
        }
    }
}