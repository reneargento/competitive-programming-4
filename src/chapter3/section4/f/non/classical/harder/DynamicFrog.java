package chapter3.section4.f.non.classical.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/08/22.
 */
public class DynamicFrog {

    private static class Stone {
        boolean isSmall;
        int location;
        boolean isBroken;

        public Stone(boolean isSmall, int location) {
            this.isSmall = isSmall;
            this.location = location;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            Stone[] stones = new Stone[FastReader.nextInt() + 2];
            stones[0] = new Stone(false, 0);
            stones[stones.length - 1] = new Stone(false, FastReader.nextInt());

            for (int i = 1; i < stones.length - 1; i++) {
                String description = FastReader.next();
                String[] data = description.split("-");
                boolean isSmall = data[0].equals("S");
                int location = Integer.parseInt(data[1]);
                stones[i] = new Stone(isSmall, location);
            }
            int maxLeap = computeMaxLeap(stones);
            outputWriter.printLine(String.format("Case %d: %d", t, maxLeap));
        }
        outputWriter.flush();
    }

    private static int computeMaxLeap(Stone[] stones) {
        int location = 0;
        int maxLeap = 0;
        int stoneIndex = 0;

        while (stoneIndex < stones.length) {
            maxLeap = Math.max(maxLeap, stones[stoneIndex].location - location);
            location = stones[stoneIndex].location;
            if (stoneIndex == stones.length - 1) {
                break;
            }

            if (stones[stoneIndex].isSmall) {
                stones[stoneIndex].isBroken = true;
            }

            if (stones[stoneIndex + 1].isSmall) {
                stoneIndex = stoneIndex + 2;
            } else {
                stoneIndex++;
            }
        }

        while (true) {
            maxLeap = Math.max(maxLeap, location - stones[stoneIndex].location);
            location = stones[stoneIndex].location;
            if (stoneIndex == 0) {
                break;
            }

            for (int j = stoneIndex - 1; j >= 0; j--) {
                if (!stones[j].isBroken) {
                    stoneIndex = j;
                    break;
                }
            }
        }
        return maxLeap;
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
