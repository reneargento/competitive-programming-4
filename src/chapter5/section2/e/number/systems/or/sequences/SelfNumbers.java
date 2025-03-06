package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;

/**
 * Created by Rene Argento on 22/02/25.
 */
public class SelfNumbers {

    public static void main(String[] args) throws IOException {
        OutputWriter outputWriter = new OutputWriter(System.out);

        boolean[] isGenerated = computeGeneratedNumbers();

        for (int selfNumberCandidate = 1; selfNumberCandidate <= 1000000; selfNumberCandidate++) {
            if (!isGenerated[selfNumberCandidate]) {
                outputWriter.printLine(selfNumberCandidate);
            }
        }
        outputWriter.flush();
    }

    private static boolean[] computeGeneratedNumbers() {
        int maxNumber = 1000000;
        boolean[] isGenerated = new boolean[maxNumber + 1];

        for (int number = 1; number < isGenerated.length; number++) {
            int generatedNumber = generateNextNumber(number);
            while (generatedNumber <= maxNumber && !isGenerated[generatedNumber]) {
                isGenerated[generatedNumber] = true;
                generatedNumber = generateNextNumber(number);
            }
        }
        return isGenerated;
    }

    private static int generateNextNumber(int number) {
        int generatedNumber = number;

        while (number > 0) {
            int digit = number % 10;
            generatedNumber += digit;
            number /= 10;
        }
        return generatedNumber;
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
