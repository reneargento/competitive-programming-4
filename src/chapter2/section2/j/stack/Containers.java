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
@SuppressWarnings("unchecked")
public class Containers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        String containers = FastReader.next();
        int caseNumber = 1;

        while (!containers.equals("end")) {
            Deque<Character>[] stacks = createStacks();
            int stacksUsed = 0;

            for (char containerId : containers.toCharArray()) {
                boolean placedContainer = false;
                int possibleStack = -1;

                for (int i = 0; i < stacksUsed; i++) {
                    if (stacks[i].peek() >= containerId) {
                        if (stacks[i].peek() == containerId) {
                            placedContainer = true;
                            break;
                        } else if (possibleStack == -1) {
                            possibleStack = i;
                        }
                    }
                }

                if (!placedContainer) {
                    if (possibleStack != -1) {
                        stacks[possibleStack].push(containerId);
                    } else {
                        stacks[stacksUsed].push(containerId);
                        stacksUsed++;
                    }
                }
            }
            System.out.printf("Case %d: %d\n", caseNumber, stacksUsed);

            caseNumber++;
            containers = FastReader.next();
        }
    }

    private static Deque<Character>[] createStacks() {
        Deque<Character>[] stacks = new Deque[26];
        for (int i = 0; i < stacks.length; i++) {
            stacks[i] = new ArrayDeque<>();
        }
        return stacks;
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
