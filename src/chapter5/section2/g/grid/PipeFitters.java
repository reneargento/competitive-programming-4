package chapter5.section2.g.grid;

import java.io.*;

/**
 * Created by Rene Argento on 10/04/25.
 */
public class PipeFitters {

    private static class Result {
        int maximumPipes;
        String pattern;

        public Result(int maximumPipes, String pattern) {
            this.maximumPipes = maximumPipes;
            this.pattern = pattern;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            double diameter1 = Double.parseDouble(data[0]);
            double diameter2 = Double.parseDouble(data[1]);

            Result result = packPipes(diameter1, diameter2);
            outputWriter.printLine(result.maximumPipes + " " + result.pattern);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result packPipes(double diameter1, double diameter2) {
        int gridMaxPipes = (int) diameter1 * (int) diameter2;
        int skewMaxPipes1 = computeSkewPattern(diameter1, diameter2);
        int skewMaxPipes2 = computeSkewPattern(diameter2, diameter1);
        int skewMaxPipes = Math.max(skewMaxPipes1, skewMaxPipes2);

        if (gridMaxPipes >= skewMaxPipes) {
            return new Result(gridMaxPipes, "grid");
        } else {
            return new Result(skewMaxPipes, "skew");
        }
    }

    private static int computeSkewPattern(double diameter1, double diameter2) {
        if (diameter2 < 1) {
            return 0;
        }
        int height = 1 + (int) ((diameter2 - 1) / Math.sqrt(3) * 2);
        return (int) Math.ceil(height / 2.0) * (int) diameter1 + height / 2 * (int) (diameter1 - 0.5);
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
