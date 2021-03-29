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
public class DelimiterSoup {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int charactersLength = FastReader.nextInt();
        String characters = FastReader.getLine();

        Deque<Character> stack = new ArrayDeque<>();
        int errorPosition = -1;

        for (int i = 0; i < characters.length(); i++) {
            char value = characters.charAt(i);

            if (value == ')') {
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();
                } else {
                    errorPosition = i;
                    break;
                }
            } else if (value == ']') {
                if (!stack.isEmpty() && stack.peek() == '[') {
                    stack.pop();
                } else {
                    errorPosition = i;
                    break;
                }
            } else if (value == '}') {
                if (!stack.isEmpty() && stack.peek() == '{') {
                    stack.pop();
                } else {
                    errorPosition = i;
                    break;
                }
            } else if (value != ' ') {
                stack.push(value);
            }
        }

        if (errorPosition == -1) {
            System.out.println("ok so far");
        } else {
            System.out.printf("%s %d\n", characters.charAt(errorPosition), errorPosition);
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
