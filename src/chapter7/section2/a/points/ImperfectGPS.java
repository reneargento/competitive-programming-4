package chapter7.section2.a.points;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/11/2026.
 */
public class ImperfectGPS {

    private static class Position {
        double x;
        double y;
        int timeReached;

        public Position(double x, double y, int timeReached) {
            this.x = x;
            this.y = y;
            this.timeReached = timeReached;
        }
    }

    private static final double EPSILON = .00000000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Position[] positions = new Position[FastReader.nextInt()];
        int gpsInterval = FastReader.nextInt();
        for (int i = 0; i < positions.length; i++) {
            positions[i] = new Position(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
        }

        double lostDistancePercentage = computeLostDistance(positions, gpsInterval);
        outputWriter.printLine(lostDistancePercentage);
        outputWriter.flush();
    }

    private static double computeLostDistance(Position[] positions, int gpsInterval) {
        double actualDistance = computeDistanceBetweenPositions(positions);
        if (actualDistance == 0) {
            return 0;
        }

        List<Position> gpsPositions = computeGpsPositions(positions, gpsInterval);
        Position[] gpsPositionsArray = gpsPositions.toArray(new Position[0]);
        double gpsDistance = computeDistanceBetweenPositions(gpsPositionsArray);

        double distanceDifference = actualDistance - gpsDistance;
        return (distanceDifference / actualDistance) * 100;
    }

    private static double computeDistanceBetweenPositions(Position[] positions) {
        double actualDistance = 0;
        for (int i = 1; i < positions.length; i++) {
            actualDistance += distanceBetweenPoints(positions[i - 1].x, positions[i - 1].y, positions[i].x, positions[i].y);
        }
        return actualDistance;
    }

    private static List<Position> computeGpsPositions(Position[] positions, int gpsInterval) {
        List<Position> gpsPositions = new ArrayList<>();
        gpsPositions.add(positions[0]);

        int finalTimeReached = positions[positions.length - 1].timeReached;
        for (int time = gpsInterval; time <= finalTimeReached; time += gpsInterval) {
            Position position = getPositionRead(positions, time);
            gpsPositions.add(position);
        }
        if (finalTimeReached % gpsInterval != 0) {
            Position position = getPositionRead(positions, finalTimeReached);
            gpsPositions.add(position);
        }
        return gpsPositions;
    }

    private static Position getPositionRead(Position[] positions, int gpsTimestamp) {
        Position positionBefore = positions[0];

        for (int i = 0; i < positions.length; i++) {
            if (Math.abs(positions[i].timeReached - gpsTimestamp) < EPSILON) {
                return positions[i];
            }
            if (positions[i].timeReached - gpsTimestamp > EPSILON) {
                if (i > 0) {
                    positionBefore = positions[i - 1];
                }
                Position positionAfter = positions[i];
                double actualDeltaTime = positionAfter.timeReached - positionBefore.timeReached;
                double distanceX = positionAfter.x - positionBefore.x;
                double distanceY = positionAfter.y - positionBefore.y;

                double gpsDeltaTime = gpsTimestamp - positionBefore.timeReached;
                double ratio = gpsDeltaTime / actualDeltaTime;
                double x = positionBefore.x + (ratio * distanceX);
                double y = positionBefore.y + (ratio * distanceY);
                return new Position(x, y, 0);
            }
        }
        return null;
    }

    private static double distanceBetweenPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
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

        public void flush() {
            writer.flush();
        }
    }
}