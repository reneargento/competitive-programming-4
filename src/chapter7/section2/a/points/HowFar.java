package chapter7.section2.a.points;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/11/2026.
 */
public class HowFar {

    private static class Body {
        double radius;
        double completeCycleTime;

        public Body(double radius, double completeCycleTime) {
            this.radius = radius;
            this.completeCycleTime = completeCycleTime;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            Body[] bodies = new Body[Integer.parseInt(data[0])];
            int queryTime = Integer.parseInt(data[1]);

            for (int i = 0; i < bodies.length; i++) {
                bodies[i] = new Body(FastReader.nextInt(), FastReader.nextInt());
            }

            double[] sunDistances = computeSunDistances(bodies, queryTime);
            outputWriter.print(String.format("%.4f", sunDistances[0]));
            for (int i = 1; i < sunDistances.length; i++) {
                outputWriter.print(String.format(" %.4f", sunDistances[i]));
            }
            outputWriter.printLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static double[] computeSunDistances(Body[] bodies, int queryTime) {
        double[] sunDistances = new double[bodies.length];
        double x = 0;
        double y = 0;

        for (int i = 0; i < bodies.length; i++) {
            double angle = 2 * Math.PI * queryTime / bodies[i].completeCycleTime;
            x += bodies[i].radius * Math.cos(angle);
            y += bodies[i].radius * Math.sin(angle);
            sunDistances[i] = Math.sqrt(x * x + y * y);
        }
        return sunDistances;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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