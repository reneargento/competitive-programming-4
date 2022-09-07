package chapter3.section4.f.non.classical.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/08/22.
 */
public class Playground {

    private static final double EPSILON = 0.00000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int wires = FastReader.nextInt();

        while (wires != 0) {
            double[] radii = new double[wires];
            for (int i = 0; i < radii.length; i++) {
                radii[i] = FastReader.nextDouble();
            }

            boolean canMakeFigure = canMakeFigure(radii);
            outputWriter.printLine(canMakeFigure ? "YES" : "NO");
            wires = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean canMakeFigure(double[] radii) {
        Arrays.sort(radii);

        for (int i = 0; i < radii.length; i++) {
            if (canMakeFigureConsideringLongestWire(radii, i)) {
                return true;
            }
        }
        return false;
    }

    private static boolean canMakeFigureConsideringLongestWire(double[] radii, int longestWireIndex) {
        double sumOfOtherWires = 0;
        for (int i = 0; i < longestWireIndex; i++) {
            sumOfOtherWires += radii[i];
        }
        return sumOfOtherWires + EPSILON > radii[longestWireIndex];
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
