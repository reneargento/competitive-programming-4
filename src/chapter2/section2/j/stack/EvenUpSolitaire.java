package chapter2.section2.j.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/03/21.
 */
public class EvenUpSolitaire {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int numberOfCards = FastReader.nextInt();
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < numberOfCards; i++) {
            int card = FastReader.nextInt();

            if (!stack.isEmpty() && (stack.peek() + card) % 2 == 0) {
                stack.pop();
            } else {
                stack.push(card);
            }
        }
        System.out.println(stack.size());
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
}
