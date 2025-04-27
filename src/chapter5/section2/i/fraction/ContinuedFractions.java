package chapter5.section2.i.fraction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 23/04/25.
 */
public class ContinuedFractions {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int number1 = Integer.parseInt(data[0]);
            int number2 = Integer.parseInt(data[1]);

            List<Long> continuedFraction = gcdForContinuedFraction(number1, number2);
            outputWriter.print("[" + continuedFraction.get(0) + ";");
            for (int i = 1; i < continuedFraction.size(); i++) {
                outputWriter.print(continuedFraction.get(i));
                if (i != continuedFraction.size() - 1) {
                    outputWriter.print(",");
                } else {
                    outputWriter.print("]");
                }
            }
            outputWriter.printLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<Long> gcdForContinuedFraction(long number1, long number2) {
        List<Long> continuedFraction = new ArrayList<>();

        while (number2 > 0) {
            long coefficient = number1 / number2;
            continuedFraction.add(coefficient);

            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return continuedFraction;
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
