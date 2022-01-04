package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/01/22.
 */
public class Tri {

    private static class Operators {
        String operator1;
        String operator2;

        public Operators(String operator1, String operator2) {
            this.operator1 = operator1;
            this.operator2 = operator2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int number1 = FastReader.nextInt();
        int number2 = FastReader.nextInt();
        int number3 = FastReader.nextInt();

        Operators operators = computeEquation(number1, number2, number3);
        outputWriter.printLine(String.format("%d%s%d%s%d", number1, operators.operator1, number2,
                operators.operator2, number3));
        outputWriter.flush();
    }

    private static Operators computeEquation(int number1, int number2, int number3) {
        String operator1;
        String operator2;

        if (number1 + number2 == number3) {
            operator1 = "+";
            operator2 = "=";
        } else if (number1 - number2 == number3) {
            operator1 = "-";
            operator2 = "=";
        } else if (number1 * number2 == number3) {
            operator1 = "*";
            operator2 = "=";
        } else if (number2 != 0
                && number1 / number2 == number3) {
            operator1 = "/";
            operator2 = "=";
        } else if (number1 == number2 + number3) {
            operator1 = "=";
            operator2 = "+";
        } else if (number1 == number2 - number3) {
            operator1 = "=";
            operator2 = "-";
        } else if (number1 == number2 * number3) {
            operator1 = "=";
            operator2 = "*";
        } else {
            operator1 = "=";
            operator2 = "/";
        }
        return new Operators(operator1, operator2);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
