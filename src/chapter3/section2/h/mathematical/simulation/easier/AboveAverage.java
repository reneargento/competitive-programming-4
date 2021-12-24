package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/12/21.
 */
public class AboveAverage {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] grades = new int[FastReader.nextInt()];
            for (int i = 0; i < grades.length; i++) {
                grades[i] = FastReader.nextInt();
            }
            double studentsAboveAverage = computeAboveAverage(grades);
            outputWriter.printLine(String.format("%.3f%%", studentsAboveAverage));
        }
        outputWriter.flush();
    }

    private static double computeAboveAverage(int[] grades) {
        double numberOfGrades = grades.length;
        long total = 0;
        for (int grade : grades) {
            total += grade;
        }
        double average = total / numberOfGrades;

        int studentsAboveAverage = 0;
        for (int grade : grades) {
            if (grade > average) {
                studentsAboveAverage++;
            }
        }
        return studentsAboveAverage / numberOfGrades * 100;
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
