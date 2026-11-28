package chapter7.section2.a.points;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/11/2026.
 */
public class Yoyodyne {

    private static class Location {
        int x;
        int y;
        int z;

        public Location(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    private static class MissionResult {
        String state;
        double timePassed;
        double travelledDistance;
        double fuelLeft;
        double distanceRemaining;

        public MissionResult(String state, double timePassed, double travelledDistance, double fuelLeft,
                             double distanceRemaining) {
            this.state = state;
            this.timePassed = timePassed;
            this.travelledDistance = travelledDistance;
            this.fuelLeft = fuelLeft;
            this.distanceRemaining = distanceRemaining;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int fuel = FastReader.nextInt();
        int burnRate = FastReader.nextInt();
        int speed = FastReader.nextInt();
        int buoysNumber = FastReader.nextInt();
        int missionId = 1;

        while (fuel != 0 || burnRate != 0 || speed != 0 || buoysNumber != 0) {
            Location[] buoys = new Location[buoysNumber];
            for (int i = 0; i < buoys.length; i++) {
                buoys[i] = new Location(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
            }

            MissionResult missionResult = attemptMission(buoys, fuel, burnRate, speed);
            outputWriter.print(String.format("Mission %d: %s ", missionId, missionResult.state));
            if (missionResult.state.equals("SUCCESS!!")) {
                outputWriter.printLine(String.format("Time: %.2f  Traveled: %.2f  Fuel Left: %.2f",
                        missionResult.timePassed, missionResult.travelledDistance, missionResult.fuelLeft));
            } else {
                outputWriter.printLine(String.format("Traveled: %.2f  From Home: %.2f",
                        missionResult.travelledDistance, missionResult.distanceRemaining));
            }
            missionId++;
            fuel = FastReader.nextInt();
            burnRate = FastReader.nextInt();
            speed = FastReader.nextInt();
            buoysNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static MissionResult attemptMission(Location[] buoys, double fuel, int burnRate, int speed) {
        double timePassed = 0;
        double travelledDistance = 0;
        boolean[] visited = new boolean[buoys.length];

        Location location = new Location(0, 0, 1);
        int nextBuoyIndex = selectNextBuoy(location, buoys, visited);
        boolean failed = false;
        double distanceRemaining = 0;

        while (nextBuoyIndex != -1) {
            visited[nextBuoyIndex] = true;
            double distanceToNextBuoy = distanceBetweenPoints(location, buoys[nextBuoyIndex]);
            double timeToReachNextBuoy = distanceToNextBuoy / speed;
            double fuelNeeded = burnRate * timeToReachNextBuoy;

            if (fuelNeeded > fuel || failed) {
                if (fuel >= 0) {
                    timePassed += (timeToReachNextBuoy * fuel) / fuelNeeded;
                    double partialDistanceTravelled = (distanceToNextBuoy * fuel) / fuelNeeded;
                    travelledDistance += partialDistanceTravelled;
                    distanceRemaining += (distanceToNextBuoy - partialDistanceTravelled);
                } else {
                    distanceRemaining += distanceToNextBuoy;
                }
                failed = true;
            }
            location = buoys[nextBuoyIndex];
            if (!failed) {
                timePassed += timeToReachNextBuoy;
                travelledDistance += distanceToNextBuoy;
            }
            fuel -= fuelNeeded;
            nextBuoyIndex = selectNextBuoy(location, buoys, visited);
        }

        if (failed) {
            return new MissionResult("FAILURE!!", timePassed, travelledDistance, 0, distanceRemaining);
        }
        return new MissionResult("SUCCESS!!", timePassed, travelledDistance, fuel, 0);
    }

    private static int selectNextBuoy(Location location, Location[] buoys, boolean[] visited) {
        double smallestDistance = Double.POSITIVE_INFINITY;
        int selectedIndex = -1;

        for (int i = 0; i < buoys.length; i++) {
            if (visited[i]) {
                continue;
            }
            double distance = distanceBetweenPoints(location, buoys[i]);
            if (distance < smallestDistance) {
                smallestDistance = distance;
                selectedIndex = i;
            }
        }
        return selectedIndex;
    }

    private static double distanceBetweenPoints(Location point1, Location point2) {
        return Math.sqrt(Math.pow(point1.x - point2.x, 2)
                + Math.pow(point1.y - point2.y, 2)
                + Math.pow(point1.z - point2.z, 2) );
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