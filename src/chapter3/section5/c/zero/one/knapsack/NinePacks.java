package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/11/22.
 */
public class NinePacks {

    private static final int OFFSET = 1000;
    private static final int INFINITE = 1000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] hotDogPacks = readPacks();
        int[] bunPacks = readPacks();

        int minimumPacksToPurchase = computeMinimumPacksToPurchase(hotDogPacks, bunPacks);
        if (minimumPacksToPurchase != INFINITE) {
            outputWriter.printLine(minimumPacksToPurchase);
        } else {
            outputWriter.printLine("impossible");
        }
        outputWriter.flush();
    }

    private static int[] readPacks() throws IOException {
        int[] packs = new int[FastReader.nextInt()];
        for (int i = 0; i < packs.length; i++) {
            packs[i] = FastReader.nextInt();
        }
        return packs;
    }

    private static int computeMinimumPacksToPurchase(int[] hotDogPacks, int[] bunPacks) {
        int maxLength = Math.max(hotDogPacks.length, bunPacks.length);
        // dp[hot dogs selected][buns selected][difference between them] = minimum number of packs selected
        int[][][] dp = new int[maxLength + 1][maxLength + 1][2001];
        for (int[][] packs : dp) {
            for (int[] differences : packs) {
                Arrays.fill(differences, -1);
            }
        }

        int minimumPacksToPurchase = INFINITE;
        for (int startingHotDogId = 0; startingHotDogId < hotDogPacks.length; startingHotDogId++) {
            int packsSelected = computeMinimumPacksToPurchase(hotDogPacks, bunPacks, dp, startingHotDogId + 1, 0,
                    OFFSET + hotDogPacks[startingHotDogId]) + 1;
            minimumPacksToPurchase = Math.min(minimumPacksToPurchase, packsSelected);
        }
        return minimumPacksToPurchase;
    }

    private static int computeMinimumPacksToPurchase(int[] hotDogPacks, int[] bunPacks, int[][][] dp,
                                                     int hotDogId, int bunId, int difference) {
        if (difference == OFFSET)  {
            return 0;
        }
        if (difference < OFFSET && hotDogId == hotDogPacks.length) {
            return INFINITE;
        }
        if (difference > OFFSET && bunId == bunPacks.length) {
            return INFINITE;
        }
        if (dp[hotDogId][bunId][difference] != -1) {
            return dp[hotDogId][bunId][difference];
        }

        if (difference > OFFSET) {
            int packsWithoutBun = computeMinimumPacksToPurchase(hotDogPacks, bunPacks, dp, hotDogId, bunId + 1,
                    difference);
            int packsWithBun = computeMinimumPacksToPurchase(hotDogPacks, bunPacks, dp, hotDogId, bunId + 1,
                    difference - bunPacks[bunId]) + 1;
            dp[hotDogId][bunId][difference] = Math.min(packsWithoutBun, packsWithBun);
        } else {
            int packsWithoutHotDog = computeMinimumPacksToPurchase(hotDogPacks, bunPacks, dp, hotDogId + 1, bunId,
                    difference);
            int packsWithHotDog = computeMinimumPacksToPurchase(hotDogPacks, bunPacks, dp, hotDogId + 1, bunId,
                    difference + hotDogPacks[hotDogId]) + 1;
            dp[hotDogId][bunId][difference] = Math.min(packsWithoutHotDog, packsWithHotDog);
        }
        return dp[hotDogId][bunId][difference];
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
