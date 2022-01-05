package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/01/22.
 */
// Based on https://github.com/morris821028/UVa/blob/master/volume111/11130%20-%20Billiard%20bounces.cpp
public class BilliardBounces {

    private static class Bounces {
        double verticalWallBounces;
        double horizontalWallBounces;

        public Bounces(double verticalWallBounces, double horizontalWallBounces) {
            this.verticalWallBounces = verticalWallBounces;
            this.horizontalWallBounces = horizontalWallBounces;
        }
    }

    private static final double PI = Math.acos(-1);

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int horizontalLength = FastReader.nextInt();
        int verticalLength = FastReader.nextInt();
        int velocity = FastReader.nextInt();
        int angle = FastReader.nextInt();
        int time = FastReader.nextInt();

        while (horizontalLength != 0 || verticalLength != 0 || velocity != 0 || angle != 0 || time != 0) {
            Bounces bounces = computeBounces(horizontalLength, verticalLength, velocity, angle, time);
            outputWriter.printLine(String.format("%.0f %.0f", bounces.verticalWallBounces, bounces.horizontalWallBounces));

            horizontalLength = FastReader.nextInt();
            verticalLength = FastReader.nextInt();
            velocity = FastReader.nextInt();
            angle = FastReader.nextInt();
            time = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Bounces computeBounces(int horizontalLength, int verticalLength, int velocity,
                                          int angle, int time) {
        int distance = (velocity * time) / 2;
        double angleInRadian = angle / 180.0 * PI;

        double horizontalDistance = distance * Math.cos(angleInRadian);
        double verticalDistance = distance * Math.sin(angleInRadian);
        return new Bounces(horizontalDistance / horizontalLength,
                verticalDistance / verticalLength);
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
