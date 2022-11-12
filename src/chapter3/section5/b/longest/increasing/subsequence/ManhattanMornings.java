package chapter3.section5.b.longest.increasing.subsequence;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/11/22.
 */
public class ManhattanMornings {

    private static class Location implements Comparable<Location> {
        int x;
        int y;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Location other) {
            if (x != other.x) {
                return Integer.compare(x, other.x);
            }
            return Integer.compare(y, other.y);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int errandsNumber = FastReader.nextInt();
        Location house = new Location(FastReader.nextInt(), FastReader.nextInt());
        Location workplace = new Location(FastReader.nextInt(), FastReader.nextInt());

        boolean shouldFlipX = house.x > workplace.x;
        boolean shouldFlipY = house.y > workplace.y;

        if (shouldFlipX) {
            house.x = -house.x;
            workplace.x = -workplace.x;
        }
        if (shouldFlipY) {
            house.y = -house.y;
            workplace.y = -workplace.y;
        }

        List<Location> errands = new ArrayList<>();
        for (int i = 0; i < errandsNumber; i++) {
            int xCoordinate = FastReader.nextInt();
            int yCoordinate = FastReader.nextInt();

            if (shouldFlipX) {
                xCoordinate = -xCoordinate;
            }
            if (shouldFlipY) {
                yCoordinate = -yCoordinate;
            }
            if (xCoordinate < house.x || xCoordinate > workplace.x || yCoordinate < house.y || yCoordinate > workplace.y) {
                continue;
            }
            errands.add(new Location(xCoordinate, yCoordinate));
        }
        int maxErrands = longestNonDecreasingSubsequence(errands);
        outputWriter.printLine(maxErrands);
        outputWriter.flush();
    }

    private static int longestNonDecreasingSubsequence(List<Location> errands) {
        if (errands.isEmpty()) {
            return 0;
        }
        Collections.sort(errands);

        int[] endIndexes = new int[errands.size()];
        int length = 1;

        for (int i = 1; i < errands.size(); i++) {
            // Case 1 - smallest end element
            if (errands.get(i).y < errands.get(endIndexes[0]).y) {
                endIndexes[0] = i;
            } else if (errands.get(endIndexes[length - 1]).y <= errands.get(i).y) {
                // Case 2 - highest end element - extends longest increasing subsequence
                endIndexes[length++] = i;
            } else {
                // Case 3 - middle end element
                int indexToReplace = nextElementIndex(errands, endIndexes, 0, length - 1, errands.get(i));
                endIndexes[indexToReplace] = i;
            }
        }
        return length;
    }

    private static int nextElementIndex(List<Location> errands, int[] endIndexes, int low, int high, Location location) {
        while (high > low) {
            int middle = low + (high - low) / 2;

            if (errands.get(endIndexes[middle]).y > location.y) {
                high = middle;
            } else {
                low = middle + 1;
            }
        }
        return high;
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

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void flush() {
            writer.flush();
        }
    }
}
