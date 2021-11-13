package chapter3.section2.c.three.or.more.nested.loops.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/11/21.
 */
public class GrandpaPepesPizza {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int circumference = Integer.parseInt(data[0]);
            int olives = Integer.parseInt(data[1]);

            int sectorSize = circumference / olives;
            int[] olivesLocation = new int[olives];
            for (int i = 0; i < olivesLocation.length; i++) {
                olivesLocation[i] = FastReader.nextInt();
            }

            if (isPossibleToDivide(sectorSize, olivesLocation)) {
                outputWriter.printLine("S");
            } else {
                outputWriter.printLine("N");
            }

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean isPossibleToDivide(int sectorSize, int[] olivesLocation) {
        double startPoint = 0.5;
        double endPoint = startPoint + sectorSize;

        for (double point = startPoint; point < endPoint; point++) {
            if (isPossibleToDivideStartingInPoint(sectorSize, olivesLocation, point)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isPossibleToDivideStartingInPoint(int sectorSize, int[] olivesLocation,
                                                             double startPoint) {
        int oliveIndex = 0;

        if (olivesLocation[0] < startPoint) {
            oliveIndex++;
        }

        for (double point = startPoint; oliveIndex < olivesLocation.length; point += sectorSize,
                oliveIndex++) {
            int oliveLocation = olivesLocation[oliveIndex];
            if (oliveLocation < point
                    || oliveLocation > point + sectorSize) {
                return false;
            }
        }
        return true;
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
