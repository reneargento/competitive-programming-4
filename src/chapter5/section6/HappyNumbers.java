package chapter5.section6;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 25/03/26.
 */
public class HappyNumbers {

    private static class HappyNumber {
        int value;
        int iterations;

        public HappyNumber(int value, int iterations) {
            this.value = value;
            this.iterations = iterations;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        int[] happyNumberIterations = new int[100000];
        Arrays.fill(happyNumberIterations, -1);

        int caseId = 1;
        while (line != null) {
            String[] data = line.split(" ");
            int low = Integer.parseInt(data[0]);
            int high = Integer.parseInt(data[1]);

            List<HappyNumber> happyNumbers = computeHappyNumbers(low, high, happyNumberIterations);
            if (caseId > 1) {
                outputWriter.printLine();
            }
            for (HappyNumber happyNumber : happyNumbers) {
                outputWriter.printLine(happyNumber.value + " " + happyNumber.iterations);
            }
            caseId++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<HappyNumber> computeHappyNumbers(int low, int high, int[] happyNumberIterations) {
        List<HappyNumber> happyNumbers = new ArrayList<>();
        for (int number = low; number <= high; number++) {
            int iterations = isHappyNumber(number, happyNumberIterations);
            if (iterations != -1) {
                happyNumbers.add(new HappyNumber(number, iterations));
            }
        }
        return happyNumbers;
    }

    private static int isHappyNumber(int number, int[] happyNumberIterations) {
        int originalNumber = number;
        if (happyNumberIterations[number] != -1) {
            return happyNumberIterations[number];
        }

        int iterations = 1;
        Set<Integer> numbersSet = new HashSet<>();
        while (!numbersSet.contains(number)) {
            numbersSet.add(number);

            if (number == 1) {
                happyNumberIterations[originalNumber] = iterations;
                return iterations;
            }
            number = getNextNumber(number);
            iterations++;
        }
        return -1;
    }

    private static int getNextNumber(int number) {
        int nextNumber = 0;
        while (number > 0) {
            int digit = number % 10;
            nextNumber += digit * digit;
            number /= 10;
        }
        return nextNumber;
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
