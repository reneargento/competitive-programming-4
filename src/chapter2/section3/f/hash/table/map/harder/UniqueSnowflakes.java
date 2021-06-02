package chapter2.section3.f.hash.table.map.harder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/06/21.
 */
public class UniqueSnowflakes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int snowflakes = FastReader.nextInt();
            Map<Integer, Integer> snowflakeIndexes = new HashMap<>();

            int start = 0;
            int end = 0;
            int highestLength = 0;

            for (int index = 0; index < snowflakes; index++) {
                int snowflake = FastReader.nextInt();
                if (snowflakeIndexes.containsKey(snowflake)
                        && snowflakeIndexes.get(snowflake) >= start) {
                    if (highestLength == 0) {
                        highestLength = end;
                    } else {
                        int currentLength = end - start + 1;
                        if (currentLength > highestLength) {
                            highestLength = currentLength;
                        }
                    }

                    int duplicateSnowflakeIndex = snowflakeIndexes.get(snowflake);
                    start = duplicateSnowflakeIndex + 1;
                }
                end = index;
                snowflakeIndexes.put(snowflake, index);
            }

            int finalSegmentLength = end - start + 1;
            if (snowflakes > 0 && finalSegmentLength > highestLength) {
                highestLength = finalSegmentLength;
            }
            outputWriter.printLine(highestLength);
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
