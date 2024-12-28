package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/12/24.
 */
public class CosNA {

    private static class Formula {
        Long[] coefficients = new Long[51];
    }

    private static final int CONSTANT_INDEX = 0;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int n = FastReader.nextInt();
        Formula[] formulasDp = new Formula[51];

        while (n > 0) {
            Formula result = cos(n, formulasDp);
            boolean isFirstTerm = true;

            for (int cosPower = result.coefficients.length - 1; cosPower >= 0; cosPower--) {
                Long coefficient = result.coefficients[cosPower];
                if (coefficient == null) {
                    continue;
                }

                if (!isFirstTerm && coefficient > 0) {
                    outputWriter.print("+");
                }

                if (cosPower == CONSTANT_INDEX) {
                    outputWriter.print(coefficient);
                } else {
                    if (coefficient != 0 && coefficient != 1) {
                        outputWriter.print(coefficient);
                    }
                    outputWriter.print("Cos");
                    if (cosPower > 1) {
                        outputWriter.print("^" + cosPower);
                    }
                    outputWriter.print("(A)");
                }
                isFirstTerm = false;
            }
            outputWriter.printLine();
            n = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Formula cos(int number, Formula[] formulasDp) {
        if (formulasDp[number] != null) {
            return formulasDp[number];
        }
        if (number == 1) {
            Formula formula = new Formula();
            formula.coefficients[1] = 1L;
            formulasDp[number] = formula;
            return formula;
        }
        if (number == 2) {
            Formula formula = new Formula();
            formula.coefficients[2] = 2L;
            formula.coefficients[0] = -1L;
            formulasDp[number] = formula;
            return formula;
        }

        Formula formula = new Formula();
        Formula previousTerm = cos(number - 1, formulasDp);
        for (int i = 0; i < previousTerm.coefficients.length - 1; i++) {
            if (previousTerm.coefficients[i] != null) {
                formula.coefficients[i + 1] = previousTerm.coefficients[i] * 2;
            }
        }

        Formula previous2Term = cos(number - 2, formulasDp);
        for (int i = 0; i < previous2Term.coefficients.length; i++) {
            if (previous2Term.coefficients[i] != null) {
                if (formula.coefficients[i] == null) {
                    formula.coefficients[i] = 0L;
                }
                formula.coefficients[i] -= previous2Term.coefficients[i];
            }
        }
        formulasDp[number] = formula;
        return formula;
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
