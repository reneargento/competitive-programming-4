package chapter6.section7.anagram;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 02/10/2026.
 */
public class Anagram {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String word = FastReader.getLine();
            List<String> anagrams = computeAllAnagrams(word);
            for (String anagram : anagrams) {
                outputWriter.printLine(anagram);
            }
        }
        outputWriter.flush();
    }

    private static List<String> computeAllAnagrams(String word) {
        Set<String> anagramsSet = new HashSet<>();
        Character[] letters = new Character[word.length()];
        for (int i = 0; i < word.length(); i++) {
            letters[i] = word.charAt(i);
        }
        Arrays.sort(letters, new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                return compareLowerUpper(o1, o2);
            }
        });

        while (letters != null) {
            anagramsSet.add(getStringFromArray(letters));
            letters = nextPermutation(letters);
        }

        List<String> anagramList = new ArrayList<>(anagramsSet);
        Collections.sort(anagramList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int minLength = Math.min(o1.length(), o2.length());
                for (int i = 0; i < minLength; i++) {
                    if (o1.charAt(i) != o2.charAt(i)) {
                        return compareLowerUpper(o1.charAt(i), o2.charAt(i));
                    }
                }
                return Integer.compare(o1.length(), o2.length());
            }
        });
        return anagramList;
    }

    private static String getStringFromArray(Character[] array) {
        StringBuilder string = new StringBuilder();
        for (Character character : array) {
            string.append(character);
        }
        return string.toString();
    }

    public static Character[] nextPermutation(Character[] permutation) {
        // 1. Find the largest k, such that permutation[k] < permutation[k + 1]
        int first = getFirstIndexToSwap(permutation);
        if (first == -1) {
            return null; // no greater permutation
        }

        // 2. Find the last index toSwap, that permutation[k] < permutation[toSwap]
        int toSwap = permutation.length - 1;
        while (compareLowerUpper(permutation[first], permutation[toSwap]) >= 0) {
            toSwap--;
        }

        // 3. Swap elements with indexes first and toSwap
        swap(permutation, first++, toSwap);

        // 4. Reverse sequence from k + 1 to n (inclusive)
        toSwap = permutation.length - 1;
        while (first < toSwap) {
            swap(permutation, first++, toSwap--);
        }
        return permutation;
    }

    // Finds the largest k, that permutation[k] < permutation[k + 1]
    // If no such k exists (there is not greater permutation), return -1
    private static int getFirstIndexToSwap(Character[] permutation) {
        for (int i = permutation.length - 2; i >= 0; --i) {
            if (compareLowerUpper(permutation[i], permutation[i + 1]) < 0) {
                return i;
            }
        }
        return -1;
    }

    private static int compareLowerUpper(Character character1, Character character2) {
        Character lowerCaseCharacter1 = Character.toLowerCase(character1);
        Character lowerCaseCharacter2 = Character.toLowerCase(character2);

        if (lowerCaseCharacter1 != lowerCaseCharacter2) {
            return lowerCaseCharacter1.compareTo(lowerCaseCharacter2);
        }
        return character1.compareTo(character2);
    }

    // Swaps two elements (with indexes index1 and index2) in array
    private static void swap(Character[] permutation, int index1, int index2) {
        Character temp = permutation[index1];
        permutation[index1] = permutation[index2];
        permutation[index2] = temp;
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

        public void flush() {
            writer.flush();
        }
    }
}