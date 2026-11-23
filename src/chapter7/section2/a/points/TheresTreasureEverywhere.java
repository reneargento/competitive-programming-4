package chapter7.section2.a.points;

import java.io.*;

/**
 * Created by Rene Argento on 20/11/2026.
 */
public class TheresTreasureEverywhere {

    private static class Treasure {
        Point location;
        double distance;

        public Treasure(Point location, double distance) {
            this.location = location;
            this.distance = distance;
        }
    }

    private static class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final double DIAGONAL_STEP_LENGTH = 0.70710678;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int mapId = 1;
        String map = FastReader.getLine();
        while (!map.equals("END")) {
            outputWriter.printLine(String.format("Map #%d", mapId));
            Treasure treasure = computeTreasureData(map);
            outputWriter.printLine(String.format("The treasure is located at (%.3f,%.3f).", treasure.location.x,
                    treasure.location.y));
            outputWriter.printLine(String.format("The distance to the treasure is %.3f.", treasure.distance));
            outputWriter.printLine();

            mapId++;
            map = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Treasure computeTreasureData(String map) {
        map = map.substring(0, map.length() - 1);
        String[] instructions = map.split(",");
        Point currentLocation = new Point(0, 0);

        for (String instruction : instructions) {
            move(currentLocation, instruction);
        }
        double distance = distanceBetweenPoints(0, 0, currentLocation.x, currentLocation.y);
        return new Treasure(currentLocation, distance);
    }

    private static void move(Point currentLocation, String instruction) {
        int numberIndexEnd = 0;
        for (int i = 0; i < instruction.length(); i++) {
            if (Character.isLetter(instruction.charAt(i))) {
                numberIndexEnd = i;
                break;
            }
        }
        int steps = Integer.parseInt(instruction.substring(0, numberIndexEnd));
        String direction = instruction.substring(numberIndexEnd);

        if (direction.equals("N")) {
            currentLocation.y += steps;
        } else if (direction.equals("NE")) {
            currentLocation.y += DIAGONAL_STEP_LENGTH * steps;
            currentLocation.x += DIAGONAL_STEP_LENGTH * steps;
        } else if (direction.equals("E")) {
            currentLocation.x += steps;
        } else if (direction.equals("SE")) {
            currentLocation.y -= DIAGONAL_STEP_LENGTH * steps;
            currentLocation.x += DIAGONAL_STEP_LENGTH * steps;
        } else if (direction.equals("S")) {
            currentLocation.y -= steps;
        } else if (direction.equals("SW")) {
            currentLocation.y -= DIAGONAL_STEP_LENGTH * steps;
            currentLocation.x -= DIAGONAL_STEP_LENGTH * steps;
        } else if (direction.equals("W")) {
            currentLocation.x -= steps;
        } else if (direction.equals("NW")) {
            currentLocation.y += DIAGONAL_STEP_LENGTH * steps;
            currentLocation.x -= DIAGONAL_STEP_LENGTH * steps;
        }
    }

    private static double distanceBetweenPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
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