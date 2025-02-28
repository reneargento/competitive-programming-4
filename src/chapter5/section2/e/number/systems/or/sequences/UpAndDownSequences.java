package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/02/25.
 */
public class UpAndDownSequences {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int currentValue = FastReader.nextInt();
        while (currentValue != 0) {
            int totalUpRuns = 0;
            int totalDownRuns = 0;
            long totalUpLength = 0;
            long totalDownLength = 0;
            boolean started = false;
            boolean isUpSequence = false;
            long currentLength = 0;
            int numberOfValues = 1;

            int nextValue = FastReader.nextInt();
            while (nextValue != 0) {
                numberOfValues++;

                if (nextValue > currentValue) {
                    if (started) {
                        if (isUpSequence) {
                            currentLength++;
                        } else {
                            totalDownRuns++;
                            totalDownLength += currentLength;
                            currentLength = 1;
                            isUpSequence = true;
                        }
                    } else {
                        started = true;
                        totalUpLength += currentLength;
                        currentLength = 1;
                        isUpSequence = true;
                    }
                } else if (nextValue < currentValue) {
                    if (started) {
                        if (isUpSequence) {
                            totalUpRuns++;
                            totalUpLength += currentLength;
                            currentLength = 1;
                            isUpSequence = false;
                        } else {
                            currentLength++;
                        }
                    } else {
                        started = true;
                        totalDownLength += currentLength;
                        currentLength = 1;
                    }
                } else {
                    currentLength++;
                }

                currentValue = nextValue;
                nextValue = FastReader.nextInt();
            }

            if (started) {
                if (isUpSequence) {
                    totalUpRuns++;
                    totalUpLength += currentLength;
                } else {
                    totalDownRuns++;
                    totalDownLength += currentLength;
                }
            }
            double upAverage =  0;
            double downAverage = 0;
            if (totalUpLength > 0) {
                upAverage = totalUpLength / (double) totalUpRuns;
            }
            if (totalDownLength > 0) {
                downAverage = totalDownLength / (double) totalDownRuns;
            }
            outputWriter.printLine(String.format("Nr values = %d:  %.6f %.6f", numberOfValues, upAverage, downAverage));
            currentValue = FastReader.nextInt();
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

        public void flush() {
            writer.flush();
        }
    }
}
