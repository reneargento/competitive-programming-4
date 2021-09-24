package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;

/**
 * Created by Rene Argento on 24/09/21.
 */
public class Kickdown {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String gear1 = FastReader.getLine();

        while (gear1 != null) {
            String gear2 = FastReader.getLine();

            int gear1Length = gear1.length();
            int gear2Length = gear2.length();
            int minimumLength = gear1Length + gear2Length;

            for (int i = -gear2Length; i <= gear1Length; i++) {
                boolean fitTogether = true;

                for (int j = 0; j < gear2Length; j++) {
                    if (i + j >= 0 && i + j < gear1Length) {
                        if (gear1.charAt(i + j) == '2' && gear2.charAt(j) == '2') {
                            fitTogether = false;
                            break;
                        }
                    }
                }
                if (fitTogether)
                    minimumLength = Math.min(minimumLength,
                            Math.max(gear1Length, i + gear2Length) - Math.min(i, 0));
            }

            outputWriter.printLine(minimumLength);
            gear1 = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
