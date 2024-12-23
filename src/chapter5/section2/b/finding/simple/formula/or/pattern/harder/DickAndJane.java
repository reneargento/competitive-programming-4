package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;

/**
 * Created by Rene Argento on 20/12/24.
 */
public class DickAndJane {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            long spotYearsWhenPuffBorn = Long.parseLong(data[0]);
            long puffYearsWhenYertleBorn = Long.parseLong(data[1]);
            long spotYearsWhenYertleBorn = Long.parseLong(data[2]);
            long janesAge = Long.parseLong(data[3]);

            double yertlesAgeDouble = (12 + janesAge - puffYearsWhenYertleBorn - spotYearsWhenYertleBorn) / 3.0;
            long yertlesAge = (12 + janesAge - puffYearsWhenYertleBorn - spotYearsWhenYertleBorn) / 3;
            long spotsAge = yertlesAge + spotYearsWhenYertleBorn;
            long puffsAge = yertlesAge + puffYearsWhenYertleBorn;

            if (yertlesAgeDouble != yertlesAge) {
                if (yertlesAgeDouble - yertlesAge < 0.66) {
                    if (spotYearsWhenYertleBorn == spotYearsWhenPuffBorn + puffYearsWhenYertleBorn) {
                        spotsAge++;
                    } else {
                        puffsAge++;
                    }
                } else {
                    spotsAge++;
                    puffsAge++;
                }
            }
            outputWriter.printLine(spotsAge + " " + puffsAge + " " + yertlesAge);
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
