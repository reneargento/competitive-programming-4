package chapter3.section4.f.non.classical.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/08/22.
 */
public class Parliament {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }

            int delegates = FastReader.nextInt();
            List<Integer> groupSizes = computeGroupSizes(delegates);
            outputWriter.print(groupSizes.get(0));
            for (int i = 1; i < groupSizes.size(); i++) {
                outputWriter.print(" " + groupSizes.get(i));
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static List<Integer> computeGroupSizes(int delegates) {
        List<Integer> groupSizes = new ArrayList<>();
        long sum = 0;
        int groupSize = 2;
        while (sum < delegates) {
            groupSizes.add(groupSize);
            sum += groupSize;
            groupSize++;
        }

        if (sum > delegates) {
            if (sum - delegates == 1) {
                groupSizes.remove(Integer.valueOf(2));
                groupSizes.remove(groupSizes.size() - 1);
                groupSizes.add(groupSize);
            } else {
                Integer numberToRemove = (int) (sum - delegates);
                groupSizes.remove(numberToRemove);
            }
        }
        return groupSizes;
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
