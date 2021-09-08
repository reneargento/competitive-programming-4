package chapter3.section2.a.precalculateable;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 09/09/21.
 */
public class Run {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<Integer> runaroundNumbers = generateRunaroundNumbers();
        int caseNumber = 1;
        int number = FastReader.nextInt();

        while (number != 0) {
            int nextRunaroundNumber = getNextRunaroundNumber(runaroundNumbers, number);
            outputWriter.printLine(String.format("Case %d: %d", caseNumber, nextRunaroundNumber));
            caseNumber++;
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int getNextRunaroundNumber(List<Integer> runaroundNumbers, int number) {
        int low = 0;
        int high = runaroundNumbers.size() - 1;

        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (runaroundNumbers.get(middle) == number) {
                return number;
            } else if (runaroundNumbers.get(middle) < number) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return runaroundNumbers.get(low);
    }

    private static List<Integer> generateRunaroundNumbers() {
        List<Integer> runaroundNumbers = new ArrayList<>();

        for (int number = 10; number <= 10000000; number++) {
            if (isRunaround(number)) {
                runaroundNumbers.add(number);
            }
        }
        return runaroundNumbers;
    }

    private static boolean isRunaround(int number) {
        char[] digits = String.valueOf(number).toCharArray();
        int length = digits.length;
        boolean[] visited = new boolean[length];
        Set<Integer> uniqueVisits = new HashSet<>();

        int visits = 1;
        visited[0] = true;
        int index = 0;

        while (true) {
            int digitValue = Character.getNumericValue(digits[index]);
            if (uniqueVisits.contains(digitValue)) {
                return false;
            }
            uniqueVisits.add(digitValue);

            index = (index + digitValue) % length;

            if (visited[index]) {
                return index == 0 && visits == length;
            } else {
                visited[index] = true;
                visits++;
            }
        }
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
