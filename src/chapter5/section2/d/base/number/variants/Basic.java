package chapter5.section2.d.base.number.variants;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/02/25.
 */
public class Basic {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String symbols = FastReader.next();
            if (isValidConstant(symbols)) {
                outputWriter.printLine("yes");
            } else {
                outputWriter.printLine("no");
            }
        }
        outputWriter.flush();
    }

    private static boolean isValidConstant(String symbols) {
        BigInteger bigInteger2 = BigInteger.valueOf(2);
        BigInteger bigInteger16 = BigInteger.valueOf(16);
        StringBuilder currentValue = new StringBuilder();
        BigInteger currentBase = new BigInteger("10");
        boolean isNumberInProgress = false;
        boolean firstBase = true;

        try {
            for (int i = 0; i < symbols.length(); i++) {
                char symbol = symbols.charAt(i);
                if (isInvalidSymbol(symbol)) {
                    return false;
                }

                if (symbol == '#') {
                    if (firstBase) {
                        currentBase = new BigInteger(currentValue.toString(), currentBase.intValue());
                        firstBase = false;

                        if (isInvalidBase(currentBase, bigInteger2, bigInteger16)) {
                            return false;
                        }
                    }

                    if (isNumberInProgress) {
                        currentBase = new BigInteger(currentValue.toString(), currentBase.intValue());

                        if (i < symbols.length() - 1 &&
                                isInvalidBase(currentBase, bigInteger2, bigInteger16)) {
                            return false;
                        }
                        currentValue = new StringBuilder();
                        isNumberInProgress = false;
                    } else {
                        currentValue = new StringBuilder();
                        isNumberInProgress = true;
                    }
                } else {
                    currentValue.append(symbol);
                }
            }
        } catch (NumberFormatException exception) {
            return false;
        }
        return !isNumberInProgress;
    }

    private static boolean isInvalidSymbol(char symbol) {
        if (Character.isLetter(symbol) && Character.isUpperCase(symbol)) {
            return true;
        }
        return !Character.isDigit(symbol) && !Character.isLetter(symbol) && symbol != '#';
    }

    private static boolean isInvalidBase(BigInteger base, BigInteger bigInteger2, BigInteger bigInteger16) {
        return base.compareTo(bigInteger2) < 0 || base.compareTo(bigInteger16) > 0;
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
