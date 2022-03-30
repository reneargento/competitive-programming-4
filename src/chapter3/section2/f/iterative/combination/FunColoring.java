package chapter3.section2.f.iterative.combination;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Note: this problem has I/O issues in UVa and is flagged in uDebug, the solution doesn't get accepted.
 * Source: https://github.com/arun-karthikeyan/Algorithms/issues/3
 * and https://www.udebug.com/UVa/12348
 * On my investigation I found that the first line of the input file has no numbers and that the second line is EOF.
 *
 * Created by Rene Argento on 05/12/21.
 */
@SuppressWarnings("unchecked")
public class FunColoring {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.getInteger(FastReader.getLine());

        for (int t = 0; t < tests; t++) {
            String[] lineValues = FastReader.getLine().trim().split(" ");
            int maxValue = FastReader.getInteger(lineValues[0]) - 1;
            List<Integer>[] sets = new List[FastReader.getInteger(lineValues[1])];

            for (int s = 0; s < sets.length; s++) {
                sets[s] = new ArrayList<>();
                String line = FastReader.getLine().trim();
                String[] values = line.split(" ");

                for (String value : values) {
                    int elementValue = FastReader.getInteger(value) - 1;
                    sets[s].add(elementValue);
                }
            }
            boolean functionExists = doesFunctionExists(sets, maxValue, 0, 0);
            outputWriter.print(functionExists ? "Y" : "N");

            if (t < tests - 1) {
                FastReader.getLine();
            }
        }
        outputWriter.printLine();
        outputWriter.flush();
    }

    private static boolean doesFunctionExists(List<Integer>[] sets, int maxValue, int mask, int index) {
        if (index == maxValue + 1) {
            return isFunctionCorrect(sets, mask);
        }

        int maskWithColor = mask | (1 << index);
        return doesFunctionExists(sets, maxValue, mask, index + 1)
                || doesFunctionExists(sets, maxValue, maskWithColor, index + 1);
    }

    private static boolean isFunctionCorrect(List<Integer>[] sets, int mask) {
        for (List<Integer> set : sets) {
            if (!hasUniqueElementColor(set, mask)) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasUniqueElementColor(List<Integer> set, int mask) {
        int zeroCount = 0;
        int oneCount = 0;

        for (int element : set) {
            int color = mask & (1 << element);
            if (color > 0) {
                oneCount++;
            } else {
                zeroCount++;
            }
        }
        return oneCount == 1 || zeroCount == 1;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        private static int getInteger(String string) {
            int number = 0;

            for (int i = 0; i < string.length(); i++) {
                char value = string.charAt(i);

                if (value >= '0' && value <= '9') {
                    number = number * 10 + (value - '0');
                } else {
                    break;
                }
            }
            return number;
        }

        private static String getLine() throws IOException {
            return reader.readLine();
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
