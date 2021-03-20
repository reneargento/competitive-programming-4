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
public class Inception {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int queries = FastReader.nextInt();
        Deque<String> dreams = new ArrayDeque<>();

        for (int i = 0; i < queries; i++) {
            String[] queryData = FastReader.getLine().split(" ");

            switch (queryData[0]) {
                case "Sleep": dreams.push(queryData[1]); break;
                case "Kick":
                    if (!dreams.isEmpty()) {
                        dreams.pop();
                    }
                    break;
                default:
                    if (!dreams.isEmpty()) {
                        System.out.println(dreams.peek());
                    } else {
                        System.out.println("Not in a dream");
                    }
            }
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
