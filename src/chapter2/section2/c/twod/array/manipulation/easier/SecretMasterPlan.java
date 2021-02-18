package chapter2.section2.c.twod.array.manipulation.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/02/21.
 */
public class SecretMasterPlan {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            if (t > 1) {
                FastReader.getLine();
            }
            String originalPlan = readPlan();
            String openedPlan = readPlan();

            boolean possible = (originalPlan + "-" + originalPlan).contains(openedPlan);
            System.out.printf("Case #%d: ", t);
            System.out.println(possible ? "POSSIBLE" : "IMPOSSIBLE");
        }
    }

    private static String readPlan() throws IOException {
        String[] line1 = FastReader.getLine().split(" ");
        String[] line2 = FastReader.getLine().split(" ");
        return line1[0] + '-' + line1[1] + '-' + line2[1] + '-' + line2[0];
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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
