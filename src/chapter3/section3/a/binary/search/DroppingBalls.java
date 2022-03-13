package chapter3.section3.a.binary.search;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/03/22.
 */
public class DroppingBalls {

    private static class BallConfiguration {
        int depth;
        int ballNumber;

        public BallConfiguration(int depth, int ballNumber) {
            this.depth = depth;
            this.ballNumber = ballNumber;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BallConfiguration that = (BallConfiguration) o;
            return depth == that.depth && ballNumber == that.ballNumber;
        }

        @Override
        public int hashCode() {
            return Objects.hash(depth, ballNumber);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
     //   Map<BallConfiguration, Integer> configurationsMap = precomputeConfigurations();

        for (int t = 0; t < tests; t++) {
            int depth = FastReader.nextInt();
            int ballNumber = FastReader.nextInt();

//            BallConfiguration ballConfiguration = new BallConfiguration(depth, ballNumber);
//            int stopPosition = configurationsMap.get(ballConfiguration);
            int stopPosition = getPositionWithBitManipulation(depth, ballNumber);
            outputWriter.printLine(stopPosition);
        }
        outputWriter.flush();
    }

    private static int getPositionWithBitManipulation(int depth, int ballNumber) {
        int powerOf2 = (int) Math.pow(2, depth - 1);
        int[] bits = getBits(ballNumber - 1, depth - 1);
        int reverseNumber = reverseAndGetNumber(bits);
        return powerOf2 + reverseNumber;
    }

    private static int[] getBits(int number, int length) {
        int[] bits = new int[length];
        int index = bits.length - 1;

        while (number > 0) {
            int bit = number % 2;
            bits[index] = bit;
            index--;
            number /= 2;
        }
        return bits;
    }

    private static int reverseAndGetNumber(int[] bits) {
        int number = 0;
        int powerOf2 = 1;

        for (int bit : bits) {
            if (bit == 1) {
                number += powerOf2;
            }
            powerOf2 *= 2;
        }
        return number;
    }

    // *************************************************************************************
    // Alternative solution with binary search
    private static Map<BallConfiguration, Integer> precomputeConfigurations() {
        Map<BallConfiguration, Integer> configurationsMap = new HashMap<>();

        for (int depth = 2; depth <= 20; depth++) {
            getStopPositionsInDepth(depth, configurationsMap);
        }
        return configurationsMap;
    }

    private static void getStopPositionsInDepth(int depth, Map<BallConfiguration, Integer> configurationsMap) {
        int lastNonTerminalNode = (int) Math.pow(2, depth - 1) - 1;
        int maxNumberOfBalls = lastNonTerminalNode * 2 + 1;
        boolean[] nodesFlags = new boolean[lastNonTerminalNode + 1];

        for (int ball = 1; ball <= maxNumberOfBalls; ball++) {
            int currentNode = 1;

            while (currentNode <= lastNonTerminalNode) {
                int leftChild = currentNode * 2;
                int rightChild = leftChild + 1;
                boolean isFlagSet = nodesFlags[currentNode];
                nodesFlags[currentNode] = !nodesFlags[currentNode];

                if (isFlagSet) {
                    currentNode = rightChild;
                } else {
                    currentNode = leftChild;
                }
            }
            BallConfiguration ballConfiguration = new BallConfiguration(depth, ball);
            configurationsMap.put(ballConfiguration, currentNode);
        }
    }
    // *************************************************************************************

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
