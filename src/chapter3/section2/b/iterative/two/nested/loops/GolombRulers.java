package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 01/10/21.
 */
public class GolombRulers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] marksString = line.split(" ");
            int[] marks = new int[marksString.length];
            int highestMark = 0;

            for (int i = 0; i < marks.length; i++) {
                marks[i] = Integer.parseInt(marksString[i]);
                highestMark = Math.max(highestMark, marks[i]);
            }

            boolean[] distances = computeDistances(marks, highestMark);
            if (distances == null) {
                outputWriter.printLine("not a ruler");
            } else {
                List<Integer> missingDistances = getMissingDistances(distances);

                if (missingDistances.isEmpty()) {
                    outputWriter.printLine("perfect");
                } else {
                    outputWriter.print("missing ");
                    for (int i = 0; i < missingDistances.size(); i++) {
                        outputWriter.print(missingDistances.get(i));

                        if (i != missingDistances.size() - 1) {
                            outputWriter.print(" ");
                        }
                    }
                    outputWriter.printLine();
                }
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean[] computeDistances(int[] marks, int highestMark) {
        boolean[] distances = new boolean[highestMark + 1];

        for (int i = 0; i < marks.length; i++) {
            for (int j = 0; j < marks.length; j++) {
                if (i == j) {
                    continue;
                }

                int distance = marks[i] - marks[j];
                if (distance <= 0) {
                    continue;
                }
                if (distances[distance]) {
                    return null;
                }
                distances[distance] = true;
            }
        }
        return distances;
    }

    private static List<Integer> getMissingDistances(boolean[] distances) {
        List<Integer> missingDistances = new ArrayList<>();

        for (int distance = 1; distance < distances.length; distance++) {
            if (!distances[distance]) {
                missingDistances.add(distance);
            }
        }
        return missingDistances;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
