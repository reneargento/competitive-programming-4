package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/02/26.
 */
public class AnotherLottery {

    private static class Fraction {
        long numerator;
        long denominator;

        public Fraction(long numerator, long denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int participants = FastReader.nextInt();
        int rounds = FastReader.nextInt();

        while (participants != 0 || rounds != 0) {
            long[] ticketsLastRound = new long[participants];
            long totalTicketsLastRound = 0;

            for (int personId = 0; personId < participants; personId++) {
                for (int roundId = 0; roundId < rounds; roundId++) {
                    int tickets = FastReader.nextInt();

                    if (roundId == rounds - 1) {
                        ticketsLastRound[personId] = tickets;
                        totalTicketsLastRound += tickets;
                    }
                }
            }
            Fraction[] probabilities = computeProbabilities(ticketsLastRound, totalTicketsLastRound);
            for (Fraction fraction : probabilities) {
                outputWriter.printLine(fraction.numerator + " / " + fraction.denominator);
            }

            participants = FastReader.nextInt();
            rounds = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Fraction[] computeProbabilities(long[] ticketsLastRound, long totalTicketsLastRound) {
        Fraction[] probabilities = new Fraction[ticketsLastRound.length];

        for (int personId = 0; personId < ticketsLastRound.length; personId++) {
            probabilities[personId] = new Fraction(ticketsLastRound[personId], totalTicketsLastRound);
        }

        for (Fraction fraction : probabilities) {
            long gcd = gcd(fraction.numerator, fraction.denominator);
            fraction.numerator = fraction.numerator / gcd;
            fraction.denominator = fraction.denominator / gcd;
        }
        return probabilities;
    }

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
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
