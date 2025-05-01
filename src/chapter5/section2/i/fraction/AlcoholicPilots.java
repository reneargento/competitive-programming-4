package chapter5.section2.i.fraction;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/04/25.
 */
public class AlcoholicPilots {

    private static class Result {
        boolean oweBeer;
        long timeNominator;
        long timeDenominator;

        public Result(boolean oweBeer, long timeNominator, long timeDenominator) {
            this.oweBeer = oweBeer;
            this.timeNominator = timeNominator;
            this.timeDenominator = timeDenominator;
        }
    }

    private static class Fraction {
        long nominator;
        long denominator;

        public Fraction(long nominator, long denominator) {
            this.nominator = nominator;
            this.denominator = denominator;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int velocity1 = FastReader.nextInt();
        int distance1 = FastReader.nextInt();
        int velocity2 = FastReader.nextInt();
        int distance2 = FastReader.nextInt();
        int caseId = 1;

        while (velocity1 != 0 || distance1 != 0 || velocity2 != 0 || distance2 != 0) {
            Result result = computeArrivalTime(velocity1, distance1, velocity2, distance2);
            outputWriter.print(String.format("Case #%d: ", caseId));
            if (result.oweBeer) {
                outputWriter.printLine("You owe me a beer!");
            } else {
                outputWriter.printLine("No beer for the captain.");
            }
            outputWriter.print("Avg. arrival time: " + result.timeNominator);
            if (result.timeDenominator != 1) {
                outputWriter.print("/" + result.timeDenominator);
            }
            outputWriter.printLine();

            caseId++;
            velocity1 = FastReader.nextInt();
            distance1 = FastReader.nextInt();
            velocity2 = FastReader.nextInt();
            distance2 = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeArrivalTime(long velocity1, long distance1, long velocity2, long distance2) {
        boolean oweBeer = distance1 * velocity2 < distance2 * velocity1;

        Fraction averageArrivalTime = sumFraction(distance1, velocity1, distance2, velocity2);
        averageArrivalTime = multiplyFraction(averageArrivalTime.nominator, averageArrivalTime.denominator, 1, 2);

        long gcd = gcd(averageArrivalTime.nominator, averageArrivalTime.denominator);
        averageArrivalTime.nominator /= gcd;
        averageArrivalTime.denominator /= gcd;
        return new Result(oweBeer, averageArrivalTime.nominator, averageArrivalTime.denominator);
    }

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

    private static long lcm(long number1, long number2) {
        return number1 * (number2 / gcd(number1, number2));
    }

    private static Fraction sumFraction(long nominator1, long denominator1, long nominator2, long denominator2) {
        long lcm = lcm(denominator1, denominator2);
        long multiplier1 = lcm / denominator1;
        long multiplier2 = lcm / denominator2;

        long nominator = (nominator1 * multiplier1) + (nominator2 * multiplier2);
        return new Fraction(nominator, lcm);
    }

    private static Fraction multiplyFraction(long nominator1, long denominator1, long nominator2, long denominator2) {
        return new Fraction(nominator1 * nominator2, denominator1 * denominator2);
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
