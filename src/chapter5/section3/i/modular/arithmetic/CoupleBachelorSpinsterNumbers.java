package chapter5.section3.i.modular.arithmetic;

import java.io.*;

/**
 * Created by Rene Argento on 06/10/25.
 */
public class CoupleBachelorSpinsterNumbers {

    private static class Result {
        int number1;
        int number2;

        public Result(int number1, int number2) {
            this.number1 = number1;
            this.number2 = number2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int number1 = Integer.parseInt(data[0]);

            if (data.length > 1) {
                int number2 = Integer.parseInt(data[1]);
                int bachelorNumbersInRange = computeBachelorNumbersInRange(number1, number2);
                outputWriter.printLine(bachelorNumbersInRange);
            } else {
                Result squareCouple = computeSquareCouple(number1);
                if (squareCouple == null) {
                    outputWriter.printLine("Bachelor Number.");
                } else {
                    outputWriter.printLine(squareCouple.number1 + " " + squareCouple.number2);
                }
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeBachelorNumbersInRange(int start, int end) {
        int bachelorNumbersInRange = 0;
        if (start % 2 == 1) {
            start++;
        }
        for (int number = start; number <= end; number += 2) {
            if (number % 4 > 0) {
                bachelorNumbersInRange++;
            }
        }
        return bachelorNumbersInRange;
    }

    private static Result computeSquareCouple(int number) {
        if (number == 0) {
            return new Result(0, 0);
        }

        int number1;
        int number2;
        boolean isNegative = false;
        if (number < 0) {
            isNegative = true;
            number = -number;
        }

        if (number % 2 == 0) {
            if (number % 4 == 0) {
                number1 = number / 4 + 1;
                number2 = number / 4 - 1;
            } else {
                return null;
            }
        } else {
            number1 = (number + 1) / 2;
            number2 = (number - 1) / 2;
        }

        if (isNegative) {
            int temp = number1;
            number1 = number2;
            number2 = temp;
        }
        return new Result(number1, number2);
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
