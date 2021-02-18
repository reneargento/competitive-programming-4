package chapter2.section2.c.twod.array.manipulation.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/02/21.
 */
public class Formula1 {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int grandPrixRaces = FastReader.nextInt();
        int pilots = FastReader.nextInt();

        while (grandPrixRaces != 0 || pilots != 0) {
            int[][] results = new int[grandPrixRaces][pilots];

            for (int race = 0; race < results.length; race++) {
                for (int pilot = 0; pilot < results[0].length; pilot++) {
                    int position = FastReader.nextInt() - 1;
                    results[race][position] = pilot;
                }
            }

            int scoringSystems = FastReader.nextInt();
            for (int s = 0; s < scoringSystems; s++) {
                int lastPositionAwarded = FastReader.nextInt();
                int[] points = new int[lastPositionAwarded];

                for (int i = 0; i < points.length; i++) {
                    points[i] = FastReader.nextInt();
                }

                List<Integer> champions = getChampions(results, points);
                printChampions(champions);
            }

            grandPrixRaces = FastReader.nextInt();
            pilots = FastReader.nextInt();
        }
    }

    private static List<Integer> getChampions(int[][] results, int[] points) {
        List<Integer> champions = new ArrayList<>();
        int pilotsNumber = results[0].length;
        int[] pilotPoints = new int[pilotsNumber];
        int maxPoints = 0;

        for (int race = 0; race < results.length; race++) {
            for (int i = 0; i < points.length; i++) {
                int pilot = results[race][i];
                pilotPoints[pilot] += points[i];
                maxPoints = Math.max(maxPoints, pilotPoints[pilot]);
            }
        }

        for (int i = 0; i < pilotPoints.length; i++) {
            if (pilotPoints[i] == maxPoints) {
                champions.add(i + 1);
            }
        }
        return champions;
    }

    private static void printChampions(List<Integer> champions) {
        StringJoiner description = new StringJoiner(" ");
        for (int champion : champions) {
            description.add(String.valueOf(champion));
        }
        System.out.println(description);
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
