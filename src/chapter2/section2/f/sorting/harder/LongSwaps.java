package chapter2.section2.f.sorting.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/02/21.
 */
public class LongSwaps {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        String string = FastReader.next();
        int longSwapSize = FastReader.nextInt();

        boolean canBeSorted = canBeSorted(string, longSwapSize);
        System.out.println(canBeSorted ? "Yes" : "No");
    }

    private static boolean canBeSorted(String string, int longSwapSize) {
        if (longSwapSize <= string.length() / 2) {
            return true;
        }

        char[] characters = string.toCharArray();
        char[] sortedCharacters = string.toCharArray();
        Arrays.sort(sortedCharacters);

        int middleStartIndex = string.length() - longSwapSize;
        int middleEndIndex = longSwapSize - 1;
        return isTheSameSubstring(characters, sortedCharacters, middleStartIndex, middleEndIndex);
    }

    private static boolean isTheSameSubstring(char[] characters, char[] characters2, int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            if (characters[i] != characters2[i]) {
                return false;
            }
        }
        return true;
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
