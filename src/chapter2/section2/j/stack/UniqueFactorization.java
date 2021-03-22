package chapter2.section2.j.stack;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/03/21.
 */
public class UniqueFactorization {

    private static class Factorization implements Comparable<Factorization> {
        List<Integer> factors;

        public Factorization(Deque<Integer> copyFactors) {
            factors = new ArrayList<>(copyFactors);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            Factorization otherFactorization = (Factorization) other;
            if (factors.size() != otherFactorization.factors.size()) {
                return false;
            }
            for (int i = 0; i < factors.size(); i++) {
                if (!factors.get(i).equals(otherFactorization.factors.get(i))) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            return Objects.hash(factors);
        }

        @Override
        public int compareTo(Factorization other) {
            int minSize = Math.min(factors.size(), other.factors.size());
            for (int i = 0; i < minSize; i++) {
                if (factors.get(i) < other.factors.get(i)) {
                    return -1;
                } else if (factors.get(i) > other.factors.get(i)) {
                    return 1;
                }
            }
            return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int number = FastReader.nextInt();

        while (number != 0) {
            List<Factorization> factorizations = getAllFactorizations(number);
            printFactorizations(factorizations, outputWriter);

            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Factorization> getAllFactorizations(int number) {
        Set<Factorization> factorizations = new HashSet<>();
        getAllFactorizations(number, number, new ArrayDeque<>(), factorizations, 2);

        List<Factorization> factorizationList = new ArrayList<>(factorizations);
        Collections.sort(factorizationList);
        return factorizationList;
    }

    private static void getAllFactorizations(int originalNumber, int number, Deque<Integer> currentFactorization,
                                             Set<Factorization> factorizations, int lastDivisor) {
        if (number == 1) {
            if (!currentFactorization.isEmpty()) {
                Factorization factorization = new Factorization(currentFactorization);
                Collections.sort(factorization.factors);
                factorizations.add(factorization);
            }
            return;
        }

        for (int i = lastDivisor; i <= number; i++) {
            if (i == originalNumber) {
                break;
            }
            if (number % i == 0) {
                currentFactorization.push(i);
                getAllFactorizations(originalNumber, number / i, currentFactorization, factorizations, i);
                currentFactorization.pop();
            }
        }
    }

    private static void printFactorizations(List<Factorization> factorizations, OutputWriter outputWriter) {
        outputWriter.printLine(factorizations.size());
        for (Factorization factorization : factorizations) {
            for (int i = 0; i < factorization.factors.size(); i++) {
                outputWriter.print(factorization.factors.get(i));

                if (i != factorization.factors.size() - 1) {
                    outputWriter.print(" ");
                }
            }
            outputWriter.printLine();
        }
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
