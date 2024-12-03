package chapter5.section2.a.finding.simple.formula.or.pattern.easier;

import java.io.*;

/**
 * Created by Rene Argento on 29/11/24.
 */
public class TheHotelWithInfiniteRooms {

    private static final double EPSILON = .0000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            long initialGroupSize = Integer.parseInt(data[0]);
            long days = Long.parseLong(data[1]);

            long groupSizeSum = (initialGroupSize * (initialGroupSize - 1)) / 2;
            long c = -2L * (days + groupSizeSum - 1L);
            double targetGroupSize = (1.0 + Math.sqrt(1 - 4.0 * c)) / 2.0;
            int targetGroupSizeFloor = (int) (Math.floor(targetGroupSize) + EPSILON);
            outputWriter.printLine(targetGroupSizeFloor);

            line = FastReader.getLine();
        }
        outputWriter.flush();
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
