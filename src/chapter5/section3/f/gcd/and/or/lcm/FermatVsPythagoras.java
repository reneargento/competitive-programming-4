package chapter5.section3.f.gcd.and.or.lcm;

import java.io.*;

/**
 * Created by Rene Argento on 08/08/25.
 */
// Based on https://rizoantoufiq.blogspot.com/2015/10/uva-106-fermat-vs-pythagoras.html
public class FermatVsPythagoras {

    private static class Result {
        int primeTriples;
        int numbersNotInTriples;

        public Result(int primeTriples, int numbersNotInTriples) {
            this.primeTriples = primeTriples;
            this.numbersNotInTriples = numbersNotInTriples;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            int number = Integer.parseInt(line);
            Result result = computeTriples(number);
            outputWriter.printLine(result.primeTriples + " " + result.numbersNotInTriples);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result computeTriples(int number) {
        int primeTriples = 0;
        int numbersNotInTriples = number;
        boolean[] used = new boolean[number + 1];

        int sqrtN = (int) Math.sqrt(number);
        for (int i = 1; i <= sqrtN; i++) {
            for (int j = i + 1; j <= sqrtN; j += 2) {
                if (gcd(i, j) == 1) {
                    primeTriples++;

                    // Euclid's formula
                    int a = j * j - i * i;
                    int b = 2 * i * j;
                    int c = i * i + j * j;
                    if (c > number) {
                        break;
                    }

                    int multipleA = a;
                    int multipleB = b;
                    int multipleC = c;
                    while (multipleC <= number) {
                        used[multipleA] = true;
                        used[multipleB] = true;
                        used[multipleC] = true;

                        multipleA += a;
                        multipleB += b;
                        multipleC += c;
                    }
                }
            }
        }

        for (int i = 1; i <= number; i++) {
            if (used[i]) {
                numbersNotInTriples--;
            }
        }
        return new Result(primeTriples, numbersNotInTriples);
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

        public void flush() {
            writer.flush();
        }
    }
}
