package chapter4.session6.a.shortest.longest.paths.on.dag;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/05/24.
 */
public class SafePassage {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] timeToCross = new int[FastReader.nextInt()];
        for (int i = 0; i < timeToCross.length; i++) {
            timeToCross[i] = FastReader.nextInt();
        }

        int minimumGroupTime = computeMinimumGroupTime(timeToCross);
        outputWriter.printLine(minimumGroupTime);
        outputWriter.flush();
    }

    private static int computeMinimumGroupTime(int[] timeToCross) {
        int minimumGroupTime = 0;
        Arrays.sort(timeToCross);

        int personID;
        for (personID = timeToCross.length - 1; personID > 2; personID -= 2) {
            int fastestPersonGoesBackTwice = timeToCross[personID] + timeToCross[0] + timeToCross[personID - 1] + timeToCross[0];
            int secondFastestPersonGoesBack = timeToCross[1] + timeToCross[0] + timeToCross[personID] + timeToCross[1];
            minimumGroupTime += Math.min(fastestPersonGoesBackTwice, secondFastestPersonGoesBack);
        }

        if (personID == 2) {
            minimumGroupTime += timeToCross[2] + timeToCross[0] + timeToCross[1];
        }
        if (personID == 1) {
            minimumGroupTime += timeToCross[1];
        }
        if (personID == 0) {
            minimumGroupTime += timeToCross[0];
        }
        return minimumGroupTime;
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
