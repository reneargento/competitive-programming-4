package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/10/21.
 */
public class WorkReduction {

    private static class Agency implements Comparable<Agency> {
        int minimumCost;
        String name;

        public Agency(int minimumCost, String name) {
            this.minimumCost = minimumCost;
            this.name = name;
        }

        @Override
        public int compareTo(Agency other) {
            if (minimumCost != other.minimumCost) {
                return Integer.compare(minimumCost, other.minimumCost);
            }
            return name.compareTo(other.name);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int startingWorkload = FastReader.nextInt();
            int targetWorkload = FastReader.nextInt();
            int agenciesNumber = FastReader.nextInt();
            Agency[] agencies = new Agency[agenciesNumber];

            for (int i = 0; i < agencies.length; i++) {
                String agencyLine = FastReader.getLine();
                String[] agencyLineComponents = agencyLine.split(":");
                String agencyName = agencyLineComponents[0];

                String[] costComponents = agencyLineComponents[1].split(",");
                int unitCost = Integer.parseInt(costComponents[0]);
                int halveCost = Integer.parseInt(costComponents[1]);

                int minimumCost = computeMinimumCost(startingWorkload, targetWorkload, unitCost, halveCost);
                agencies[i] = new Agency(minimumCost, agencyName);
            }
            Arrays.sort(agencies);

            outputWriter.printLine(String.format("Case %d", t));
            for (Agency agency : agencies) {
                outputWriter.printLine(String.format("%s %d", agency.name, agency.minimumCost));
            }
        }
        outputWriter.flush();
    }

    private static int computeMinimumCost(int startingWorkload, int targetWorkload, int unitCost,
                                          int halveCost) {
        int cost = 0;

        while (startingWorkload > targetWorkload) {
            long nextReduction;
            boolean canHalve = true;

            double halfWorkload = startingWorkload / 2.0;
            if (halfWorkload >= targetWorkload) {
                nextReduction = (int) Math.ceil(halfWorkload);
            } else {
                nextReduction = startingWorkload - targetWorkload;
                canHalve = false;
            }

            long totalUnitCost = unitCost * nextReduction;
            if (totalUnitCost < halveCost || !canHalve) {
                cost += totalUnitCost;
                startingWorkload -= nextReduction;
            } else {
                cost += halveCost;
                startingWorkload /= 2;
            }
        }
        return cost;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
