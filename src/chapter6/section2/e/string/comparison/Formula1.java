package chapter6.section2.e.string.comparison;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Rene Argento on 28/06/2026.
 */
public class Formula1 {

    private static class Pilot implements Comparable<Pilot> {
        String name;
        int minutes;
        int seconds;
        int milliseconds;

        public Pilot(String name, int minutes, int seconds, int milliseconds) {
            this.name = name;
            this.minutes = minutes;
            this.seconds = seconds;
            this.milliseconds = milliseconds;
        }

        @Override
        public int compareTo(Pilot other) {
            if (this.minutes != other.minutes) {
                return Integer.compare(this.minutes, other.minutes);
            }
            if (this.seconds != other.seconds) {
                return Integer.compare(this.seconds, other.seconds);
            }
            if (this.milliseconds != other.milliseconds) {
                return Integer.compare(this.milliseconds, other.milliseconds);
            }
            return name.toUpperCase().compareTo(other.name.toUpperCase());
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            Pilot[] pilots = new Pilot[Integer.parseInt(line)];

            for (int i = 0; i < pilots.length; i++) {
                line = FastReader.getLine();
                String[] data = line.split(" ");
                String name = data[0];
                int minutes = Integer.parseInt(data[2]);
                int seconds = Integer.parseInt(data[4]);
                int milliseconds = Integer.parseInt(data[6]);
                pilots[i] = new Pilot(name, minutes, seconds, milliseconds);
            }
            printRows(pilots, outputWriter);

            FastReader.getLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void printRows(Pilot[] pilots, OutputWriter outputWriter) {
        Arrays.sort(pilots);
        int row = 1;

        for (int i = 0; i < pilots.length; i += 2) {
            outputWriter.printLine("Row " + row);
            outputWriter.printLine(pilots[i].name);
            if (i < pilots.length - 1) {
                outputWriter.printLine(pilots[i + 1].name);
            }
            row++;
        }
        outputWriter.printLine();
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