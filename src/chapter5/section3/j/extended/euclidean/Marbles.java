package chapter5.section3.j.extended.euclidean;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/10/25.
 */
public class Marbles {

    private static class Result {
        long boxes1;
        long boxes2;

        public Result(long boxes1, long boxes2) {
            this.boxes1 = boxes1;
            this.boxes2 = boxes2;
        }
    }

    private static long bezoutCoefficient1;
    private static long bezoutCoefficient2;
    private static long gcd;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int marbles = FastReader.nextInt();

        while (marbles != 0) {
            int cost1 = FastReader.nextInt();
            int size1 = FastReader.nextInt();
            int cost2 = FastReader.nextInt();
            int size2 = FastReader.nextInt();

            Result result = computeBoxesRequired(marbles, cost1, size1, cost2, size2);
            if (result == null) {
                outputWriter.printLine("failed");
            } else {
                outputWriter.printLine(result.boxes1 + " " + result.boxes2);
            }
            marbles = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeBoxesRequired(int marbles, int cost1, int size1, int cost2, int size2) {
        long[] solution = diophantineEquation(size1, size2, marbles, cost1, cost2);
        if (solution[0] != 0) {
            return null;
        }
        return new Result(solution[1], solution[2]);
    }

    private static long[] diophantineEquation(int a, int b, int c, int cost1, int cost2) {
        long[] solution = { 1, -1, -1 };

        extendedEuclid(a, b);

        // The equation only has integer solutions if gcd(a, b) divides c
        if (c % gcd != 0) {
            return solution;
        }

        // First solutions are given by
        // x0 = bezoutCoefficient1 * (c / gcd)
        // y0 = bezoutCoefficient2 * (c / gcd)

        long x = bezoutCoefficient1 * (c / gcd);
        long y = bezoutCoefficient2 * (c / gcd);

        // If both values are negative, return
        if (x < 0 && y < 0) {
            solution[0] = 2;
            return solution;
        }

        long deltaX = b / gcd;
        long deltaY = a / gcd;
        long delta = cost1 * deltaX - cost2 * deltaY;

        double low = Math.ceil((double) -x / deltaX);
        double high = Math.floor((double) y / deltaY);

        if (low > high) {
            solution[0] = 2;
            return solution;
        }
        if (delta > 0) {
            x += low * deltaX;
            y -= low * deltaY;
        } else {
            x += high * deltaX;
            y -= high * deltaY;
        }

        solution[0] = 0;
        solution[1] = x;
        solution[2] = y;
        return solution;
    }

    private static void extendedEuclid(int number1, int number2) {
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
