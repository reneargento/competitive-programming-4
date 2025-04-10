package chapter5.section2.g.grid;

import java.io.*;

/**
 * Created by Rene Argento on 08/04/25.
 */
public class DermubaTriangle {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int startHouse = Integer.parseInt(data[0]);
            int endHouse = Integer.parseInt(data[1]);

            double shortestDistance = computeShortestDistance(startHouse, endHouse);
            outputWriter.printLine(String.format("%.3f", shortestDistance));
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static double computeShortestDistance(long startHouse, long endHouse) {
        double sqrt3DividedBy2 = Math.sqrt(3) / 2.0;
        startHouse++;
        endHouse++;

        double sqrtStartHouse = Math.ceil(Math.sqrt(startHouse));
        double sqrtEndHouse = Math.ceil(Math.sqrt(endHouse));

        double startHouseMinusSquare = startHouse - (sqrtStartHouse - 1) * (sqrtStartHouse - 1);
        double endHouseMinusSquare = endHouse - (sqrtEndHouse - 1) * (sqrtEndHouse - 1);

        double startHouseY;
        if (startHouseMinusSquare % 2 == 0) {
            startHouseY = 1;
        } else {
            startHouseY = 2;
        }
        startHouseY = (startHouseY / 3.0 + (sqrtStartHouse - 1)) * sqrt3DividedBy2;

        double endHouseY;
        if (endHouseMinusSquare % 2 == 0) {
            endHouseY = 1;
        } else {
            endHouseY = 2;
        }
        endHouseY = (endHouseY / 3.0 + (sqrtEndHouse - 1)) * sqrt3DividedBy2;

        double startHouseX = (startHouseMinusSquare - sqrtStartHouse) / 2;
        double endHouseX = (endHouseMinusSquare - sqrtEndHouse) / 2;

        double distanceX = startHouseX - endHouseX;
        double distanceY = startHouseY - endHouseY;
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
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
