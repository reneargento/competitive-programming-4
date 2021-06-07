package chapter2.section3.g.balanced.bst.set;

import java.io.*;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * Created by Rene Argento on 07/06/21.
 */
public class PalindromicPassword {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int numbers = FastReader.nextInt();
        TreeSet<Integer> palindromes = generatePalindromes();

        for (int n = 0; n < numbers; n++) {
            int number = FastReader.nextInt();
            Integer floor = palindromes.floor(number);
            Integer ceiling = palindromes.ceiling(number);

            if (floor != null && ceiling != null) {
                int floorDifference = Math.abs(floor - number);
                int ceilingDifference = Math.abs(ceiling - number);
                if (floorDifference <= ceilingDifference) {
                    outputWriter.printLine(floor);
                } else {
                    outputWriter.printLine(ceiling);
                }
            } else if (floor != null) {
                outputWriter.printLine(floor);
            } else {
                outputWriter.printLine(ceiling);
            }
        }
        outputWriter.flush();
    }

    private static TreeSet<Integer> generatePalindromes() {
        TreeSet<Integer> palindromes = new TreeSet<>();
        for (int number = 100; number <= 999; number++) {
            StringBuilder palindrome = new StringBuilder(String.valueOf(number));

            for (int i = 2; i >= 0; i--) {
                palindrome.append(palindrome.charAt(i));
            }
            palindromes.add(Integer.parseInt(palindrome.toString()));
        }
        return palindromes;
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
