package chapter2.section2.e.sorting.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/02/21.
 */
public class UnderstandingRecursion {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int numbersCount = FastReader.nextInt();
        int caseId = 1;

        while (numbersCount > 0) {
            long[] numbers = new long[numbersCount];
            for (int i = 0; i < numbersCount; i++) {
                numbers[i] = FastReader.nextInt();
            }

            Arrays.sort(numbers);
            long sum = sum(numbers);

            System.out.printf("Case %d: %d\n", caseId, sum);
            caseId++;
            numbersCount = FastReader.nextInt();
        }
    }

    private static long sum(long[] numbers) {
        long sum = 0;
        int sameNumber = 0;

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] == numbers[i - 1]) {
                sameNumber++;
            } else {
                sameNumber = 0;
            }

            sum += i - sameNumber;
        }
        return sum;
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
