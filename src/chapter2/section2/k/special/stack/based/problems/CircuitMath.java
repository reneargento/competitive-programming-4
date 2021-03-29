package chapter2.section2.k.special.stack.based.problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/03/21.
 */
public class CircuitMath {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int variables = FastReader.nextInt();
        boolean[] values = new boolean[variables];

        for (int i = 0; i < values.length; i++) {
            if (FastReader.next().equals("T")) {
                values[i] = true;
            }
        }

        Deque<Boolean> variableStack = new ArrayDeque<>();
        String[] circuit = FastReader.getLine().split(" ");

        for (String inputValue : circuit) {
            char input = inputValue.charAt(0);

            if (Character.isLetter(input)) {
                int index = input - 'A';
                variableStack.push(values[index]);
            } else {
                boolean value1 = variableStack.pop();

                if (input == '-') {
                    variableStack.push(!value1);
                    continue;
                }

                boolean value2 = variableStack.pop();
                if (input == '*') {
                    variableStack.push(value1 && value2);
                } else {
                    variableStack.push(value1 || value2);
                }
            }
        }
        boolean output = variableStack.pop();
        System.out.println(output ? "T" : "F");
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
}
