package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/12/23.
 */
public class IgnoringDigits {

    private static class State {
        int modNumber;
        String completeNumber;

        public State(int modNumber, String completeNumber) {
            this.modNumber = modNumber;
            this.completeNumber = completeNumber;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String digitsAvailable = FastReader.next();
        int number = FastReader.nextInt();

        while (!digitsAvailable.equals("0") || number != 0) {
            String smallestMultiple = computeSmallestMultiple(digitsAvailable, number);
            if (!smallestMultiple.equals("-1")) {
                outputWriter.printLine(smallestMultiple);
            } else {
                outputWriter.printLine("impossible");
            }

            digitsAvailable = FastReader.next();
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static String computeSmallestMultiple(String digitsAvailable, int number) {
        int[] digits = getDigits(digitsAvailable);
        boolean[] visited = new boolean[100001];

        Queue<State> queue = new LinkedList<>();
        for (int digit : digits) {
            if (digit != 0) {
                queue.offer(new State(digit, String.valueOf(digit)));

                if (digit % number == 0) {
                    return String.valueOf(digit);
                }
                visited[digit] = true;
            }
        }

        while (!queue.isEmpty()) {
            State state = queue.poll();
            int currentNumber = state.modNumber;

            for (int digit : digits) {
                int numberWithDigit = currentNumber * 10 + digit;
                int newModNumber = numberWithDigit % number;
                if (!visited[newModNumber]) {
                    String newCompleteNumber = state.completeNumber + digit;
                    if (newModNumber == 0) {
                        return newCompleteNumber;
                    }

                    queue.offer(new State(newModNumber, newCompleteNumber));
                    visited[newModNumber] = true;
                }
            }
        }
        return "-1";
    }

    private static int[] getDigits(String digitsAvailable) {
        int[] digits = new int[digitsAvailable.length()];

        for (int i = digitsAvailable.length() - 1; i >= 0; i--) {
            int digitIndex = digitsAvailable.length() - 1 - i;
            digits[digitIndex] = Character.getNumericValue(digitsAvailable.charAt(i));
        }
        return digits;
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

        public void flush() {
            writer.flush();
        }
    }
}
