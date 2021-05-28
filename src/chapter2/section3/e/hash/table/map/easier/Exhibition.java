package chapter2.section3.e.hash.table.map.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 28/05/21.
 */
@SuppressWarnings("unchecked")
public class Exhibition {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int friends = FastReader.nextInt();
            Map<Integer, Set<Integer>> stampOwners = new HashMap<>();
            Set<Integer>[] friendsStamps = new HashSet[friends];

            for (int i = 0; i < friendsStamps.length; i++) {
                int numberOfStamps = FastReader.nextInt();
                Set<Integer> stamps = new HashSet<>();

                for (int s = 0; s < numberOfStamps; s++) {
                    int stamp = FastReader.nextInt();
                    Set<Integer> currentStampOwners = stampOwners.get(stamp);
                    if (currentStampOwners == null) {
                        currentStampOwners = new HashSet<>();
                    }
                    currentStampOwners.add(i);
                    stampOwners.put(stamp, currentStampOwners);

                    stamps.add(stamp);
                }
                friendsStamps[i] = stamps;
            }
            outputWriter.print(String.format("Case %d: ", t));
            computeIncomeDistribution(stampOwners, friendsStamps, outputWriter);
        }
        outputWriter.flush();
    }

    private static void computeIncomeDistribution(Map<Integer, Set<Integer>> stampOwners,
                                                  Set<Integer>[] friendsStamps, OutputWriter outputWriter) {
        double uniqueStamps = countUniqueStamps(stampOwners);

        for (int i = 0; i < friendsStamps.length; i++) {
            int friendUniqueStamps = 0;

            for (int stamp : friendsStamps[i]) {
                if (stampOwners.get(stamp).size() == 1) {
                    friendUniqueStamps++;
                }
            }

            double incomePercent = friendUniqueStamps / uniqueStamps * 100;
            outputWriter.print(String.format("%.6f", incomePercent) + "%");

            if (i != friendsStamps.length - 1) {
                outputWriter.print(" ");
            }
        }
        outputWriter.printLine();
    }

    private static int countUniqueStamps(Map<Integer, Set<Integer>> stampOwners) {
        int uniqueStamps = 0;

        for (Set<Integer> owners : stampOwners.values()) {
            if (owners.size() == 1) {
                uniqueStamps++;
            }
        }
        return uniqueStamps;
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
