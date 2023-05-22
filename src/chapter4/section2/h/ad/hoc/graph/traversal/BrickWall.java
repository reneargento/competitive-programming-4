package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 18/05/23.
 */
public class BrickWall {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] bricks = new int[FastReader.nextInt()];
        int[] brickWidths = new int[3];
        for (int i = 0; i < brickWidths.length; i++) {
            brickWidths[i] = FastReader.nextInt();
        }

        int targetWidth = 0;
        Set<Integer> forbiddenWidths = new HashSet<>();
        for (int i = 0; i < bricks.length; i++) {
            bricks[i] = FastReader.nextInt();
            targetWidth += bricks[i];

            if (i != bricks.length - 1) {
                forbiddenWidths.add(targetWidth);
            }
        }

        boolean isPossible = isPossibleToBuildRow(brickWidths, targetWidth, forbiddenWidths);
        outputWriter.printLine(isPossible ? "YES" : "NO");
        outputWriter.flush();
    }

    private static boolean isPossibleToBuildRow(int[] brickWidths, int targetWidth, Set<Integer> forbiddenWidths) {
        // dp[brick width 1 used][brick width 2 used][brick width 3 used]
        int[][][] dp = new int[brickWidths[0] + 1][brickWidths[1] + 1][brickWidths[2] + 1];
        for (int[][] values : dp) {
            for (int[] widths : values) {
                Arrays.fill(widths, -1);
            }
        }
        return isPossibleToBuildRow(brickWidths, forbiddenWidths, dp, targetWidth, 0, 0, 0)
                == 1;
    }

    private static int isPossibleToBuildRow(int[] brickWidths, Set<Integer> forbiddenWidths, int[][][] dp,
                                            int targetWidth, int width1Used, int width2Used, int width3Used) {
        if (dp[width1Used][width2Used][width3Used] != -1) {
            return dp[width1Used][width2Used][width3Used];
        }

        int currentWidth = width1Used + width2Used * 2 + width3Used * 3;
        if (forbiddenWidths.contains(currentWidth)) {
            dp[width1Used][width2Used][width3Used] = 0;
            return 0;
        }

        if (currentWidth == targetWidth) {
            return 1;
        }

        if (width1Used < brickWidths[0]) {
            int isPossible = isPossibleToBuildRow(brickWidths, forbiddenWidths, dp, targetWidth, width1Used + 1,
                    width2Used, width3Used);
            if (isPossible == 1) {
                return 1;
            }
        }
        if (width2Used < brickWidths[1]) {
            int isPossible = isPossibleToBuildRow(brickWidths, forbiddenWidths, dp, targetWidth, width1Used,
                    width2Used + 1, width3Used);
            if (isPossible == 1) {
                return 1;
            }
        }
        if (width3Used < brickWidths[2]) {
            int isPossible = isPossibleToBuildRow(brickWidths, forbiddenWidths, dp, targetWidth, width1Used,
                    width2Used, width3Used + 1);
            if (isPossible == 1) {
                return 1;
            }
        }
        dp[width1Used][width2Used][width3Used] = 0;
        return 0;
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
