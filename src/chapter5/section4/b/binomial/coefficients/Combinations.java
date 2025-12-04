package chapter5.section4.b.binomial.coefficients;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 02/12/25.
 */
public class Combinations {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (true) {
            List<String> words = getWords(line);
            int totalNumbers = Integer.parseInt(words.get(0));
            int numbersToChoose = Integer.parseInt(words.get(1));

            if (totalNumbers == 0 || numbersToChoose == 0) {
                break;
            }

            BigInteger ways = binomialCoefficient(totalNumbers, numbersToChoose);
            outputWriter.printLine(String.format("%d things taken %d at a time is %s exactly.", totalNumbers,
                    numbersToChoose, ways.toString()));
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static BigInteger binomialCoefficient(int totalNumbers, int numbersToChoose) {
        BigInteger result = BigInteger.ONE;

        for (int i = 0; i < numbersToChoose; i++) {
            result = result.multiply(BigInteger.valueOf(totalNumbers - i))
                    .divide(BigInteger.valueOf(i + 1));
        }
        return result;
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ' || character == '\t') {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(character);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
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
