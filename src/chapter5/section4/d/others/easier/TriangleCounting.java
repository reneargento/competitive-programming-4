package chapter5.section4.d.others.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/10/25.
 */
public class TriangleCounting {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long[] triangles = computeTriangles();
        int number = FastReader.nextInt();
        while (number >= 3) {
            outputWriter.printLine(triangles[number]);
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long[] computeTriangles() {
        long[] triangles = new long[1000001];
        triangles[4] = 1;

        long totalIncrement = 2;
        int increment = 2;
        int incrementsUsed = 0;

        for (int i = 5; i < triangles.length; i++) {
            long nextTriangles = triangles[i - 1] + totalIncrement;
            totalIncrement += increment;
            incrementsUsed++;

            if (incrementsUsed % 2 == 0) {
                incrementsUsed = 0;
                increment++;
            }
            triangles[i] = nextTriangles;
        }
        return triangles;
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
