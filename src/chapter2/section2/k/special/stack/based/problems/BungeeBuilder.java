package chapter2.section2.k.special.stack.based.problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/03/21.
 */
public class BungeeBuilder {

    private static class Mountain {
        int height;
        int location;

        public Mountain(int height, int location) {
            this.height = height;
            this.location = location;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int mountains = FastReader.nextInt();

        Mountain topMountain = new Mountain(FastReader.nextInt(), 0);
        int maxJumpDistance = 0;
        int minimumCurrentHeight = Integer.MAX_VALUE;

        for (int i = 1; i < mountains; i++) {
            Mountain mountain = new Mountain(FastReader.nextInt(), i);

            if (mountain.height <= topMountain.height) {
                minimumCurrentHeight = Math.min(minimumCurrentHeight, mountain.height);
                maxJumpDistance = Math.max(maxJumpDistance, mountain.height - minimumCurrentHeight);

                if (mountain.height == topMountain.height) {
                    topMountain = mountain;
                    minimumCurrentHeight = Integer.MAX_VALUE;
                }
            } else {
                if (i > topMountain.location + 1) {
                    maxJumpDistance = Math.max(maxJumpDistance, topMountain.height - minimumCurrentHeight);
                }
                topMountain = mountain;
                minimumCurrentHeight = Integer.MAX_VALUE;
            }
        }
        System.out.println(maxJumpDistance);
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
