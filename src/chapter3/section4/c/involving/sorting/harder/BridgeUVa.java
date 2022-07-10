package chapter3.section4.c.involving.sorting.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/07/22.
 */
public class BridgeUVa {

    private static class Group {
        int person1;
        int person2;

        public Group(int person1, int person2) {
            this.person1 = person1;
            this.person2 = person2;
        }
    }

    private static class Solution {
        int totalCrossingTime;
        List<Group> groups;

        public Solution(int totalCrossingTime, List<Group> groups) {
            this.totalCrossingTime = totalCrossingTime;
            this.groups = groups;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }

            int[] crossingTimes = new int[FastReader.nextInt()];
            for (int i = 0; i < crossingTimes.length; i++) {
                crossingTimes[i] = FastReader.nextInt();
            }
            Solution solution = crossBridge(crossingTimes);
            outputWriter.printLine(solution.totalCrossingTime);
            for (Group group : solution.groups) {
                outputWriter.print(group.person1);
                if (group.person2 != -1) {
                    outputWriter.print(" " + group.person2);
                }
                outputWriter.printLine();
            }
        }
        outputWriter.flush();
    }

    private static Solution crossBridge(int[] crossingTimes) {
        int totalCrossingTime;
        List<Group> groups = new ArrayList<>();

        Arrays.sort(crossingTimes);

        if (crossingTimes.length == 1) {
            totalCrossingTime = crossingTimes[0];
            groups.add(new Group(crossingTimes[0], -1));
        } else if (crossingTimes.length == 2) {
            totalCrossingTime = crossingTimes[1];
            groups.add(new Group(crossingTimes[0], crossingTimes[1]));
        } else if (crossingTimes.length == 3) {
            totalCrossingTime = crossingTimes[1];
            groups.add(new Group(crossingTimes[0], crossingTimes[1]));

            totalCrossingTime += crossingTimes[0];
            groups.add(new Group(crossingTimes[0], -1));

            totalCrossingTime += crossingTimes[2];
            groups.add(new Group(crossingTimes[0], crossingTimes[2]));
        } else {
            // Strategy 1
            totalCrossingTime = crossingTimes[1];
            groups.add(new Group(crossingTimes[0], crossingTimes[1]));

            totalCrossingTime += crossingTimes[0];
            groups.add(new Group(crossingTimes[0], -1));

            int deltaBetweenFirstTwo = crossingTimes[1] - crossingTimes[0];
            boolean switchToStrategy2 = false;
            boolean secondPersonOnLeft = false;

            for (int i = crossingTimes.length - 1; i >= 2; i--) {
                if (crossingTimes[i] == crossingTimes[1]) {
                    switchToStrategy2 = true;
                }

                totalCrossingTime += crossingTimes[i];
                if (switchToStrategy2) {
                    groups.add(new Group(crossingTimes[0], crossingTimes[i]));

                    if (i != 2) {
                        totalCrossingTime += crossingTimes[0];
                        groups.add(new Group(crossingTimes[0], -1));
                    }
                } else {
                    if (i == 2) {
                        groups.add(new Group(crossingTimes[0], crossingTimes[i]));
                    } else {
                        groups.add(new Group(crossingTimes[i - 1], crossingTimes[i]));
                        i--;

                        totalCrossingTime += crossingTimes[1];
                        groups.add(new Group(crossingTimes[1], -1));

                        if (i >= 4) {
                            int delta2 = crossingTimes[i - 2] - crossingTimes[1];
                            if (delta2 < deltaBetweenFirstTwo) {
                                switchToStrategy2 = true;
                                secondPersonOnLeft = true;
                            }
                        }

                        if (!switchToStrategy2) {
                            totalCrossingTime += crossingTimes[1];
                            groups.add(new Group(crossingTimes[0], crossingTimes[1]));

                            if (i != 2) {
                                totalCrossingTime += crossingTimes[0];
                                groups.add(new Group(crossingTimes[0], -1));
                            }
                        }
                    }
                }
            }
            if (secondPersonOnLeft) {
                totalCrossingTime += crossingTimes[0];
                groups.add(new Group(crossingTimes[0], -1));

                totalCrossingTime += crossingTimes[1];
                groups.add(new Group(crossingTimes[0], crossingTimes[1]));
            }

            int strategy2Time = crossingTimes[0] * (crossingTimes.length - 2);
            for (int i = 1; i < crossingTimes.length; i++) {
                strategy2Time += crossingTimes[i];
            }
            if (strategy2Time < totalCrossingTime) {
                return computeStrategy2(strategy2Time, crossingTimes);
            }
        }
        return new Solution(totalCrossingTime, groups);
    }

    private static Solution computeStrategy2(int strategy2Time, int[] crossingTimes) {
        List<Group> groups = new ArrayList<>();

        for (int i = crossingTimes.length - 1; i >= 1; i--) {
            groups.add(new Group(crossingTimes[0], crossingTimes[i]));

            if (i != 1) {
                groups.add(new Group(crossingTimes[0], -1));
            }
        }
        return new Solution(strategy2Time, groups);
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
