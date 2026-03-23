package chapter5.section6;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Rene Argento on 20/03/26.
 */
public class EventuallyPeriodicSequence {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            if (data[0].equals("0")) {
                break;
            }

            long period = computePeriod(data);
            outputWriter.printLine(period);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long computePeriod(String[] data) {
        long N = Long.parseLong(data[0]);
        long n = Long.parseLong(data[1]);
        return computeCycleLength(n, N, data);
    }

    private static int computeCycleLength(long number0, long N, String[] data) {
        long tortoise = evaluateExpression(number0, N, data);
        long hare = evaluateExpression(evaluateExpression(number0, N, data), N, data);

        while (tortoise != hare) {
            tortoise = evaluateExpression(tortoise, N, data);
            hare = evaluateExpression(evaluateExpression(hare, N, data), N, data);
        }

        hare = number0;
        while (tortoise != hare) {
            tortoise = evaluateExpression(tortoise, N, data);
            hare = evaluateExpression(hare, N, data);
        }

        int lambda = 1;
        hare = evaluateExpression(tortoise, N, data);
        while (tortoise != hare) {
            hare = evaluateExpression(hare, N, data);
            lambda++;
        }
        return lambda;
    }

    private static long evaluateExpression(long n, long N, String[] data) {
        Deque<Long> deque = new ArrayDeque<>();

        for (int i = 2; i < data.length; i++) {
            String symbol = data[i];

            if (isOperator(symbol)) {
                long operator2 = deque.pop();
                long operator1 = deque.pop();
                long result;

                if (symbol.equals("*")) {
                    result = (operator1 * operator2) % N;
                } else if (symbol.equals("+")) {
                    result = operator1 + operator2;
                } else {
                    result = operator1 % operator2;
                }
                deque.push(result);
            } else {
                long value;
                if (symbol.equals("N")) {
                    value = N;
                } else if (symbol.equals("x")) {
                    value = n;
                } else {
                    value = Long.parseLong(symbol);
                }
                deque.push(value);
            }
        }
        return deque.pop();
    }

    private static boolean isOperator(String symbol) {
        return symbol.equals("*") || symbol.equals("+") || symbol.equals("%");
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
