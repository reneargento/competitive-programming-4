package chapter2.section2.f.sorting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/02/21.
 */
public class PartyGames {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int namesNumber = FastReader.nextInt();

        while (namesNumber != 0) {
            String[] names = new String[namesNumber];
            for (int i = 0; i < names.length; i++) {
                names[i] = FastReader.next();
            }

            String middleString = computeMiddleString(names);
            outputWriter.printLine(middleString);

            namesNumber = FastReader.nextInt();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static String computeMiddleString(String[] names) {
        Arrays.sort(names);

        int middleIndex = names.length / 2;
        String name1 = names[middleIndex - 1];
        String name2 = names[middleIndex];

        int minLength = Math.min(name1.length(), name2.length());

        for (int i = 0; i < minLength; i++) {
            if (name1.charAt(i) != name2.charAt(i)
                    && i < name1.length() - 1) {
                String equalSubstring = name1.substring(0, i);
                char currentCharacter = name1.charAt(i);
                char nextCharacter = (char) (currentCharacter + 1);

                String nextString = equalSubstring + nextCharacter;
                if (nextString.compareTo(name2) < 0) {
                    return nextString;
                }

                if (i < name1.length() - 2) {
                    int nextUpgradableIndex = findNextNonZCharIndex(name1, i + 1);
                    if (nextUpgradableIndex == -1 || nextUpgradableIndex == name1.length() - 1) {
                        break;
                    }

                    String newSubstring = name1.substring(0, nextUpgradableIndex);
                    char upgradableCharacter = (char) (name1.charAt(nextUpgradableIndex) + 1);
                    return newSubstring + upgradableCharacter;
                }
            }
        }
        return name1;
    }

    private static int findNextNonZCharIndex(String name, int startIndex) {
        for (int i = startIndex; i < name.length(); i++) {
            if (name.charAt(i) != 'Z') {
                return i;
            }
        }
        return -1;
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
