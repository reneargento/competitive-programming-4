package chapter2.section2.e.sorting.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 16/02/21.
 */
public class BuildingDesigning {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int floorsNumber = FastReader.nextInt();
            List<Integer> blueFloors = new ArrayList<>();
            List<Integer> redFloors = new ArrayList<>();

            for (int i = 0; i < floorsNumber; i++) {
                int size = FastReader.nextInt();
                if (size > 0) {
                    blueFloors.add(size);
                } else {
                    redFloors.add(Math.abs(size));
                }
            }

            blueFloors.sort(Collections.reverseOrder());
            redFloors.sort(Collections.reverseOrder());

            int totalNumberOfFloors = computeTotalNumberOfFloors(blueFloors, redFloors);
            System.out.println(totalNumberOfFloors);
        }
    }

    private static int computeTotalNumberOfFloors(List<Integer> blueFloors, List<Integer> redFloors) {
        if (blueFloors.isEmpty() || redFloors.isEmpty()) {
            if (!blueFloors.isEmpty() || !redFloors.isEmpty()) {
                return 1;
            }
            return 0;
        }

        int totalNumberOfFloors = 1;

        int blueFloorsIndex = 0;
        int redFloorsIndex = 0;
        int previousFloorSize;
        boolean nextFloorIsBlue = blueFloors.get(0) > redFloors.get(0);

        if (nextFloorIsBlue) {
            previousFloorSize = blueFloors.get(0);
            blueFloorsIndex++;
        } else {
            previousFloorSize = redFloors.get(0);
            redFloorsIndex++;
        }
        nextFloorIsBlue = !nextFloorIsBlue;

        while (true) {
            if (nextFloorIsBlue) {
                while (blueFloorsIndex < blueFloors.size()
                        && blueFloors.get(blueFloorsIndex) >= previousFloorSize) {
                    blueFloorsIndex++;
                }
                if (blueFloorsIndex < blueFloors.size()) {
                    totalNumberOfFloors++;
                    previousFloorSize = blueFloors.get(blueFloorsIndex);
                    blueFloorsIndex++;
                } else {
                    break;
                }
            } else {
                while (redFloorsIndex < redFloors.size()
                        && redFloors.get(redFloorsIndex) >= previousFloorSize) {
                    redFloorsIndex++;
                }
                if (redFloorsIndex < redFloors.size()) {
                    totalNumberOfFloors++;
                    previousFloorSize = redFloors.get(redFloorsIndex);
                    redFloorsIndex++;
                } else {
                    break;
                }
            }
            nextFloorIsBlue = !nextFloorIsBlue;
        }
        return totalNumberOfFloors;
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
