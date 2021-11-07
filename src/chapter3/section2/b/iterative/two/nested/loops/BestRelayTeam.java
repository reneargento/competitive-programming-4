package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/11/21.
 */
public class BestRelayTeam {

    private static class Runner implements Comparable<Runner> {
        String name;
        double firstLegTime;
        double otherLegsTime;

        public Runner(String name, double firstLegTime, double otherLegsTime) {
            this.name = name;
            this.firstLegTime = firstLegTime;
            this.otherLegsTime = otherLegsTime;
        }

        @Override
        public int compareTo(Runner other) {
            return Double.compare(otherLegsTime, other.otherLegsTime);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();

        int runnersNumber = FastReader.nextInt();
        Runner[] runners = new Runner[runnersNumber];

        for (int i = 0; i < runners.length; i++) {
            runners[i] = new Runner(FastReader.next(), FastReader.nextDouble(), FastReader.nextDouble());
        }
        computeBestTeam(runners);
    }

    private static void computeBestTeam(Runner[] runners) {
        OutputWriter outputWriter = new OutputWriter(System.out);
        Arrays.sort(runners);

        double bestTime = Double.MAX_VALUE;
        List<Runner> bestTeam = new ArrayList<>();

        for (int i = 0; i < runners.length; i++) {
            Runner firstRunner = runners[i];
            double currentTime = firstRunner.firstLegTime;
            List<Runner> currentTeam = new ArrayList<>();
            currentTeam.add(runners[i]);

            int endIndex = 3;

            for (int j = 0; j < endIndex; j++) {
                if (j != i) {
                    currentTime += runners[j].otherLegsTime;
                    currentTeam.add(runners[j]);
                } else {
                    endIndex = 4;
                }
            }

            if (currentTime < bestTime) {
                bestTime = currentTime;
                bestTeam = currentTeam;
            }
        }

        outputWriter.printLine(bestTime);
        for (Runner runner : bestTeam) {
            outputWriter.printLine(runner.name);
        }
        outputWriter.flush();
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
