package chapter5.section5.a.probability.theory.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/02/26.
 */
public class OddsOfMia {

    private static class Results {
        long wins;
        long otherOutcomes;
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        char symbol1 = FastReader.next().charAt(0);
        char symbol2 = FastReader.next().charAt(0);
        char symbol3 = FastReader.next().charAt(0);
        char symbol4 = FastReader.next().charAt(0);

        while (symbol1 != '0' || symbol2 != '0' || symbol3 != '0' || symbol4 != '0') {
            String winProbability = computeWinProbability(symbol1, symbol2, symbol3, symbol4);
            outputWriter.printLine(winProbability);

            symbol1 = FastReader.next().charAt(0);
            symbol2 = FastReader.next().charAt(0);
            symbol3 = FastReader.next().charAt(0);
            symbol4 = FastReader.next().charAt(0);
        }
        outputWriter.flush();
    }

    private static String computeWinProbability(char symbol1, char symbol2, char symbol3, char symbol4) {
        Results results = new Results();
        computeWinProbability(symbol1, symbol2, symbol3, symbol4, results);
        long totalOutcomes = results.wins + results.otherOutcomes;

        long gcd = gcd(results.wins, totalOutcomes);
        long nominatorReduced = results.wins / gcd;
        long denominatorReduced = totalOutcomes / gcd;

        if (nominatorReduced == 0) {
            return "0";
        }
        if (nominatorReduced == denominatorReduced) {
            return "1";
        }
        return nominatorReduced + "/" + denominatorReduced;
    }

    private static void computeWinProbability(char symbol1, char symbol2, char symbol3, char symbol4, Results results) {
        if (isDigit(symbol1) && isDigit(symbol2) && isDigit(symbol3) && isDigit(symbol4)) {
            if (isWin(symbol1, symbol2, symbol3, symbol4)) {
                results.wins += 1;
            } else {
                results.otherOutcomes += 1;
            }
            return;
        }

        if (symbol1 == '*') {
            for (char nextSymbol = '1'; nextSymbol <= '6'; nextSymbol++) {
                computeWinProbability(nextSymbol, symbol2, symbol3, symbol4, results);
            }
        }
        if (symbol2 == '*') {
            for (char nextSymbol = '1'; nextSymbol <= '6'; nextSymbol++) {
                computeWinProbability(symbol1, nextSymbol, symbol3, symbol4, results);
            }
        }
        if (symbol3 == '*') {
            for (char nextSymbol = '1'; nextSymbol <= '6'; nextSymbol++) {
                computeWinProbability(symbol1, symbol2, nextSymbol, symbol4, results);
            }
        }
        if (symbol4 == '*') {
            for (char nextSymbol = '1'; nextSymbol <= '6'; nextSymbol++) {
                computeWinProbability(symbol1, symbol2, symbol3, nextSymbol, results);
            }
        }
    }

    private static boolean isDigit(char symbol) {
        return symbol != '*';
    }

    private static boolean isWin(char symbol1, char symbol2, char symbol3, char symbol4) {
        if (isMia(symbol1, symbol2)) {
            return !isMia(symbol3, symbol4);
        }
        if (isMia(symbol3, symbol4)) {
            return false;
        }

        if (symbol1 == symbol2) {
            if (symbol3 == symbol4) {
                return symbol1 > symbol3;
            }
            return true;
        }
        if (symbol3 == symbol4) {
            return false;
        }

        int number1 = Integer.parseInt(getNumber(symbol1, symbol2));
        int number2 = Integer.parseInt(getNumber(symbol3, symbol4));
        return number1 > number2;
    }

    private static boolean isMia(char symbol1, char symbol2) {
        return (symbol1 == '1' && symbol2 == '2') || (symbol1 == '2' && symbol2 == '1');
    }

    private static String getNumber(char symbol1, char symbol2) {
        if (symbol1 > symbol2) {
            return String.valueOf(symbol1) + symbol2;
        } else {
            return String.valueOf(symbol2) + symbol1;
        }
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
