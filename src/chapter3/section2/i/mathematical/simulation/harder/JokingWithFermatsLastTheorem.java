package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 15/01/22.
 */
public class JokingWithFermatsLastTheorem {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        List<Integer> cubicRoots = computeCubicRoots();

        int caseNumber = 1;
        while (line != null) {
            String[] values = line.split(" ");
            int rangeStart = Integer.parseInt(values[0]);
            int rangeEnd = Integer.parseInt(values[1]);

            int solutions = computeNumberOfSolutions(rangeStart, rangeEnd, cubicRoots);
            outputWriter.printLine(String.format("Case %d: %d", caseNumber, solutions));

            caseNumber++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeNumberOfSolutions(int rangeStart, int rangeEnd, List<Integer> cubicRoots) {
        int solutions = 0;

        for (int indexA = rangeStart; indexA < cubicRoots.size() && indexA <= rangeEnd; indexA++) {
            for (int indexB = indexA + 1; indexB < cubicRoots.size() && indexB <= rangeEnd; indexB++) {
                long result = cubicRoots.get(indexA) + cubicRoots.get(indexB);
                if (result / 10 > rangeEnd) {
                    break;
                }

                if (result % 10 == 3) {
                    solutions++;
                }
            }
        }
        return solutions * 2;
    }

    private static List<Integer> computeCubicRoots() {
        List<Integer> cubicRoots = new ArrayList<>();
        cubicRoots.add(0);

        for (int i = 1; i * i * i <= 1000000000; i++) {
            int cubicRoot = i * i * i;
            cubicRoots.add(cubicRoot);
        }
        return cubicRoots;
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
