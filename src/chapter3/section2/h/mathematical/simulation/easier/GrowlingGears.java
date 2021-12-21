package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/12/21.
 */
// Based on http://earthmath.kennesaw.edu/main_site/review_topics/vertex_of_parabola.htm
public class GrowlingGears {

    private static class Gear {
        int a;
        int b;
        int c;

        public Gear(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            Gear[] gears = new Gear[FastReader.nextInt()];
            for (int i = 0; i < gears.length; i++) {
                gears[i] = new Gear(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
            }

            int bestGear = getBestGear(gears);
            outputWriter.printLine(bestGear);
        }
        outputWriter.flush();
    }

    private static int getBestGear(Gear[] gears) {
        double highestTorque = 0;
        int bestGear = 0;

        for (int i = 0; i < gears.length; i++) {
            Gear gear = gears[i];

            double xVertex = Math.abs((-gear.b) / (2.0 * gear.a));
            // Compute yVertex (highest torque)
            double yVertex = (-gear.a * xVertex * xVertex) + (gear.b * xVertex) + gear.c;

            if (yVertex > highestTorque) {
                highestTorque = yVertex;
                bestGear = i + 1;
            }
        }
        return bestGear;
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
