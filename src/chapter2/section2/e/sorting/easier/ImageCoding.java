package chapter2.section2.e.sorting.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/02/21.
 */
public class ImageCoding {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int rows = FastReader.nextInt();
            int columns = FastReader.nextInt();
            int importantRegionSize = FastReader.nextInt();
            int otherRegionSize = FastReader.nextInt();

            int imageSize = computeImageSize(rows, columns, importantRegionSize, otherRegionSize);
            System.out.printf("Case %d: %d\n", t, imageSize);
        }
    }

    private static int computeImageSize(int rows, int columns, int importantRegionSize, int otherRegionSize)
            throws IOException {
        int imageSize = 0;
        Map<Character, Integer> frequencies = new HashMap<>();
        int maxFrequency = 0;

        for (int row = 0; row < rows; row++) {
            String imageData = FastReader.next();
            for (int column = 0; column < columns; column++) {
                char character = imageData.charAt(column);
                int frequency = frequencies.getOrDefault(character, 0);
                frequency++;

                maxFrequency = Math.max(maxFrequency, frequency);
                frequencies.put(character, frequency);
            }
        }

        for (Character character : frequencies.keySet()) {
            int frequency = frequencies.get(character);
            if (frequency == maxFrequency) {
                imageSize += frequency * importantRegionSize;
            } else {
                imageSize += frequency * otherRegionSize;
            }
        }
        return imageSize;
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

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
