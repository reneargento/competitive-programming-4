package chapter2.section2.j.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/03/21.
 */
public class PairingSocks {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int pairOfSocks = FastReader.nextInt();
        Deque<Integer> socksStack = new ArrayDeque<>();
        int moves = 0;

        for (int i = 0; i < 2 * pairOfSocks; i++) {
            int sockType = FastReader.nextInt();

            if (!socksStack.isEmpty() && socksStack.peek() == sockType) {
                socksStack.pop();
                moves += 2;
            } else {
                socksStack.push(sockType);
            }
        }

        if (socksStack.isEmpty()) {
            System.out.println(moves);
        } else {
            System.out.println("impossible");
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
}
