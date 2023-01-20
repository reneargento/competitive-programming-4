package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/01/23.
 */
public class Kutevi {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] knownAngles = new int[FastReader.nextInt()];
        int targetAngles = FastReader.nextInt();

        for (int i = 0; i < knownAngles.length; i++) {
            knownAngles[i] = FastReader.nextInt();
        }
        boolean[] possibleAngles = computePossibleAngles(knownAngles);
        for (int i = 0; i < targetAngles; i++) {
            int targetAngle = FastReader.nextInt();
            outputWriter.printLine(possibleAngles[targetAngle] ? "YES" : "NO");
        }
        outputWriter.flush();
    }

    private static boolean[] computePossibleAngles(int[] knownAngles) {
        boolean[] possibleAngles = new boolean[360];
        possibleAngles[0] = true;

        for (int angle = 0; angle < possibleAngles.length; angle++) {
            if (possibleAngles[angle]) {
                for (int knownAngle : knownAngles) {
                    for (int possibleAngle = (angle + knownAngle) % 360; possibleAngle != angle;
                         possibleAngle = (possibleAngle + knownAngle) % 360) {
                        possibleAngles[possibleAngle] = true;
                    }
                }
            }
        }
        return possibleAngles;
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
