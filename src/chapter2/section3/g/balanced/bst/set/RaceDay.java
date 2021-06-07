package chapter2.section3.g.balanced.bst.set;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/06/21.
 */
public class RaceDay {

    private static class RunnerResult implements Comparable<RunnerResult> {
        String name;
        String bib;
        String[] times;
        int[] ranks;

        public RunnerResult(String name, String bib) {
            this.name = name;
            this.bib = bib;
            times = new String[3];
            ranks = new int[3];
        }

        @Override
        public int compareTo(RunnerResult other) {
            return name.compareTo(other.name);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;
            RunnerResult that = (RunnerResult) other;
            return Objects.equals(bib, that.bib);
        }

        @Override
        public int hashCode() {
            return Objects.hash(bib);
        }
    }

    private static class RunnerTime implements Comparable<RunnerTime> {
        String name;
        String bib;
        String time;

        public RunnerTime(String name, String bib, String time) {
            this.name = name;
            this.bib = bib;
            this.time = time;
        }

        @Override
        public int compareTo(RunnerTime other) {
            if (!time.equals(other.time)) {
                return time.compareTo(other.time);
            }
            return name.compareTo(other.name);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int runners = FastReader.nextInt();

        while (runners != 0) {
            Map<String, String> bibToName = new HashMap<>();
            TreeSet<RunnerTime> split1Times = new TreeSet<>();
            TreeSet<RunnerTime> split2Times = new TreeSet<>();
            TreeSet<RunnerTime> finishTimes = new TreeSet<>();

            for (int i = 0; i < runners; i++) {
                String firstName = FastReader.next();
                String lastName = FastReader.next();
                String name = lastName + ", " + firstName;
                String bib = FastReader.next();
                bibToName.put(bib, name);
            }

            for (int i = 0; i < runners * 3; i++) {
                String bib = FastReader.next();
                String name = bibToName.get(bib);
                String section = FastReader.next();
                String time = FastReader.next();

                RunnerTime runnerTime = new RunnerTime(name, bib, time);
                switch (section) {
                    case "S1": split1Times.add(runnerTime); break;
                    case "S2": split2Times.add(runnerTime); break;
                    case "F": finishTimes.add(runnerTime);
                }
            }

            Map<String, RunnerResult> resultsMap = new HashMap<>();
            mergeResults(resultsMap, split1Times, 0);
            mergeResults(resultsMap, split2Times, 1);
            mergeResults(resultsMap, finishTimes, 2);

            printResults(resultsMap, outputWriter);
            runners = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void mergeResults(Map<String, RunnerResult> resultsMap, TreeSet<RunnerTime> times,
                                     int sectionIndex) {
        int rank = 1;
        int position = 1;
        String previousTime = null;

        for (RunnerTime runnerTime : times) {
            if (previousTime != null && !previousTime.equals(runnerTime.time)) {
                rank = position;
            }
            String name = runnerTime.name;

            if (!resultsMap.containsKey(name)) {
                RunnerResult runnerResult = new RunnerResult(name, runnerTime.bib);
                resultsMap.put(name, runnerResult);
            }
            RunnerResult runnerResult = resultsMap.get(name);
            runnerResult.times[sectionIndex] = runnerTime.time;
            runnerResult.ranks[sectionIndex] = rank;

            position++;
            previousTime = runnerTime.time;
        }
    }

    private static void printResults(Map<String, RunnerResult> resultsMap, OutputWriter outputWriter) {
        List<RunnerResult> results = new ArrayList<>(resultsMap.values());
        Collections.sort(results);

        String headerOutputFormat = "%-20s%10s%10s%10s%10s%10s%10s%10s";
        String outputFormat = "%-20s%10s%10s%10d%10s%10d%10s%10d";
        outputWriter.printLine(String.format(headerOutputFormat, "NAME", "BIB", "SPLIT1", "RANK", "SPLIT2", "RANK",
                "FINISH", "RANK"));
        for (RunnerResult result : results) {
            outputWriter.printLine(String.format(outputFormat, result.name, result.bib, result.times[0], result.ranks[0],
                    result.times[1], result.ranks[1], result.times[2], result.ranks[2]));
        }
        outputWriter.printLine();
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
