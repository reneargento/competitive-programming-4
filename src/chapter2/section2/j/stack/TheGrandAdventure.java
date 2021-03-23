package chapter2.section2.j.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/03/21.
 */
public class TheGrandAdventure {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int adventures = FastReader.nextInt();

        for (int a = 0; a < adventures; a++) {
            String adventure = FastReader.next();
            Deque<Character> itemStack = new ArrayDeque<>();
            boolean failedAdventure = false;

            for (int i = 0; i < adventure.length(); i++) {
                char character = adventure.charAt(i);
                if (character == '$' || character == '|' || character == '*') {
                    itemStack.push(character);
                } else if (character == 't') {
                    if (!canContinueAdventure(itemStack, '|')) {
                        failedAdventure = true;
                        break;
                    }
                } else if (character == 'j') {
                    if (!canContinueAdventure(itemStack, '*')) {
                        failedAdventure = true;
                        break;
                    }
                } else if (character == 'b') {
                    if (!canContinueAdventure(itemStack, '$')) {
                        failedAdventure = true;
                        break;
                    }
                }
            }

            if (!itemStack.isEmpty()) {
                failedAdventure = true;
            }

            System.out.println(failedAdventure ? "NO" : "YES");
        }
    }

    private static boolean canContinueAdventure(Deque<Character> itemStack, char expectedItem) {
        if (!itemStack.isEmpty() && itemStack.peek() == expectedItem) {
            itemStack.pop();
            return true;
        }
        return false;
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
