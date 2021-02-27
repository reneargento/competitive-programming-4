package chapter2.section2.f.sorting.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/02/21.
 */
public class LawnMower {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int horizontalCutsNumber = FastReader.nextInt();
        int verticalCutsNumber = FastReader.nextInt();
        double cutWidth = FastReader.nextDouble();

        while (horizontalCutsNumber != 0 || verticalCutsNumber != 0 || cutWidth != 0) {
            double[] horizontalCuts = new double[horizontalCutsNumber];
            double[] verticalCuts = new double[verticalCutsNumber];

            for (int i = 0; i < horizontalCuts.length; i++) {
                horizontalCuts[i] = FastReader.nextDouble();
            }

            for (int i = 0; i < verticalCuts.length; i++) {
                verticalCuts[i] = FastReader.nextDouble();
            }

            boolean isHorizontalCutOk = didGoodJob(horizontalCuts, 75, cutWidth);
            boolean isVerticalCutOk = false;

            if (isHorizontalCutOk) {
                isVerticalCutOk = didGoodJob(verticalCuts, 100, cutWidth);
            }

            if (isHorizontalCutOk && isVerticalCutOk) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }

            horizontalCutsNumber = FastReader.nextInt();
            verticalCutsNumber = FastReader.nextInt();
            cutWidth = FastReader.nextDouble();
        }
    }

    private static boolean didGoodJob(double[] cuts, int size, double cutWidth) {
        Arrays.sort(cuts);
        double previousLocationToCut = 0;
        double halfCutWidth = cutWidth / 2;

        for (double cut : cuts) {
            double previousCut = cut - halfCutWidth;

            if (previousCut > previousLocationToCut) {
                return false;
            }

            previousLocationToCut = cut + halfCutWidth;
        }

        return previousLocationToCut >= size;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }
    }
}
