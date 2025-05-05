package chapter5.section2.j.really.ad.hoc;

import java.io.*;

/**
 * Created by Rene Argento on 02/05/25.
 */
public class Yoda {

    private static class Result {
        String number1;
        String number2;

        public Result(String number1, String number2) {
            this.number1 = number1;
            this.number2 = number2;
        }
    }

    private static final String YODA = "YODA";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        
        String number1 = FastReader.getLine();
        String number2 = FastReader.getLine();

        Result result = collideIntegers(number1, number2);
        outputWriter.printLine(result.number1);
        outputWriter.printLine(result.number2);
        outputWriter.flush();
    }
    
    private static Result collideIntegers(String number1, String number2) {
        StringBuilder newNumber1 = new StringBuilder();
        StringBuilder newNumber2 = new StringBuilder();

        if (number1.length() > number2.length()) {
            int difference = number1.length() - number2.length();
            number2 = appendZeroes(number2, difference);
        } else if (number2.length() > number1.length()) {
            int difference = number2.length() - number1.length();
            number1 = appendZeroes(number1, difference);
        }

        for (int i = 0; i < number1.length(); i++) {
            int number1Digit = Character.getNumericValue(number1.charAt(i));
            int number2Digit = Character.getNumericValue(number2.charAt(i));

            if (number1Digit > number2Digit) {
                newNumber1.append(number1Digit);
            } else if (number2Digit > number1Digit) {
                newNumber2.append(number2Digit);
            } else {
                newNumber1.append(number1Digit);
                newNumber2.append(number2Digit);
            }
        }

        if (newNumber1.length() == 0) {
            newNumber1.append(YODA);
        } else {
            int value = Integer.parseInt(newNumber1.toString());
            newNumber1 = new StringBuilder(String.valueOf(value));
        }
        if (newNumber2.length() == 0) {
            newNumber2.append(YODA);
        } else {
            int value = Integer.parseInt(newNumber2.toString());
            newNumber2 = new StringBuilder(String.valueOf(value));
        }
        return new Result(newNumber1.toString(), newNumber2.toString());
    }

    private static String appendZeroes(String number, int zeroesNumber) {
        StringBuilder zeroes = new StringBuilder();

        for (int i = 0; i < zeroesNumber; i++) {
            zeroes.append("0");
        }
        return zeroes + number;
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
