package chapter2.section2.l.list.queue.deque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/04/21.
 */
public class Server {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int tasks = FastReader.nextInt();
        int timeAvailable = FastReader.nextInt();
        int timeSpent = 0;
        int tasksCompleted = 0;

        for (int i = 0; i < tasks; i++) {
            int taskTime = FastReader.nextInt();

            if (timeSpent + taskTime <= timeAvailable) {
                timeSpent += taskTime;
                tasksCompleted++;
            } else {
                break;
            }
        }
        System.out.println(tasksCompleted);
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
