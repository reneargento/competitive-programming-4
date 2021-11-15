package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/11/21.
 */
public class TheSumEqualsTheProduct {

    private static class Solution implements Comparable<Solution> {
        double a;
        double b;
        double c;
        double sum;

        public Solution(double a, double b, double c) {
            this.a = a;
            this.b = b;
            this.c = c;
            sum = a + b + c;
        }

        @Override
        public int compareTo(Solution other) {
            if (sum != other.sum) {
                return Double.compare(sum, other.sum);
            }
            return Double.compare(a, other.a);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        double number1 = FastReader.nextDouble();
        double number2 = FastReader.nextDouble();
        double min = Math.min(number1, number2);
        double max = Math.max(number1, number2);

        List<Solution> solutions = getEqualSumAndProducts(min, max);
        for (Solution solution : solutions) {
            outputWriter.printLine(String.format("%.2f = %.2f + %.2f + %.2f = %.2f * %.2f * %.2f",
                    solution.sum, solution.a, solution.b, solution.c, solution.a, solution.b,
                    solution.c));
        }
        outputWriter.flush();
    }

    private static List<Solution> getEqualSumAndProducts(double min, double max) {
        List<Solution> solutions = new ArrayList<>();
        int centsConstant = 10000;
        int maxValue = (int) (max * 1000000);

        for (int a = 1; a <= 25600; a++) {
            if (a * a * a > maxValue) {
                break;
            }

            for (int b = a; b <= 25600; b++) {
                if (a * b * b > maxValue) {
                    break;
                }

                long product = a * b;
                long sum = a + b;

                if (product - centsConstant == 0) {
                    continue;
                }
                if ((sum * centsConstant) % (product - centsConstant) != 0) {
                    continue;
                }

                long c = (sum * centsConstant) / (product - centsConstant);
                if (b > c) {
                    continue;
                }

                double aDouble = a / 100.00;
                double bDouble = b / 100.00;
                double cDouble = c / 100.00;
                double totalProduct = aDouble * bDouble * cDouble;
                if (totalProduct < min) {
                    continue;
                }
                if (totalProduct > max) {
                    continue;
                }
                solutions.add(new Solution(aDouble, bDouble, cDouble));
            }
        }
        Collections.sort(solutions);
        return solutions;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
