package chapter4.session6.f.eulerian.graph;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 16/08/24.
 */
public class SnowClearing {

    private static class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        FastReader.getLine();

        for (int t = 0; t < tests; t++) {
            FastReader.getLine();
            String line = FastReader.getLine();

            double totalDistance = 0;

            while (line != null && !line.isEmpty()) {
                String[] data = line.split(" ");
                int x1 = Integer.parseInt(data[0]);
                int y1 = Integer.parseInt(data[1]);
                int x2 = Integer.parseInt(data[2]);
                int y2 = Integer.parseInt(data[3]);

                Coordinate coordinate1 = new Coordinate(x1, y1);
                Coordinate coordinate2 = new Coordinate(x2, y2);
                totalDistance += distanceBetweenPoints(coordinate1, coordinate2);

                line = FastReader.getLine();
            }

            String clearStreetsTime = getClearStreetsTime(totalDistance * 2);
            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(clearStreetsTime);
        }
        outputWriter.flush();
    }

    private static String getClearStreetsTime(double totalDistance) {
        double totalHours = totalDistance / 1000.0 / 20;
        int hours = (int) totalHours;
        double minutesSection = ((totalHours - hours) * 100);
        int minutes = (int) Math.round(minutesSection * 60 / 100.0);

        // Handle edge case
        if (minutes == 60) {
            hours++;
            minutes = 0;
        }
        return hours + ":" + String.format("%02d", minutes);
    }

    public static double distanceBetweenPoints(Coordinate point1, Coordinate point2) {
        return Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
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
