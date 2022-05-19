package chapter3.section4.a.classical;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/05/22.
 */
public class Fishmongers {

    private static class Fishmonger implements Comparable<Fishmonger> {
        int fishWanted;
        long pricePerKilogram;

        public Fishmonger(int fishWanted, int pricePerKilogram) {
            this.fishWanted = fishWanted;
            this.pricePerKilogram = pricePerKilogram;
        }

        @Override
        public int compareTo(Fishmonger other) {
            return Long.compare(other.pricePerKilogram, pricePerKilogram);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] fishWeights = new int[FastReader.nextInt()];
        Fishmonger[] fishmongers = new Fishmonger[FastReader.nextInt()];

        for (int i = 0; i < fishWeights.length; i++) {
            fishWeights[i] = FastReader.nextInt();
        }
        for (int i = 0; i < fishmongers.length; i++) {
            fishmongers[i] = new Fishmonger(FastReader.nextInt(), FastReader.nextInt());
        }
        long maxMoney = computeMaxMoney(fishWeights, fishmongers);
        outputWriter.printLine(maxMoney);
        outputWriter.flush();
    }

    private static long computeMaxMoney(int[] fishWeights, Fishmonger[] fishmongers) {
        long money = 0;
        int fishSold = 0;
        Arrays.sort(fishWeights);
        Arrays.sort(fishmongers);

        for (int i = 0; i < fishmongers.length && fishSold < fishWeights.length; i++) {
            int quantity = fishmongers[i].fishWanted;
            int startFishIndex = fishWeights.length - 1 - fishSold;

            for (int j = startFishIndex; j >= 0 && j > startFishIndex - quantity; j--) {
                money += fishWeights[j] * fishmongers[i].pricePerKilogram;
                fishSold++;
            }
        }
        return money;
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
