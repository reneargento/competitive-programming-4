package chapter5.section6;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 27/03/26.
 */
public class ProfessorLazyPhD {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long alpha = FastReader.nextLong();
        long beta = FastReader.nextLong();
        long n = FastReader.nextLong();

        while (alpha != 0 || beta != 0 || n != 0) {
            long grade = computeGrade(alpha, beta, n);
            outputWriter.printLine(grade);

            alpha = FastReader.nextLong();
            beta = FastReader.nextLong();
            n = FastReader.nextLong();
        }
        outputWriter.flush();
    }

    private static long computeGrade(long alpha, long beta, long n) {
        n %= 5;

        if (n == 0) {
            return alpha;
        }
        if (n == 1) {
            return beta;
        }
        if (n == 2) {
            return (1 + beta) / alpha;
        }
        if (n == 3) {
            return (1 + beta + alpha) / (alpha * beta);
        }
        return (1 + alpha) / beta;
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
