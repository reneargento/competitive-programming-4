package chapter2.section2.k.special.stack.based.problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/03/21.
 */
public class ParenthesesBalance {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int strings = FastReader.nextInt();

        for (int i = 0; i < strings; i++) {
            String string = FastReader.getLine();
            Deque<Character> stack = new ArrayDeque<>();
            boolean isCorrect = true;

            for (char character : string.toCharArray()) {
                if (character == '(' || character == '[') {
                    stack.push(character);
                } else if (character == ')') {
                    if (!stack.isEmpty() && stack.peek() == '(') {
                        stack.pop();
                    } else {
                        isCorrect = false;
                        break;
                    }
                } else {
                    if (!stack.isEmpty() && stack.peek() == '[') {
                        stack.pop();
                    } else {
                        isCorrect = false;
                        break;
                    }
                }
            }

            if (!stack.isEmpty()) {
                isCorrect = false;
            }
            System.out.println(isCorrect ? "Yes" : "No");
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

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
