package chapter5.section2.j.really.ad.hoc;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/05/25.
 */
public class Car {

    private static class Result {
        double parameter1;
        double parameter2;

        public Result(double parameter1, double parameter2) {
            this.parameter1 = parameter1;
            this.parameter2 = parameter2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int type = FastReader.nextInt();
        int caseId = 1;

        while (type != 0) {
            double value1 = FastReader.nextDouble();
            double value2 = FastReader.nextDouble();
            double value3 = FastReader.nextDouble();

            Result result = computeMissingParameters(type, value1, value2, value3);
            outputWriter.printLine(String.format("Case %d: %.3f %.3f", caseId, result.parameter1, result.parameter2));

            caseId++;
            type = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeMissingParameters(int type, double value1, double value2, double value3) {
        // v^2 = u^2 + 2 * a * s
        // s = (v^2 - u^2) / 2 * a
        // u^2 = v^2 - 2 * a * s

        // a = (v - u) / t
        // t = (v - u) / a
        double parameter1;
        double parameter2;

        switch (type) {
            case 1: parameter2 = (value2 - value1) / value3;
            parameter1 = computeDistance(value1, value2, parameter2); break;
            case 2: parameter1 = computeDistance(value1, value2, value3);
            parameter2 = (value2 - value1) / value3; break;
            case 3: parameter1 = Math.sqrt(value1 * value1 + 2 * value2 * value3);
            parameter2 = (parameter1 - value1) / value2; break;
            default: parameter1 = Math.sqrt(value1 * value1 - 2 * value2 * value3);
            parameter2 = (value1 - parameter1) / value2;
        }
        return new Result(parameter1, parameter2);
    }

    private static double computeDistance(double startVelocity, double endVelocity, double acceleration) {
        return (endVelocity * endVelocity - startVelocity * startVelocity) / (2 * acceleration);
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

        public void flush() {
            writer.flush();
        }
    }
}
