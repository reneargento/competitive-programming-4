package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/11/21.
 */
public class BikeGears {

    private static class Gear implements Comparable<Gear> {
        long frontTeeth;
        long backTeeth;

        public Gear(int frontTeeth, int backTeeth) {
            this.frontTeeth = frontTeeth;
            this.backTeeth = backTeeth;
        }

        @Override
        public int compareTo(Gear other) {
            long ratio1 = frontTeeth * other.backTeeth;
            long ratio2 = other.frontTeeth * backTeeth;

            if (ratio1 == ratio2) {
                return Long.compare(frontTeeth, other.frontTeeth);
            }
            return Long.compare(ratio1, ratio2);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] frontSprockets = new int[FastReader.nextInt()];
        int[] backSprockets = new int[FastReader.nextInt()];

        for (int i = 0; i < frontSprockets.length; i++) {
            frontSprockets[i] = FastReader.nextInt();
        }
        for (int i = 0; i < backSprockets.length; i++) {
            backSprockets[i] = FastReader.nextInt();
        }

        Gear[] gears = new Gear[frontSprockets.length * backSprockets.length];
        int gearIndex = 0;
        for (int frontSprocket : frontSprockets) {
            for (int backSprocket : backSprockets) {
                gears[gearIndex++] = new Gear(frontSprocket, backSprocket);
            }
        }

        Arrays.sort(gears);

        for (Gear gear : gears) {
            outputWriter.printLine(String.format("(%d,%d)", gear.frontTeeth, gear.backTeeth));
        }
        outputWriter.flush();
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
