package chapter2.section2.g.special.sorting.problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/03/21.
 */
// Based on https://nordic.icpc.io/ncpc2012/ncpc2012slides.pdf
public class BreadSorting {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int numberOfBreads = FastReader.nextInt();
        long[] breads = new long[numberOfBreads];

        for (int i = 0; i < breads.length; i++) {
            breads[i] = FastReader.nextInt();
        }

        long[] targetOrder = new long[numberOfBreads];
        for (int i = 0; i < breads.length; i++) {
            targetOrder[i] = FastReader.nextInt();
        }

        boolean isPossible = isPossibleToSort(breads, targetOrder);
        System.out.println(isPossible? "Possible" : "Impossible");
    }

    private static boolean isPossibleToSort(long[] breads, long[] targetOrder) {
        long originalInversions = countInversions(breads);
        long targetInversions = countInversions(targetOrder);
        return (originalInversions % 2) == (targetInversions % 2);
    }

    private static long countInversions(long[] array) {
        long[] helper = new long[array.length];
        return countInversions(array, helper, 0, array.length - 1);
    }

    private static long countInversions(long[] array, long[] helper, int low, int high) {
        if (low >= high) {
            return 0;
        }
        int middle = low + (high - low) / 2;

        long inversions = 0;

        inversions += countInversions(array, helper, low, middle);
        inversions += countInversions(array, helper, middle + 1, high);
        inversions += countSplitInversions(array, helper, low, middle, high);
        return inversions;
    }

    private static long countSplitInversions(long[] array, long[] helper, int low, int middle, int high) {
        long inversions = 0;

        for (int i = low; i <= high; i++) {
            helper[i] = array[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;

        while (i <= middle && j <= high) {
            if (helper[i] <= helper[j]) {
                array[k] = helper[i];
                i++;
            } else {
                array[k] = helper[j];
                j++;
                inversions += middle - i + 1;
            }
            k++;
        }

        while (i <= middle) {
            array[k] = helper[i];
            k++;
            i++;
        }
        return inversions;
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
