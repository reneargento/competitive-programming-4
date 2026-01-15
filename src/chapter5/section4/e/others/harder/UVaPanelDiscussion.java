package chapter5.section4.e.others.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/01/26.
 */
public class UVaPanelDiscussion {

    private static class Result {
        long ways3People;
        long ways4People;

        public Result(long ways3People, long ways4People) {
            this.ways3People = ways3People;
            this.ways4People = ways4People;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int contestants = FastReader.nextInt();
        int countries = FastReader.nextInt();

        while (contestants != 0 || countries != 0) {
            int[] countriesFrequency = new int[countries + 1];
            for (int i = 0; i < contestants; i++) {
                countriesFrequency[FastReader.nextInt()]++;
            }

            Result result = computeWays(countriesFrequency);
            outputWriter.printLine(result.ways3People + " " + result.ways4People);

            contestants = FastReader.nextInt();
            countries = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeWays(int[] countriesFrequency) {
        long ways3People = 0;
        long ways4People = 0;

        List<Integer> frequencies = new ArrayList<>();
        for (int frequency : countriesFrequency) {
            if (frequency == 0) {
                continue;
            }
            frequencies.add(frequency);
        }

        // 3 people

        // 3 same
        for (int frequency : frequencies) {
            if (frequency >= 3) {
                ways3People += (long) frequency * (frequency - 1) * (frequency - 2) / 6;
            }
        }

        // 3 different
        for (int i = 0; i < frequencies.size(); i++) {
            for (int j = i + 1; j < frequencies.size(); j++) {
                for (int k = j + 1; k < frequencies.size(); k++) {
                    ways3People += (long) frequencies.get(i) * frequencies.get(j) * frequencies.get(k);
                }
            }
        }

        // 4 people

        // 4 same
        for (int frequency : frequencies) {
            if (frequency >= 4) {
                ways4People += (long) frequency * (frequency - 1) * (frequency - 2) * (frequency - 3) / 24;
            }
        }

        // 3 same + 1 different
        for (int i = 0; i < frequencies.size(); i++) {
            int frequency = frequencies.get(i);
            if (frequency >= 3) {
                long countriesChoose3 = (long) frequency * (frequency - 1) * (frequency - 2) / 6;

                for (int j = 0; j < frequencies.size(); j++) {
                    if (j == i) {
                        continue;
                    }
                    ways4People += countriesChoose3 * frequencies.get(j);
                }
            }
        }

        // 2 same + 1 + 1 different
        for (int i = 0; i < frequencies.size(); i++) {
            int frequency = frequencies.get(i);
            if (frequency >= 2) {
                long countriesChoose2 = (long) frequency * (frequency - 1) / 2;
                for (int j = 0; j < frequencies.size(); j++) {
                    if (j == i) {
                        continue;
                    }
                    for (int k = j + 1; k < frequencies.size(); k++) {
                        if (k == i) {
                            continue;
                        }
                        ways4People += countriesChoose2 * frequencies.get(j) * frequencies.get(k);
                    }
                }
            }
        }

        // 4 different
        for (int i = 0; i < frequencies.size(); i++) {
            for (int j = i + 1; j < frequencies.size(); j++) {
                for (int k = j + 1; k < frequencies.size(); k++) {
                    for (int l = k + 1; l < frequencies.size(); l++) {
                        ways4People += (long) frequencies.get(i) * frequencies.get(j) * frequencies.get(k)
                                * frequencies.get(l);
                    }
                }
            }
        }
        return new Result(ways3People, ways4People);
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
