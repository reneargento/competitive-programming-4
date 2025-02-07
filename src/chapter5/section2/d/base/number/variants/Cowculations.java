package chapter5.section2.d.base.number.variants;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/01/25.
 */
public class Cowculations {

    private static class SymbolSumResult {
        char result;
        char carry;

        public SymbolSumResult(char result, char carry) {
            this.result = result;
            this.carry = carry;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int tablets = FastReader.nextInt();
        outputWriter.printLine("COWCULATIONS OUTPUT");
        for (int t = 0; t < tablets; t++) {
            String number1 = FastReader.next();
            String number2 = FastReader.next();
            char[] operations = new char[3];
            for (int o = 0; o < 3; o++) {
                operations[o] = FastReader.next().charAt(0);
            }
            String result = FastReader.next();

            boolean isHypothesisCorrect = isHypothesisCorrect(number1, number2, operations, result);
            outputWriter.printLine(isHypothesisCorrect ? "YES" : "NO");
        }
        outputWriter.printLine("END OF OUTPUT");
        outputWriter.flush();
    }

    private static boolean isHypothesisCorrect(String number1, String number2, char[] operations, String result) {
        for (char operation : operations) {
            if (operation == 'A') {
                number2 = sumNumber(number1, number2);
            } else if (operation == 'R') {
                number2 = rightShift(number2);
            } else if (operation == 'L') {
                number2 = leftShift(number2);
            }
        }

        int appendNeeded = 8 - number2.length();
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < appendNeeded; i++) {
            prefix.append("V");
        }
        number2 = prefix + number2;
        return number2.equals(result);
    }

    private static String sumNumber(String number1, String number2) {
        StringBuilder result = new StringBuilder();
        String highest;
        String lowest;

        if (number1.length() >= number2.length()) {
            highest = number1;
            lowest = number2;
        } else {
            highest = number2;
            lowest = number1;
        }

        int highestIndex = highest.length() - 1;
        int lowestIndex = lowest.length() - 1;
        char carry = '-';

        while (highestIndex >= 0 || lowestIndex >= 0) {
            if (highestIndex >= 0 && lowestIndex >= 0) {
                char symbol1 = highest.charAt(highestIndex);
                char symbol2 = lowest.charAt(lowestIndex);

                if (carry != '-') {
                    SymbolSumResult symbolSumResult = sumSymbol(symbol1, carry);
                    symbol1 = symbolSumResult.result;
                    carry = symbolSumResult.carry;
                }

                SymbolSumResult symbolSumResult = sumSymbol(symbol1, symbol2);
                if (symbolSumResult.carry != '-') {
                    carry = symbolSumResult.carry;
                }
                result.append(symbolSumResult.result);
                highestIndex--;
                lowestIndex--;
            } else if (highestIndex >= 0) {
                char symbol = highest.charAt(highestIndex);

                if (carry != '-') {
                    SymbolSumResult symbolSumResult = sumSymbol(symbol, carry);
                    result.append(symbolSumResult.result);
                    carry = symbolSumResult.carry;
                } else {
                    result.append(symbol);
                }
                highestIndex--;
            }
        }

        if (carry != '-') {
            result.append(carry);
        }
        return result.reverse().toString();
    }

    private static SymbolSumResult sumSymbol(char symbol1, char symbol2) {
        char result = '-';
        char carry = '-';

        switch (symbol1) {
            case 'V': switch (symbol2) {
                case 'V': result = 'V'; break;
                case 'U': result = 'U'; break;
                case 'C': result = 'C'; break;
                case 'D': result = 'D'; break;
            } break;
            case 'U': switch (symbol2) {
                case 'V': result = 'U'; break;
                case 'U': result = 'C'; break;
                case 'C': result = 'D'; break;
                case 'D': result = 'V'; carry = 'U'; break;
            } break;
            case 'C': switch (symbol2) {
                case 'V': result = 'C'; break;
                case 'U': result = 'D'; break;
                case 'C': result = 'V'; carry = 'U'; break;
                case 'D': result = 'U'; carry = 'U'; break;
            } break;
            case 'D': switch (symbol2) {
                case 'V': result = 'D'; break;
                case 'U': result = 'V'; carry = 'U'; break;
                case 'C': result = 'U'; carry = 'U'; break;
                case 'D': result = 'C'; carry = 'U'; break;
            } break;
        }
        return new SymbolSumResult(result, carry);
    }

    private static String rightShift(String number) {
        return "V" + number.substring(0, number.length() - 1);
    }

    private static String leftShift(String number) {
        return number + "V";
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