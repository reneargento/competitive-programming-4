package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/10/21.
 */
public class TourDeFrance {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        int frontClusterSprocketsNumber = FastReader.nextInt();

        while (frontClusterSprocketsNumber != 0) {
            int rearClusterSprocketsNumber = FastReader.nextInt();
            double[] driveRatios = new double[frontClusterSprocketsNumber * rearClusterSprocketsNumber];
            int driveRatiosIndex = 0;

            double[] frontClusterTeeth = new double[frontClusterSprocketsNumber];

            for (int i = 0; i < frontClusterTeeth.length; i++) {
                frontClusterTeeth[i] = FastReader.nextInt();
            }

            for (int i = 0; i < rearClusterSprocketsNumber; i++) {
                double rearClusterTeeth = FastReader.nextInt();

                for (int j = 0; j < frontClusterTeeth.length; j++) {
                    driveRatios[driveRatiosIndex++] = rearClusterTeeth / frontClusterTeeth[j];
                }
            }

            double maximumSpread = computeMaximumSpread(driveRatios);
            outputWriter.printLine(String.format("%.2f", maximumSpread));

            frontClusterSprocketsNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static double computeMaximumSpread(double[] driveRatios) {
        double maximumSpread = Double.MIN_VALUE;

        Arrays.sort(driveRatios);
        for (int i = 1; i < driveRatios.length; i++) {
            double spread = driveRatios[i] / driveRatios[i - 1];

            if (spread > maximumSpread) {
                maximumSpread = spread;
            }
        }
        return maximumSpread;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
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