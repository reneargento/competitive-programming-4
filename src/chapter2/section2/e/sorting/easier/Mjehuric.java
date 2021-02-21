package chapter2.section2.e.sorting.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/02/21.
 */
public class Mjehuric {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int[] woodenPieces = new int[5];
        for (int i = 0; i < woodenPieces.length; i++) {
            woodenPieces[i] = FastReader.nextInt();
        }
        sort(woodenPieces);
    }

    private static void sort(int[] woodenPieces) {
        boolean isSorted = false;

        while (!isSorted) {
            isSorted = true;

            for (int i = 0; i < woodenPieces.length - 1; i++) {
                if (woodenPieces[i] > woodenPieces[i + 1]) {
                    int aux = woodenPieces[i];
                    woodenPieces[i] = woodenPieces[i + 1];
                    woodenPieces[i + 1] = aux;

                    isSorted = false;
                    printWoodenPieces(woodenPieces);
                }
            }
        }
    }

    private static void printWoodenPieces(int[] woodenPieces) {
        for (int i = 0; i < woodenPieces.length; i++) {
            System.out.print(woodenPieces[i]);

            if (i != woodenPieces.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
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
