package chapter3.section4.a.classical;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/05/22.
 */
public class AMatchMakingProblem {

    private static class Result {
        int bachelorsLeft;
        int youngestBachelorLeft;

        public Result(int bachelorsLeft, int youngestBachelorLeft) {
            this.bachelorsLeft = bachelorsLeft;
            this.youngestBachelorLeft = youngestBachelorLeft;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int bachelors = FastReader.nextInt();
        int spinsters = FastReader.nextInt();
        int caseId = 1;

        while (bachelors != 0 || spinsters != 0) {
            int[] bachelorsAges = new int[bachelors];
            int[] spinstersAges = new int[spinsters];

            for (int i = 0; i < bachelorsAges.length; i++) {
                bachelorsAges[i] = FastReader.nextInt();
            }
            for (int i = 0; i < spinstersAges.length; i++) {
                spinstersAges[i] = FastReader.nextInt();
            }

            Result result = matchPeople(bachelorsAges, spinstersAges);
            outputWriter.print(String.format("Case %d: %d", caseId, result.bachelorsLeft));
            if (result.bachelorsLeft != 0) {
                outputWriter.print(" " + result.youngestBachelorLeft);
            }
            outputWriter.printLine();

            caseId++;
            bachelors = FastReader.nextInt();
            spinsters = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result matchPeople(int[] bachelorsAges, int[] spinstersAges) {
        if (bachelorsAges.length <= spinstersAges.length) {
            return new Result(0, -1);
        }

        Arrays.sort(bachelorsAges);
        int bachelorsLeft = bachelorsAges.length - spinstersAges.length;
        return new Result(bachelorsLeft, bachelorsAges[0]);
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
