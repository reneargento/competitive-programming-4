package chapter2.section2.f.sorting.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/02/21.
 */
public class DirtyDriving {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int cars = FastReader.nextInt();
        int decelerationConstant = FastReader.nextInt();
        int[] distances = new int[cars];

        for (int i = 0; i < distances.length; i++) {
            distances[i] = FastReader.nextInt();
        }

        Arrays.sort(distances);
        int minimumDistance = computeMinimumDistanceFromFirstCar(distances, decelerationConstant);
        System.out.println(minimumDistance);
    }

    private static int computeMinimumDistanceFromFirstCar(int[] distances, int decelerationConstant) {
        int minimumDistance = 0;

        for (int i = 0; i < distances.length; i++) {
            int distanceNeeded = decelerationConstant * (i + 1);
            int distanceOfCarToFirstCar = distances[i] - distances[0];
            distanceNeeded -= distanceOfCarToFirstCar;

            minimumDistance = Math.max(minimumDistance, distanceNeeded);
        }
        return minimumDistance;
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
