package chapter2.section2.b.oned.array.manipulation.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/01/21.
 */
public class JustFinishItUp {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int gasStations = FastReader.nextInt();
            int[] available = new int[gasStations];
            int[] required = new int[gasStations];

            for (int i = 0; i < available.length; i++) {
                available[i] = FastReader.nextInt();
            }
            for (int i = 0; i < required.length; i++) {
                required[i] = FastReader.nextInt();
            }

            int startStation = getStartStation(available, required);
            System.out.printf("Case %d: ", t);
            if (startStation != -1) {
                System.out.printf("Possible from station %d\n", startStation);
            } else {
                System.out.println("Not possible");
            }
        }
    }

    private static int getStartStation(int[] available, int[] required) {
        int startIndex = 0;
        int currentPetrol = 0;

        for (int i = 0; i < available.length; i++) {
            currentPetrol += (available[i] - required[i]);
            if (currentPetrol < 0) {
                startIndex = i + 1;
                currentPetrol = 0;
            }
        }

        if (startIndex == available.length) {
            return -1;
        }

        for (int i = 0; i < startIndex; i++) {
            currentPetrol += (available[i] - required[i]);
            if (currentPetrol < 0) {
                return -1;
            }
        }
        return startIndex + 1;
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
    }
}
