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

    private static final int SOUTH_INDEX = 4;
    private static final int SOUTHWEST_INDEX = 5;

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

        // northwest -> north -> northeast -> southeast -> south -> southwest
        int[] neighborRows = { 0, -1, -1, 0, 1, 1 };
        int[] neighborColumns = { -1, 0, 1, 1, 0, -1 };

        while (williNumber.value < majaCoordinates.length) {
            for (int i = 0; i < neighborRows.length; i++) {
                if (i == SOUTH_INDEX) {
                    steps++;
                }
                int stepsToUse = i != SOUTHWEST_INDEX ? steps : steps - 1;
                walk(majaCoordinates, stepsToUse, currentCoordinate, williNumber, neighborColumns[i], neighborRows[i]);
            }
        }
        return majaCoordinates;
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
