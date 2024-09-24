package chapter4.section6.d.tree;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/07/24.
 */
public class NotSoMobile {

    private static class Node {
        int weightLeft;
        int distanceLeft;
        int weightRight;
        int distanceRight;

        public Node(int weightLeft, int distanceLeft, int weightRight, int distanceRight) {
            this.weightLeft = weightLeft;
            this.distanceLeft = distanceLeft;
            this.weightRight = weightRight;
            this.distanceRight = distanceRight;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            Node mobile = readMobile();

            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(mobile == null ? "NO" : "YES");
        }
        outputWriter.flush();
    }

    private static Node readMobile() throws IOException {
        int weightLeft = FastReader.nextInt();
        int distanceLeft = FastReader.nextInt();
        int weightRight = FastReader.nextInt();
        int distanceRight = FastReader.nextInt();

        boolean invalid = false;

        if (weightLeft == 0) {
            Node subMobileLeft = readMobile();
            if (subMobileLeft == null) {
                invalid = true;
            } else {
                weightLeft = subMobileLeft.weightLeft + subMobileLeft.weightRight;
            }
        }
        if (weightRight == 0) {
            Node subMobileRight = readMobile();
            if (subMobileRight == null) {
                invalid = true;
            } else {
                weightRight = subMobileRight.weightLeft + subMobileRight.weightRight;
            }
        }

        if (invalid || distanceLeft * weightLeft != distanceRight * weightRight) {
            return null;
        }
        return new Node(weightLeft, distanceLeft, weightRight, distanceRight);
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
