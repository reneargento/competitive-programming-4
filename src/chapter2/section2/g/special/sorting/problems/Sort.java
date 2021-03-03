package chapter2.section2.g.special.sorting.problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 03/03/21.
 */
public class Sort {

    private static class NumberInformation {
        int id;
        int frequency;

        public NumberInformation(int id) {
            this.id = id;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int messageLength = FastReader.nextInt();
        long maxValue = FastReader.nextLong();

        Map<Long, NumberInformation> frequencyMap = new HashMap<>();
        int numberId = 0;

        Long[] numbers = new Long[messageLength];
        for (int i = 0; i < numbers.length; i++) {
            long value = FastReader.nextLong();

            if (!frequencyMap.containsKey(value)) {
                frequencyMap.put(value, new NumberInformation(numberId));
                numberId++;
            }
            frequencyMap.get(value).frequency++;
            numbers[i] = value;
        }

        sortNumbers(numbers, frequencyMap);
        printNumbers(numbers);
    }

    private static void sortNumbers(Long[] numbers, Map<Long, NumberInformation> frequencyMap) {
        Arrays.sort(numbers, new Comparator<Long>() {
            @Override
            public int compare(Long number1, Long number2) {
                NumberInformation numberInformation1 = frequencyMap.get(number1);
                NumberInformation numberInformation2 = frequencyMap.get(number2);

                int frequency1 = numberInformation1.frequency;
                int frequency2 = numberInformation2.frequency;

                if (frequency1 != frequency2) {
                    return Integer.compare(frequency2, frequency1);
                }
                return Integer.compare(numberInformation1.id, numberInformation2.id);
            }
        });
    }

    private static void printNumbers(Long[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i]);

            if (i != numbers.length - 1) {
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}
