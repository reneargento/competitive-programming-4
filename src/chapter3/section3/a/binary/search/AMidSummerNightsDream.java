package chapter3.section3.a.binary.search;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 13/03/22.
 */
public class AMidSummerNightsDream {

    private static class Result {
        int minimumAValue;
        int possibleAsInInput;
        int possibleAs;

        public Result(int minimumAValue, int possibleAsInInput, int possibleAs) {
            this.minimumAValue = minimumAValue;
            this.possibleAsInInput = possibleAsInInput;
            this.possibleAs = possibleAs;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            int[] numbers = new int[Integer.parseInt(line)];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = FastReader.nextInt();
            }
            Result result = computeAs(numbers);
            outputWriter.printLine(String.format("%d %d %d", result.minimumAValue, result.possibleAsInInput,
                    result.possibleAs));
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result computeAs(int[] numbers) {
        Arrays.sort(numbers);
        List<Integer> aList = getAs(numbers);
        int minimumAValue = aList.get(0);
        int possibleAs;
        if (aList.size() == 1) {
            possibleAs = 1;
        } else {
            possibleAs = aList.get(1) - aList.get(0) + 1;
        }

        Set<Integer> aSet = new HashSet<>(aList);
        int possibleAsInInput = 0;
        for (int aCandidate : aSet) {
            int startIndex = binarySearch(numbers, aCandidate, true, 0, numbers.length - 1);
            if (startIndex == -1) {
                continue;
            }
            int endIndex = binarySearch(numbers, aCandidate, false, 0, numbers.length - 1);
            possibleAsInInput += (endIndex - startIndex + 1);
        }
        return new Result(minimumAValue, possibleAsInInput, possibleAs);
    }

    private static List<Integer> getAs(int[] numbers) {
        List<Integer> aList = new ArrayList<>();
        int middleIndex = numbers.length / 2;

        if (numbers.length % 2 == 1) {
            int a = numbers[middleIndex];
            aList.add(a);
        } else {
            int firstA = numbers[middleIndex - 1];
            int lastA = numbers[middleIndex];
            aList.add(firstA);
            aList.add(lastA);
        }
        return aList;
    }

    private static int binarySearch(int[] values, int target, boolean isStartIndex, int low, int high) {
        int result = -1;

        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (isStartIndex) {
                if (values[middle] < target) {
                    low = middle + 1;
                } else if (values[middle] > target) {
                    high = middle - 1;
                } else {
                    result = middle;
                    int candidate = binarySearch(values, target, true, low, middle - 1);
                    if (candidate != -1) {
                        result = candidate;
                    }
                    break;
                }
            } else {
                if (values[middle] > target) {
                    high = middle - 1;
                } else if (values[middle] < target) {
                    low = middle + 1;
                } else {
                    result = middle;
                    int candidate = binarySearch(values, target, false, middle + 1, high);
                    if (candidate != -1) {
                        result = candidate;
                    }
                    break;
                }
            }
        }
        return result;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
