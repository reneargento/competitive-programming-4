package chapter2.section2.e.sorting.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/02/21.
 */
public class LunchInGridCity {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int streets = FastReader.nextInt();
            int avenues = FastReader.nextInt();
            int friends = FastReader.nextInt();
            int[] streetLocations = new int[friends];
            int[] avenueLocations = new int[friends];

            for (int i = 0; i < friends; i++) {
                streetLocations[i] = FastReader.nextInt();
                avenueLocations[i] = FastReader.nextInt();
            }

            Arrays.sort(streetLocations);
            Arrays.sort(avenueLocations);

            int meetingIndex;
            if (friends % 2 == 0) {
                meetingIndex = (friends / 2) - 1;
            } else {
                meetingIndex = friends / 2;
            }

            int meetingStreet = streetLocations[meetingIndex];
            int meetingAvenue = avenueLocations[meetingIndex];
            System.out.printf("(Street: %d, Avenue: %d)\n", meetingStreet, meetingAvenue);
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
