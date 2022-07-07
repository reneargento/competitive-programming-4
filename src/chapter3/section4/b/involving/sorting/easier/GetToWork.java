package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/07/22.
 */
public class GetToWork {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int towns = FastReader.nextInt();
            int officeTown = FastReader.nextInt() - 1;
            List<Integer>[] townsPerEmployees = new List[towns];
            for (int i = 0; i < townsPerEmployees.length; i++) {
                townsPerEmployees[i] = new ArrayList<>();
            }
            int employees = FastReader.nextInt();
            for (int i = 0; i < employees; i++) {
                int homeTown =  FastReader.nextInt() - 1;
                int capacity = FastReader.nextInt();
                townsPerEmployees[homeTown].add(capacity);
            }

            int[] carsNeeded = computeCarsNeeded(townsPerEmployees, officeTown);
            outputWriter.print(String.format("Case #%d:", t));
            if (carsNeeded == null) {
                outputWriter.printLine(" IMPOSSIBLE");
            } else {
                for (int cars : carsNeeded) {
                    outputWriter.print(" " + cars);
                }
                outputWriter.printLine();
            }
        }
        outputWriter.flush();
    }

    private static int[] computeCarsNeeded(List<Integer>[] townsPerEmployees, int officeTown) {
        int[] carsNeeded = new int[townsPerEmployees.length];
        for (List<Integer> townsPerEmployee : townsPerEmployees) {
            townsPerEmployee.sort(Collections.reverseOrder());
        }

        for (int i = 0; i < carsNeeded.length; i++) {
            if (i == officeTown) {
                carsNeeded[i] = 0;
            } else {
                int cars = 0;
                int totalEmployees = townsPerEmployees[i].size();
                int employeesAssigned = 0;

                for (int capacity : townsPerEmployees[i]) {
                    cars++;
                    employeesAssigned += capacity;

                    if (employeesAssigned >= totalEmployees) {
                        break;
                    }
                }

                if (employeesAssigned < totalEmployees) {
                    return null;
                }
                carsNeeded[i] = cars;
            }
        }
        return carsNeeded;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
