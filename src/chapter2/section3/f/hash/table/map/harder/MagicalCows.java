package chapter2.section3.f.hash.table.map.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 03/06/21.
 */
public class MagicalCows {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int maximumCowsAllowed = FastReader.nextInt();
        int initialFarms = FastReader.nextInt();
        int visitDays = FastReader.nextInt();
        int[] farmCows = new int[initialFarms];

        for (int i = 0; i < farmCows.length; i++) {
            farmCows[i] = FastReader.nextInt();
        }
        Map<Integer, Long> farmsPerDay = predictCowPopulation(farmCows, maximumCowsAllowed);

        for (int i = 0; i < visitDays; i++) {
            long farms = farmsPerDay.get(FastReader.nextInt());
            outputWriter.printLine(farms);
        }
        outputWriter.flush();
    }

    private static Map<Integer, Long> predictCowPopulation(int[] farmCows, int maximumCowsAllowed) {
        Map<Integer, Long> farmsPerDay = new HashMap<>();
        farmsPerDay.put(0, (long) farmCows.length);
        long[] newFarms = new long[51];

        for (int i = 0; i < farmCows.length; i++) {
            int dayWhenNewFarmIsNeeded = computeDayWhenNewFarmIsNeeded(farmCows[i], maximumCowsAllowed);
            if (dayWhenNewFarmIsNeeded <= 50) {
                newFarms[dayWhenNewFarmIsNeeded]++;
            }
        }

        for (int i = 1; i < newFarms.length; i++) {
            newFarms[i] += (newFarms[i - 1] * 2);
        }

        for (int day = 1; day <= 50; day++) {
            long totalFarms = farmsPerDay.get(day - 1) + newFarms[day];
            farmsPerDay.put(day, totalFarms);
        }
        return farmsPerDay;
    }

    private static int computeDayWhenNewFarmIsNeeded(double cows, int maximumCowsAllowed) {
        double lgCows = Math.log(maximumCowsAllowed / cows) / Math.log(2);
        if (lgCows == (int) lgCows) {
            return ((int) lgCows) + 1;
        } else {
            return (int) Math.ceil(lgCows);
        }
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
