package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 02/02/22.
 */
public class PrimeRingProblem {

    private static class Ring {
        int[] circles;

        public Ring(int[] circles) {
            this.circles = circles;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        boolean[] primes = getPrimes();
        int caseId = 1;

        String line = FastReader.getLine();
        while (line != null) {
            int circleSize = Integer.parseInt(line);
            List<Ring> primeRings = new ArrayList<>();
            int[] currentRing = new int[circleSize];
            currentRing[0] = 1;
            computePrimeRings(circleSize, primes, primeRings, currentRing, 1, 2);

            if (caseId > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("Case %d:", caseId));
            for (Ring primeRing : primeRings) {
                int[] circles = primeRing.circles;
                for (int i = 0; i < circles.length; i++) {
                    outputWriter.print(circles[i]);

                    if (i != circles.length - 1) {
                        outputWriter.print(" ");
                    }
                }
                outputWriter.printLine();
            }
            caseId++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void computePrimeRings(int circleSize, boolean[] primes, List<Ring> primeRings,
                                          int[] currentRing, int currentIndex, int mask) {
        if (currentIndex == currentRing.length) {
            int sum = currentRing[0] + currentRing[currentRing.length - 1];
            if (primes[sum]) {
                int[] copyRing = new int[currentRing.length];
                System.arraycopy(currentRing, 0, copyRing, 0, currentRing.length);
                Ring ring = new Ring(copyRing);
                primeRings.add(ring);
            }
            return;
        }

        for (int number = 2; number <= circleSize; number++) {
            if ((mask & (1 << number)) == 0) {
                int sum = currentRing[currentIndex - 1] + number;
                if (primes[sum]) {
                    int newMask = mask | (1 << number);
                    currentRing[currentIndex] = number;
                    computePrimeRings(circleSize, primes, primeRings, currentRing, currentIndex + 1, newMask);
                }
            }
        }
    }

    private static boolean[] getPrimes() {
        int[] primeValues = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31 };
        boolean[] primes = new boolean[32];
        for (int prime : primeValues) {
            primes[prime] = true;
        }
        return primes;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
