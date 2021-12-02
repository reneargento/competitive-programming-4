package chapter3.section2.e.iterative.permutation;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/12/21.
 */
public class AToweringProblem {

    private static class Tower {
        Integer[] boxes;

        public Tower(Integer[] boxes) {
            this.boxes = boxes;
            Arrays.sort(boxes, Collections.reverseOrder());
        }

        @Override
        public String toString() {
            return boxes[0] + " " + boxes[1] + " " + boxes[2];
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] boxHeights = new int[6];
        for (int i = 0; i < boxHeights.length; i++) {
            boxHeights[i] = FastReader.nextInt();
        }
        int tower1Height = FastReader.nextInt();
        int tower2Height = FastReader.nextInt();

        Tower[] towers = buildTowers(boxHeights, tower1Height, tower2Height);
        outputWriter.printLine(towers[0] + " " + towers[1]);
        outputWriter.flush();
    }

    private static Tower[] buildTowers(int[] boxHeights, int tower1Height, int tower2Height) {
        int[] currentBoxes = new int[boxHeights.length];
        return buildTowers(boxHeights, tower1Height, tower2Height, currentBoxes, 0, 0);
    }

    private static Tower[] buildTowers(int[] boxHeights, int tower1Height, int tower2Height, int[] currentBoxes,
                                     int index, int mask) {
        if (mask == (1 << boxHeights.length) - 1) {
            if (areHeightsCorrect(currentBoxes, tower1Height, tower2Height)) {
                Tower[] towers = new Tower[2];
                Integer[] boxes1 = { currentBoxes[0], currentBoxes[1], currentBoxes[2] };
                towers[0] = new Tower(boxes1);
                Integer[] boxes2 = { currentBoxes[3], currentBoxes[4], currentBoxes[5] };
                towers[1] = new Tower(boxes2);
                return towers;
            } else {
                return null;
            }
        }

        for (int i = 0; i < boxHeights.length; i++) {
            if ((mask & (1 << i)) == 0) {
                int newMask = mask | (1 << i);
                currentBoxes[index] = boxHeights[i];
                Tower[] towers = buildTowers(boxHeights, tower1Height, tower2Height, currentBoxes, index + 1, newMask);
                if (towers != null) {
                    return towers;
                }
            }
        }
        return null;
    }

    private static boolean areHeightsCorrect(int[] currentBoxes, int tower1Height, int tower2Height) {
        return (currentBoxes[0] + currentBoxes[1] + currentBoxes[2] == tower1Height)
                && (currentBoxes[3] + currentBoxes[4] + currentBoxes[5] == tower2Height);
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
