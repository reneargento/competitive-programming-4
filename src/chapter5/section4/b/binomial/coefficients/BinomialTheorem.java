package chapter5.section4.b.binomial.coefficients;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/12/25.
 */
public class BinomialTheorem {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            String equation = FastReader.getLine();
            String expandedEquation = expandExpression(equation);
            outputWriter.printLine(String.format("Case %d: %s", t, expandedEquation));
        }
        outputWriter.flush();
    }

    private static String expandExpression(String equation) {
        StringBuilder expandedEquation = new StringBuilder();

        int indexPlus = equation.indexOf('+');
        int indexVariableEnd = equation.indexOf(')');
        int indexPower = equation.indexOf('^');

        String variable1 = equation.substring(1, indexPlus);
        String variable2 = equation.substring(indexPlus + 1, indexVariableEnd);
        int power = Integer.parseInt(equation.substring(indexPower + 1));
        int variable1Power = power;

        BigInteger[] binomialCoefficients = computeBinomialCoefficients(power);

        for (int i = 0; i <= power; i++) {
            if (i > 0) {
                expandedEquation.append("+");
            }

            if (binomialCoefficients[i].compareTo(BigInteger.ONE) > 0) {
                expandedEquation.append(binomialCoefficients[i]).append("*");
            }

            boolean hasVariable1 = variable1Power != 0;
            boolean hasVariable2 = i != 0;

            if (hasVariable1) {
                expandedEquation.append(variable1);
                if (variable1Power != 1) {
                    expandedEquation.append("^").append(variable1Power);
                }
            }

            if (hasVariable1 && hasVariable2) {
                expandedEquation.append("*");
            }

            if (hasVariable2) {
                expandedEquation.append(variable2);
                if (i != 1) {
                    expandedEquation.append("^").append(i);
                }
            }
            variable1Power--;
        }
        return expandedEquation.toString();
    }

    private static BigInteger[] computeBinomialCoefficients(int power) {
        BigInteger[] binomialCoefficients = new BigInteger[power + 1];
        binomialCoefficients[0] = BigInteger.ONE;

        for (int i = 1; i < binomialCoefficients.length; i++) {
            binomialCoefficients[i] = binomialCoefficients[i - 1].multiply(BigInteger.valueOf(power - (i - 1)))
                    .divide(BigInteger.valueOf(i));
        }
        return binomialCoefficients;
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
