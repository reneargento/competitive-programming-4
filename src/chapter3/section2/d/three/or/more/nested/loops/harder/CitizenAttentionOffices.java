package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/11/21.
 */
public class CitizenAttentionOffices {

    private static class Location implements Comparable<Location> {
        int location1;
        int location2;
        int location3;
        int location4;
        int location5;

        public Location(int location1, int location2, int location3, int location4, int location5) {
            this.location1 = location1;
            this.location2 = location2;
            this.location3 = location3;
            this.location4 = location4;
            this.location5 = location5;
        }

        @Override
        public int compareTo(Location other) {
            if (location1 != other.location1) {
                return Integer.compare(location1, other.location1);
            }
            if (location2 != other.location2) {
                return Integer.compare(location2, other.location2);
            }
            if (location3 != other.location3) {
                return Integer.compare(location3, other.location3);
            }
            if (location4 != other.location4) {
                return Integer.compare(location4, other.location4);
            }
            return Integer.compare(location5, other.location5);
        }
    }

    private static class Area {
        int row;
        int column;

        public Area(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[][] city = new int[5][5];
            int people = FastReader.nextInt();
            List<Area> peopleAreas = new ArrayList<>();

            for (int i = 0; i < people; i++) {
                int row = FastReader.nextInt();
                int column = FastReader.nextInt();
                int population = FastReader.nextInt();

                city[row][column] = population;
                peopleAreas.add(new Area(row, column));
            }

            Location locations = getAttractionLocations(city, peopleAreas);
            outputWriter.printLine(String.format("%d %d %d %d %d", locations.location1,
                    locations.location2, locations.location3, locations.location4, locations.location5));
        }
        outputWriter.flush();
    }

    private static Location getAttractionLocations(int[][] city, List<Area> peopleAreas) {
        List<Location> locations = new ArrayList<>();
        int bestDistancesSum = Integer.MAX_VALUE;

        for (int location1 = 0; location1 < 25; location1++) {
            for (int location2 = location1 + 1; location2 < 25; location2++) {
                for (int location3 = location2 + 1; location3 < 25; location3++) {
                    for (int location4 = location3 + 1; location4 < 25; location4++) {
                        for (int location5 = location4 + 1; location5 < 25; location5++) {
                            int distances = 0;

                            for (Area personArea : peopleAreas) {
                                distances += getShortestDistance(location1, location2, location3, location4,
                                        location5, personArea, city);
                            }

                            if (distances < bestDistancesSum) {
                                locations = new ArrayList<>();
                                bestDistancesSum = distances;
                            }
                            if (distances == bestDistancesSum) {
                                locations.add(new Location(location1, location2, location3, location4, location5));
                            }
                        }
                    }
                }
            }
        }

        Collections.sort(locations);
        return locations.get(0);
    }

    private static int getShortestDistance(int location1, int location2, int location3, int location4,
                                           int location5, Area personArea, int[][] city) {
        List<Integer> distances = new ArrayList<>();
        distances.add(getDistance(location1, personArea, city));
        distances.add(getDistance(location2, personArea, city));
        distances.add(getDistance(location3, personArea, city));
        distances.add(getDistance(location4, personArea, city));
        distances.add(getDistance(location5, personArea, city));

        Collections.sort(distances);
        return distances.get(0);
    }

    private static int getDistance(int location, Area personArea, int[][] city) {
        int row = location / 5;
        int column = location % 5;

        return (Math.abs(personArea.row - row) + Math.abs(personArea.column - column))
                * city[personArea.row][personArea.column];
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
