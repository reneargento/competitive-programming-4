package chapter2.section2.j.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/03/21.
 */
public class GameOfThrowns {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int children = FastReader.nextInt();
        int throwCommands = FastReader.nextInt();
        Deque<Integer> positionsStack = new ArrayDeque<>();
        positionsStack.push(0);

        for (int i = 0; i < throwCommands; i++) {
            String command = FastReader.next();
            if (command.charAt(0) == '-' || Character.isDigit(command.charAt(0))) {
                int positions = Integer.parseInt(command);
                int nextPosition = wrapIndex(positionsStack.peek() + positions, children);
                positionsStack.push(nextPosition);
            } else {
                int commandsToUndo = FastReader.nextInt();
                for (int u = 0; u < commandsToUndo; u++) {
                    if (!positionsStack.isEmpty()) {
                        positionsStack.pop();
                    }
                }
            }
        }
        System.out.println(positionsStack.peek());
    }

    private static int wrapIndex(int index, int maxValue) {
        int modResult = index % maxValue;
        return modResult < 0 ? maxValue + modResult : modResult;
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
