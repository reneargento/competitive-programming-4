package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 31/01/22.
 */
public class GoodMorning {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int number = FastReader.nextInt();
            boolean[][] visited = new boolean[210][10];
            int closestNumber = getClosestNumber(number, 0, 1, visited);
            outputWriter.printLine(closestNumber);
        }
        outputWriter.flush();
    }

    private static int getClosestNumber(int targetNumber, int currentNumber, int currentDigit,
                                        boolean[][] visited) {
        if (currentNumber > targetNumber || visited[currentNumber][currentDigit]) {
            return currentNumber;
        }
        visited[currentNumber][currentDigit] = true;

        int bestDistance = Integer.MAX_VALUE;
        int bestNumber = Integer.MAX_VALUE;

        List<Integer> neighbors = getNeighbors(currentDigit);
        int nextNumberPartial = currentNumber * 10;
        for (int neighborDigit : neighbors) {
            int nextNumber = nextNumberPartial + neighborDigit;
            int bestNumberWithExtraDigit = getClosestNumber(targetNumber, nextNumber, neighborDigit, visited);
            int distanceWithDigit = Math.abs(bestNumberWithExtraDigit - targetNumber);

            if (distanceWithDigit < bestDistance) {
                bestDistance = distanceWithDigit;
                bestNumber = bestNumberWithExtraDigit;
            }

            int bestNumberWithoutExtraDigit = getClosestNumber(targetNumber, currentNumber, neighborDigit, visited);
            int distanceWithoutDigit = Math.abs(bestNumberWithoutExtraDigit - targetNumber);
            if (distanceWithoutDigit < bestDistance) {
                bestDistance = distanceWithoutDigit;
                bestNumber = bestNumberWithoutExtraDigit;
            }
        }
        return bestNumber;
    }

    private static List<Integer> getNeighbors(int digit) {
        List<Integer> neighbors = new ArrayList<>();
        neighbors.add(digit);

        if (digit == 0) {
            return neighbors;
        }

        if (digit == 8) {
            neighbors.add(9);
            neighbors.add(0);
            return neighbors;
        }

        if (digit % 3 != 0) {
            neighbors.add(digit + 1);
        }
        if (digit <= 6) {
            neighbors.add(digit + 3);
        }
        return neighbors;
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
