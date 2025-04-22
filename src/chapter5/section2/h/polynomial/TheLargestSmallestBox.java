package chapter5.section2.h.polynomial;

import java.io.*;

/**
 * Created by Rene Argento on 20/04/25.
 */
public class TheLargestSmallestBox {

    private static class Result {
        double xMaxVolume;
        double xMinVolume1;
        double xMinVolume2;

        public Result(double xMaxVolume, double xMinVolume1, double xMinVolume2) {
            this.xMaxVolume = xMaxVolume;
            this.xMinVolume1 = xMinVolume1;
            this.xMinVolume2 = xMinVolume2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            double length = Double.parseDouble(data[0]);
            double width = Double.parseDouble(data[1]);

            Result result = computeXValues(length, width);
            outputWriter.printLine(String.format("%.3f %.3f %.3f", result.xMaxVolume, result.xMinVolume1,
                    result.xMinVolume2));
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result computeXValues(double length, double width) {
        double xMinVolume2 = Math.min(length, width) / 2;

        // f(x) = (l - 2x) * (w - 2x) * x
        // f(x) = (l - 2x) * (wx - 2x^2)
        // f(x) = lwx - 2x^2l - 2x^2w + 4x^3
        // f(x) = 4x^3 - 2x^2l - 2x^2w + lwx
        // Derivative:
        // f(x) = 12x^2 - 4xl - 4xw + lw
        // f(x) = 12x^2 - 4x(l + w) + lw

        // Quadratic formula
        // a = 12
        // b = -4(l + w)
        // c = lw
        // MaxV = -b - SQRT(b^2 - 4ac) / 2a

        double a = 12;
        double b = -4 * (length + width);
        double c = length * width;
        double delta = Math.sqrt(b * b - 4 * a * c);
        double xMaxVolume = (-b - delta) / (2 * a);

        return new Result(xMaxVolume, 0, xMinVolume2);
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
