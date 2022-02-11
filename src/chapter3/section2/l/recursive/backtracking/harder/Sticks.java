package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 09/02/22.
 */
// Based on https://github.com/morris821028/UVa/blob/master/volume003/307%20-%20Sticks.cpp
public class Sticks {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int sticksNumber = FastReader.nextInt();

        while (sticksNumber != 0) {
            int sum = 0;
            Integer[] sticks = new Integer[sticksNumber];
            for (int i = 0; i < sticksNumber; i++) {
                sticks[i] = FastReader.nextInt();
                sum += sticks[i];
            }

            Arrays.sort(sticks, new Comparator<Integer>() {
                @Override
                public int compare(Integer number1, Integer number2) {
                    return number2.compareTo(number1);
                }
            });
            int originalLength = computeOriginalLength(sticks, sum);
            outputWriter.printLine(originalLength);

            sticksNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeOriginalLength(Integer[] sticks, int sum) {
        for (int length  = sticks[0]; length <= sum / 2; length++) {
            if (sum % length != 0) {
                continue;
            }
            int numberOfSticks = sum / length;
            boolean[] used = new boolean[sticks.length];
            if (canCombineSticks(sticks, length, numberOfSticks, used, true,
                    0, 0, 0)) {
                return length;
            }
        }
        return sum;
    }

    private static boolean canCombineSticks(Integer[] sticks, long length, int numberOfSticks,
                                            boolean[] used, boolean isStart, int currentLength,
                                            int completeSticks, int index) {
        if (currentLength == length) {
            if (completeSticks + 1 == numberOfSticks) {
                return true;
            } else {
                return canCombineSticks(sticks, length, numberOfSticks, used, true,
                        0, completeSticks + 1, 0);
            }
        }

        if (isStart) {
            int nextStickIndex = 0;
            for (int i = 0; i < sticks.length; i++) {
                if (!used[i]) {
                    nextStickIndex = i;
                    break;
                }
            }
            used[nextStickIndex] = true;
            if (canCombineSticks(sticks, length, numberOfSticks, used, false,
                    sticks[nextStickIndex], completeSticks, nextStickIndex + 1)) {
                return true;
            }
            used[nextStickIndex] = false;
        } else {
            for (int i = index; i < sticks.length; i++) {
                if (!used[i] && currentLength + sticks[i] <= length) {
                    if (i > 0 && sticks[i].equals(sticks[i - 1]) && !used[i - 1]) {
                        continue;
                    }

                    used[i] = true;
                    if (canCombineSticks(sticks, length, numberOfSticks, used,
                            false, currentLength + sticks[i],
                            completeSticks, i + 1)) {
                        return true;
                    }
                    used[i] = false;
                    if (currentLength + sticks[i] == length) {
                        return false;
                    }
                }
            }
        }
        return false;
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
