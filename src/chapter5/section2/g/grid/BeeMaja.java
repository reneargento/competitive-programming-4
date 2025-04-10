package chapter5.section2.g.grid;

import java.io.*;

/**
 * Created by Rene Argento on 07/04/25.
 */
public class BeeMaja {

    private static class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class WilliNumber {
        int value;

        public WilliNumber(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Coordinate[] majaCoordinates = computeMajaCoordinates();

        String line = FastReader.getLine();
        while (line != null) {
            int williNumber = Integer.parseInt(line);
            outputWriter.printLine(majaCoordinates[williNumber].x + " " + majaCoordinates[williNumber].y);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Coordinate[] computeMajaCoordinates() {
        Coordinate[] majaCoordinates = new Coordinate[100001];
        majaCoordinates[1] = new Coordinate(0, 0);
        majaCoordinates[2] = new Coordinate(0, 1);
        Coordinate currentCoordinate = new Coordinate(0, 1);
        WilliNumber williNumber = new WilliNumber(3);
        int steps = 1;

        while (williNumber.value < majaCoordinates.length) {
            walkNorthwest(majaCoordinates, steps, currentCoordinate, williNumber);
            walkNorth(majaCoordinates, steps, currentCoordinate, williNumber);
            walkNortheast(majaCoordinates, steps, currentCoordinate, williNumber);
            walkSoutheast(majaCoordinates, steps, currentCoordinate, williNumber);
            steps++;
            walkSouth(majaCoordinates, steps, currentCoordinate, williNumber);
            walkSouthwest(majaCoordinates, steps - 1, currentCoordinate, williNumber);
        }
        return majaCoordinates;
    }

    private static void walkNorthwest(Coordinate[] majaCoordinates, int steps, Coordinate currentCoordinate,
                                      WilliNumber williNumber) {
        walk(majaCoordinates, steps, currentCoordinate, williNumber, -1, 0);
    }

    private static void walkNorth(Coordinate[] majaCoordinates, int steps, Coordinate currentCoordinate,
                                  WilliNumber williNumber) {
        walk(majaCoordinates, steps, currentCoordinate, williNumber, 0, -1);
    }

    private static void walkNortheast(Coordinate[] majaCoordinates, int steps, Coordinate currentCoordinate,
                                      WilliNumber williNumber) {
        walk(majaCoordinates, steps, currentCoordinate, williNumber, 1, -1);
    }

    private static void walkSoutheast(Coordinate[] majaCoordinates, int steps, Coordinate currentCoordinate,
                                      WilliNumber williNumber) {
        walk(majaCoordinates, steps, currentCoordinate, williNumber, 1, 0);
    }

    private static void walkSouth(Coordinate[] majaCoordinates, int steps, Coordinate currentCoordinate,
                                  WilliNumber williNumber) {
        walk(majaCoordinates, steps, currentCoordinate, williNumber, 0, 1);
    }

    private static void walkSouthwest(Coordinate[] majaCoordinates, int steps, Coordinate currentCoordinate,
                                      WilliNumber williNumber) {
        walk(majaCoordinates, steps, currentCoordinate, williNumber, -1, 1);
    }

    private static void walk(Coordinate[] majaCoordinates, int steps, Coordinate currentCoordinate,
                             WilliNumber williNumber, int deltaX, int deltaY) {
        for (int i = 0; i < steps; i++) {
            if (williNumber.value >= majaCoordinates.length) {
                return;
            }
            Coordinate nextCoordinate = new Coordinate(currentCoordinate.x + deltaX, currentCoordinate.y + deltaY);
            majaCoordinates[williNumber.value] = nextCoordinate;

            currentCoordinate.x = nextCoordinate.x;
            currentCoordinate.y = nextCoordinate.y;
            williNumber.value++;
        }
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
