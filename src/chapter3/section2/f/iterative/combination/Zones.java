package chapter3.section2.f.iterative.combination;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/12/21.
 */
public class Zones {

    private static class CommonArea {
        List<Integer> towers;
        int customers;

        public CommonArea(List<Integer> towers, int customers) {
            this.towers = towers;
            this.customers = customers;
        }
    }

    private static class Strategy implements Comparable<Strategy> {
        int[] towersToBuild;
        int customersServed;

        public Strategy(int[] towersToBuild, int customersServed) {
            this.towersToBuild = towersToBuild;
            this.customersServed = customersServed;
        }

        @Override
        public int compareTo(Strategy other) {
            if (customersServed != other.customersServed) {
                return Integer.compare(other.customersServed, customersServed);
            }

            int minLength = Math.min(towersToBuild.length, other.towersToBuild.length);
            for (int i = 0; i < minLength; i++) {
                if (towersToBuild[i] != other.towersToBuild[i]) {
                    return Integer.compare(towersToBuild[i], other.towersToBuild[i]);
                }
            }
            return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int towersPlanned = FastReader.nextInt();
        int towersToBuild = FastReader.nextInt();
        int caseNumber = 1;

        while (towersPlanned != 0 || towersToBuild != 0) {
            int[] customersPerTower = new int[towersPlanned];
            for (int i = 0; i < customersPerTower.length; i++) {
                customersPerTower[i] = FastReader.nextInt();
            }

            CommonArea[] commonAreas = new CommonArea[FastReader.nextInt()];
            for (int i = 0; i < commonAreas.length; i++) {
                int towersInCommonArea = FastReader.nextInt();
                List<Integer> towers = new ArrayList<>();

                for (int t = 0; t < towersInCommonArea; t++) {
                    towers.add((FastReader.nextInt() - 1));
                }
                int customersServed = FastReader.nextInt();
                commonAreas[i] = new CommonArea(towers, customersServed);
            }

            Strategy bestStrategy = computeBestStrategy(customersPerTower, commonAreas, towersToBuild);
            outputWriter.printLine(String.format("Case Number  %d", caseNumber));
            outputWriter.printLine(String.format("Number of Customers: %d", bestStrategy.customersServed));
            int[] towers = bestStrategy.towersToBuild;
            outputWriter.print("Locations recommended: ");
            for (int i = 0; i < towers.length; i++) {
                outputWriter.print((towers[i] + 1));

                if (i != towers.length - 1) {
                    outputWriter.print(" ");
                }
            }
            outputWriter.printLine();
            outputWriter.printLine();

            caseNumber++;
            towersPlanned = FastReader.nextInt();
            towersToBuild = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Strategy computeBestStrategy(int[] customersPerTower, CommonArea[] commonAreas,
                                                int towersToBuild) {
        return computeBestStrategy(customersPerTower, commonAreas, towersToBuild, 0, 0, 0);
    }

    private static Strategy computeBestStrategy(int[] customersPerTower, CommonArea[] commonAreas,
                                                int towersToBuild, int towersSelected, int index, int mask) {
        if (towersSelected == towersToBuild) {
            return buildStrategy(customersPerTower, commonAreas, towersToBuild, mask);
        }
        if (index == customersPerTower.length) {
            return null;
        }

        int maskWithTower = mask | (1 << index);
        Strategy strategyWithTower = computeBestStrategy(customersPerTower, commonAreas, towersToBuild,
                towersSelected + 1, index + 1, maskWithTower);
        Strategy strategyWithoutTower = computeBestStrategy(customersPerTower, commonAreas, towersToBuild,
                towersSelected, index + 1, mask);

        if (strategyWithTower != null && strategyWithoutTower != null) {
            if (strategyWithTower.compareTo(strategyWithoutTower) < 0) {
                return strategyWithTower;
            } else {
                return strategyWithoutTower;
            }
        } else if (strategyWithTower != null) {
            return strategyWithTower;
        } else {
            return strategyWithoutTower;
        }
    }

    private static Strategy buildStrategy(int[] customersPerTower, CommonArea[] commonAreas, int towersToBuild,
                                          int mask) {
        int[] towers = new int[towersToBuild];
        int towersIndex = 0;
        int costumersServed = 0;

        for (int bit = 0; bit < customersPerTower.length && towersIndex < towers.length; bit++) {
            if ((mask & (1 << bit)) > 0) {
                towers[towersIndex++] = bit;
                costumersServed += customersPerTower[bit];
            }
        }
        costumersServed -= reduceDuplicateCount(towers, commonAreas);
        return new Strategy(towers, costumersServed);
    }

    private static int reduceDuplicateCount(int[] towers, CommonArea[] commonAreas) {
        int duplicateCount = 0;

        for (CommonArea commonArea : commonAreas) {
            int towerSelected = 0;

            for (int towerInArea : commonArea.towers) {
                for (int tower : towers) {
                    if (tower == towerInArea) {
                        towerSelected++;
                        break;
                    }
                }
            }
            if (towerSelected > 1) {
                duplicateCount += (towerSelected - 1) * commonArea.customers;
            }
        }
        return duplicateCount;
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
