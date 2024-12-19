package chapter5.section2.a.finding.simple.formula.or.pattern.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/12/24.
 */
public class SequentialManufacturing {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int machines = FastReader.nextInt();
        int itemsToProduce = FastReader.nextInt();
        int[] processingTimes = new int[machines];

        for (int i = 0; i < processingTimes.length; i++) {
            processingTimes[i] = FastReader.nextInt();
        }
        long manufactureTime = computeManufactureTime(itemsToProduce, processingTimes);
        outputWriter.printLine(manufactureTime);
        outputWriter.flush();
    }

    private static long computeManufactureTime(int itemsToProduce, int[] processingTimes) {
        long totalTime = 0;
        long maxProcessingTime = 0;

        for (int time : processingTimes) {
            totalTime += time;
            maxProcessingTime = Math.max(maxProcessingTime, time);
        }
        totalTime += maxProcessingTime * (itemsToProduce - 1);
        return totalTime;
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
