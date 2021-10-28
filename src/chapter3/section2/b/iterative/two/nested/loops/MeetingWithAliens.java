package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/10/21.
 */
public class MeetingWithAliens {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int aliensNumber = FastReader.nextInt();

        while (aliensNumber != 0) {
            int[] aliens = new int[aliensNumber];
            int[] sortedAliensAsc = new int[aliensNumber];
            int[] sortedAliensDesc = new int[aliensNumber];

            for (int i = 0; i < aliens.length; i++) {
                aliens[i] = FastReader.nextInt();
                sortedAliensAsc[i] = i + 1;
                sortedAliensDesc[i] = aliens.length - i;
            }
            int minimumExchanges = computeMinimumExchanges(aliens, sortedAliensAsc, sortedAliensDesc);
            outputWriter.printLine(minimumExchanges);

            aliensNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMinimumExchanges(int[] aliens, int[] sortedAliensAsc, int[] sortedAliensDesc) {
        int minimumExchanges = Integer.MAX_VALUE;

        for (int i = 0; i < aliens.length; i++) {
            int exchangesSortedAsc = computeExchanges(aliens, sortedAliensAsc, i);
            int exchangesSortedDesc = computeExchanges(aliens, sortedAliensDesc, i);
            int currentMinimumExchanges = Math.min(exchangesSortedAsc, exchangesSortedDesc);

            if (currentMinimumExchanges < minimumExchanges) {
                minimumExchanges = currentMinimumExchanges;
            }
        }
        return minimumExchanges;
    }

    private static int computeExchanges(int[] aliens, int[] sortedAliens, int startIndex) {
        int exchanges = 0;
        int[] aliensCopy = new int[aliens.length];

        System.arraycopy(aliens, 0, aliensCopy, 0, aliens.length);
        Map<Integer, Integer> positionsMap = createPositionsMap(aliens);

        for (int i = 0; i < aliensCopy.length; i++) {
            int sortedAliensIndex = (startIndex + i) % sortedAliens.length;

            if (aliensCopy[i] != sortedAliens[sortedAliensIndex]) {
                exchanges++;

                int indexToMove = positionsMap.get(sortedAliens[sortedAliensIndex]);
                int currentAlien = aliensCopy[i];

                aliensCopy[i] = sortedAliens[sortedAliensIndex];
                positionsMap.put(sortedAliens[sortedAliensIndex], i);

                aliensCopy[indexToMove] = currentAlien;
                positionsMap.put(currentAlien, indexToMove);
            }
        }

        return exchanges;
    }

    private static Map<Integer, Integer> createPositionsMap(int[] aliens) {
        Map<Integer, Integer> positionsMap = new HashMap<>();

        for (int i = 0; i < aliens.length; i++) {
            positionsMap.put(aliens[i], i);
        }
        return positionsMap;
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
