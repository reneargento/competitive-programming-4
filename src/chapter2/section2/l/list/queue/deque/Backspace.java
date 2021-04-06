package chapter2.section2.l.list.queue.deque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/04/21.
 */
public class Backspace {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        String originalText = FastReader.next();
        String intendedString = getIntendedString(originalText);
        System.out.println(intendedString);
    }

    private static String getIntendedString(String originalText) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char character : originalText.toCharArray()) {
            if (character != '<') {
                stack.push(character);
            } else {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            }
        }

        StringBuilder intendedString = new StringBuilder();
        while (!stack.isEmpty()) {
            intendedString.append(stack.pop());
        }
        return intendedString.reverse().toString();
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
    }
}
