package chapter2.section3.f.hash.table.map.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 04/06/21.
 */
public class ParallelAnalysis {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int traces = FastReader.nextInt();

        for (int t = 1; t <= traces; t++) {
            int traceLength = FastReader.nextInt();
            int totalTime = 0;
            Map<Integer, Integer> memoryWrittenToTimeMap = new HashMap<>();

            for (int i = 0; i < traceLength; i++) {
                int memoryReferences = FastReader.nextInt();
                int highestCurrentTime = 0;

                for (int m = 0; m < memoryReferences - 1; m++) {
                    int memoryRead = FastReader.nextInt();
                    int highestTimeToWriteMemory = memoryWrittenToTimeMap.getOrDefault(memoryRead, 0);
                    highestCurrentTime = Math.max(highestCurrentTime, highestTimeToWriteMemory);
                }
                int memoryWritten = FastReader.nextInt();
                highestCurrentTime++;
                memoryWrittenToTimeMap.put(memoryWritten, highestCurrentTime);

                totalTime = Math.max(totalTime, highestCurrentTime);
            }
            outputWriter.printLine(t + " " + totalTime);
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
