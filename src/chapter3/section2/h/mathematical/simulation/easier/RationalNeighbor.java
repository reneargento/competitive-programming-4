package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/12/21.
 */
public class RationalNeighbor {

    private static class Rational {
        long nominator;
        long denominator;

        public Rational(long nominator, long denominator) {
            this.nominator = nominator;
            this.denominator = denominator;
        }
    }

    private static final double EPSILON = .000000000000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            long a = Long.parseLong(data[0]);
            long b = Long.parseLong(data[1]);
            Rational rational = new Rational(a, b);
            double maxDistance = FastReader.nextDouble();

            Rational closestRational = getClosestRational(rational, maxDistance);
            outputWriter.printLine(closestRational.nominator + " " + closestRational.denominator);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Rational getClosestRational(Rational rational, double maxDistance) {
        double originalResult = rational.nominator / (double) rational.denominator;

        for (int denominator = 1; true; denominator++) {
            long nominator = (long) (originalResult * denominator);

            while (rational.nominator * denominator >= rational.denominator * nominator) {
                nominator++;
            }

            double result = nominator / (double) denominator;
            double distance = result - originalResult;

            if (distance - EPSILON <= maxDistance) {
                return new Rational(nominator, denominator);
            }
        }
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
