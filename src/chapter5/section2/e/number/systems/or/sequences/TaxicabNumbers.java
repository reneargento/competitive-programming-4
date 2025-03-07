package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 27/02/25.
 */g
public class TaxicabNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        List<Integer> cabNumbers = computeCabNumbers();

        while (line != null) {
            int lowerLimit = Integer.parseInt(line);
            int maxLimit = lowerLimit + FastReader.nextInt();
            boolean cabNumberExists = false;

            for (int cabNumber : cabNumbers) {
                if (lowerLimit <= cabNumber && cabNumber <= maxLimit) {
                    outputWriter.printLine(cabNumber);
                    cabNumberExists = true;
                }

                if (cabNumber > maxLimit) {
                    break;
                }
            }

            if (!cabNumberExists) {
                outputWriter.printLine("None");
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<Integer> computeCabNumbers() {
        Set<Integer> cabNumbersSet = new HashSet<>();
        int[] cubes = computeCubes();
        Set<Integer> cubeSums = new HashSet<>();

        for (int i = 0; i < cubes.length; i++) {
            for (int j = i; j < cubes.length; j++) {
                int sum = cubes[i] + cubes[j];

                if (cubeSums.contains(sum)) {
                    cabNumbersSet.add(sum);
                }
                cubeSums.add(sum);
            }
        }

        List<Integer> cabNumbers = new ArrayList<>(cabNumbersSet);
        Collections.sort(cabNumbers);
        return cabNumbers;
    }

    private static int[] computeCubes() {
        List<Integer> cubes = new ArrayList<>();
        int maxSum = (int) Math.ceil(Math.cbrt(1000100000));

        for (int number = 0; number <= maxSum; number++) {
            cubes.add(number * number * number);
        }

        int[] cubesArray = new int[cubes.size()];
        for (int i = 0; i < cubesArray.length; i++) {
            cubesArray[i] = cubes.get(i);
        }
        return cubesArray;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
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
