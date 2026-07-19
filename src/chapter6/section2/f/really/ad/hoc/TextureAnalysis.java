package chapter6.section2.f.really.ad.hoc;

import java.io.*;

/**
 * Created by Rene Argento on 15/07/2026.
 */
public class TextureAnalysis {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int caseId = 1;
        String line = FastReader.getLine();
        while (!line.equals("END")) {
            String result = isEven(line);
            outputWriter.printLine(caseId + " " + result);

            caseId++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String isEven(String pixels) {
        int distance = -1;
        int lastBlackPixelIndex = -1;

        for (int i = 0; i < pixels.length(); i++) {
            if (pixels.charAt(i) == '*') {
                if (lastBlackPixelIndex != -1) {
                    int currentDistance = i - lastBlackPixelIndex;
                    if (distance == -1) {
                        distance = currentDistance;
                    } else if (distance != currentDistance) {
                        return "NOT EVEN";
                    }
                }
                lastBlackPixelIndex = i;
            }
        }
        return "EVEN";
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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

        public void flush() {
            writer.flush();
        }
    }
}