package chapter5.section5.a.probability.theory.easier;

import java.io.*;

/**
 * Created by Rene Argento on 17/01/26.
 */
public class CowsAndCars {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int cows = Integer.parseInt(data[0]);
            int cars = Integer.parseInt(data[1]);
            int show = Integer.parseInt(data[2]);

            double winningProbability = computeWinningProbability(cows, cars, show);
            outputWriter.printLine(String.format("%.5f", winningProbability));
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static double computeWinningProbability(int cows, int cars, int show) {
        double totalDoors = cows + cars;
        double doorsRemainingAfterShow = cows + cars - show - 1;
        double chooseCarThenSwitch = cars / totalDoors * (cars - 1) / doorsRemainingAfterShow;
        double chooseCowThenSwitch = cows / totalDoors * cars / doorsRemainingAfterShow;
        return chooseCarThenSwitch + chooseCowThenSwitch;
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
