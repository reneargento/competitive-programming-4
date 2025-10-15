package chapter5.section3.j.extended.euclidean;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/10/25.
 */
public class EuclidProblem {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
       String line = FastReader.getLine();

       while (line != null) {
           String[] data = line.split(" ");
           int a = Integer.parseInt(data[0]);
           int b = Integer.parseInt(data[1]);
           extendedEuclid(a, b);
           outputWriter.printLine(bezoutCoefficient1 + " " + bezoutCoefficient2 + " " + gcd);

           line = FastReader.getLine();
       }
        outputWriter.flush();
    }

    private static int bezoutCoefficient1;
    private static int bezoutCoefficient2;
    private static int gcd;

    private static void extendedEuclid(int number1, int number2) {
        if (number2 == 0) {
            bezoutCoefficient1 = 1;
            bezoutCoefficient2 = 0;
            gcd = number1;
            return;
        }

        extendedEuclid(number2, number1 % number2);

        int nextBezoutCoefficient1 = bezoutCoefficient2;
        int nextBezoutCoefficient2 = bezoutCoefficient1 - (number1 / number2) * bezoutCoefficient2;

        bezoutCoefficient1 = nextBezoutCoefficient1;
        bezoutCoefficient2 = nextBezoutCoefficient2;
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
