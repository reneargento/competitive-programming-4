package chapter5.section3.j.extended.euclidean;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/10/25.
 */
public class SoYouLikeYourFoodHot {

    private static class Combination {
        long x;
        long y;

        public Combination(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final double EPSILON = 1e-6;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int totalProfit = (int) (FastReader.nextDouble() * 100 + EPSILON);
        int profitPerPita = (int) (FastReader.nextDouble() * 100 + EPSILON);
        int profitPerPizza = (int) (FastReader.nextDouble() * 100 + EPSILON);

        List<Combination> combinations = diophantineEquation(profitPerPita, profitPerPizza, totalProfit);
        if (combinations == null) {
            outputWriter.printLine("none");
        } else {
            for (Combination combination : combinations) {
                outputWriter.printLine(combination.x + " " + combination.y);
            }
        }
        outputWriter.flush();
    }

    private static List<Combination> diophantineEquation(long a, long b, long c) {
        List<Combination> combinations = new ArrayList<>();

        extendedEuclid(a, b);

        if (c % gcd != 0) {
            return null;
        }

        // First solutions are given by
        // x0 = bezoutCoefficient1 * (c / gcd)
        // y0 = bezoutCoefficient2 * (c / gcd)

        long x = bezoutCoefficient1 * (c / gcd);
        long y = bezoutCoefficient2 * (c / gcd);

        if (x < 0 && y < 0) {
            return null;
        }

        long deltaX = b / gcd;
        long deltaY = a / gcd;
        long delta = a * deltaX - b * deltaY;

        double low = Math.ceil((double) -x / deltaX);
        double high = Math.floor((double) y / deltaY);

        if (low > high) {
            return null;
        }
        if (delta >= 0) {
            x += low * deltaX;
            y -= low * deltaY;
        } else {
            x += high * deltaX;
            y -= high * deltaY;
        }

        combinations.add(new Combination(x, y));

        long nextX = x + b / gcd;
        long nextY = y - a / gcd;
        int multiplier = 1;

        while (nextY >= 0) {
            combinations.add(new Combination(nextX, nextY));

            multiplier++;
            nextX = x + b / gcd * multiplier;
            nextY = y - a / gcd * multiplier;
        }
        return combinations;
    }

    private static long bezoutCoefficient1;
    private static long bezoutCoefficient2;
    private static long gcd;

    private static void extendedEuclid(long number1, long number2) {
        if (number2 == 0) {
            bezoutCoefficient1 = 1;
            bezoutCoefficient2 = 0;
            gcd = number1;
            return;
        }

        extendedEuclid(number2, number1 % number2);

        long nextBezoutCoefficient1 = bezoutCoefficient2;
        long nextBezoutCoefficient2 = bezoutCoefficient1 - (number1 / number2) * bezoutCoefficient2;

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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
