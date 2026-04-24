package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 24/04/26.
 */
// Based on https://github.com/metaphysis/Code/blob/master/UVa%20Online%20Judge/volume001/179%20Code%20Breaking/program.cpp
public class CodeBreaking {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String plaintext = FastReader.getLine();
        while (!plaintext.equals("#")) {
            String cypherText1 = FastReader.getLine();
            String cypherText2 = FastReader.getLine();

            String result = decryptText2(plaintext, cypherText1, cypherText2);
            outputWriter.printLine(result);
            plaintext = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String decryptText2(String plainText, String cypherText1, String cypherText2) {
        List<Integer> kCandidates = computeKCandidates(plainText, cypherText1);
        if (kCandidates.isEmpty()) {
            return cypherText2;
        }

        int[] permutation = getPermutation(plainText, cypherText1, kCandidates);
        if (permutation == null) {
            return cypherText2;
        }
        int permutationLength = permutation.length;

        int plainText2Length = (int) (Math.ceil(cypherText2.length() / (double) permutationLength) * permutationLength);
        char[] plainText2 = new char[plainText2Length];
        Arrays.fill(plainText2, '?');
        for (int i = 0; i < cypherText2.length(); i += permutationLength) {
            for (int j = 0; j < permutationLength; j++) {
                int permutedIndex = permutation[j];
                if (i + j < plainText2.length
                        && i + permutedIndex < cypherText2.length()) {
                    plainText2[i + j] = cypherText2.charAt(i + permutedIndex);
                }
            }
        }
        return new String(plainText2);
    }

    private static List<Integer> computeKCandidates(String plainText, String cypherText) {
        List<Integer> kCandidates = new ArrayList<>();
        for (int k = 1; k <= plainText.length(); k++) {
            boolean isCorrect = true;

            for (int i = 0; i < plainText.length(); i += k) {
                int endIndex = Math.min(plainText.length(), i + k);
                char[] plainTextSubArray = plainText.substring(i, endIndex).toCharArray();
                char[] cypherTextSubArray = cypherText.substring(i, endIndex).toCharArray();

                Arrays.sort(plainTextSubArray);
                Arrays.sort(cypherTextSubArray);
                if (!new String(plainTextSubArray).equals(new String(cypherTextSubArray))) {
                    isCorrect = false;
                    break;
                }
            }

            if (isCorrect) {
                kCandidates.add(k);
            }
        }
        return kCandidates;
    }

    private static int[] getPermutation(String plainText, String cypherText, List<Integer> kCandidates) {
        for (int kCandidate : kCandidates) {
            if (kCandidate <= 7) {
                int[] permutation = initializePermutation(kCandidate);
                while (permutation != null) {
                    if (isValidPermutation(plainText, cypherText, permutation)) {
                        return permutation;
                    }
                    permutation = nextPermutation(permutation);
                }
            } else {
                int[] permutation = getPermutationWithBacktrack(plainText, cypherText, kCandidate);
                if (permutation != null) {
                    return permutation;
                }
            }
        }
        return null;
    }

    private static int[] initializePermutation(int length) {
        int[] permutation = new int[length];
        for (int i = 0; i < permutation.length; i++) {
            permutation[i] = i;
        }
        return permutation;
    }

    private static boolean isValidPermutation(String plainText, String cypherText, int[] permutation) {
        char[] cypherCandidate = new char[plainText.length()];

        for (int i = 0; i < plainText.length(); i += permutation.length) {
            for (int j = 0; j < permutation.length; j++) {
                int permutedIndex = permutation[j];
                if (i + permutedIndex < cypherCandidate.length
                        && i + j < plainText.length()) {
                    cypherCandidate[i + permutedIndex] = plainText.charAt(i + j);
                }
            }

            for (int j = 0; j < permutation.length; j++) {
                if (cypherCandidate[i + j] != cypherText.charAt(i + j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int timesTried;
    private static int[] getPermutationWithBacktrack(String plainText, String cypherText, int kCandidate) {
        for (int i = 0; i + kCandidate <= plainText.length(); i += kCandidate) {
            int[] permutation = initializePermutation(kCandidate);

            String source = plainText.substring(i, i + kCandidate);
            String target = cypherText.substring(i, i + kCandidate);
            boolean[] matched = new boolean[kCandidate];
            timesTried = 0;

            boolean validPermutation = backtrack(plainText, cypherText, permutation, source, target, matched,
                    kCandidate, 0);
            if (validPermutation) {
                return permutation;
            }
        }
        return null;
    }

    private static boolean backtrack(String plainText, String cypherText, int[] permutation, String source,
                                     String target, boolean[] matched, int kCandidate, int index) {
        if (timesTried > 10000) {
            return false;
        }
        if (index == kCandidate) {
            timesTried++;
            return isValidPermutation(plainText, cypherText, permutation);
        }

        for (int i = 0; i < target.length(); i++) {
            if (!matched[i] && source.charAt(index) == target.charAt(i)) {
                matched[i] = true;
                permutation[index] = i;
                boolean validPermutation = backtrack(plainText, cypherText, permutation, source, target, matched,
                        kCandidate, index + 1);
                if (validPermutation) {
                    return true;
                }
                matched[i] = false;
            }
        }
        return false;
    }

    private static int[] nextPermutation(int[] permutation) {
        int first = getFirstIndexToSwap(permutation);
        if (first == -1) {
            return null; // no greater permutation
        }

        int toSwap = permutation.length - 1;
        while (permutation[first] >= permutation[toSwap]) {
            toSwap--;
        }

        swap(permutation, first++, toSwap);

        toSwap = permutation.length - 1;
        while (first < toSwap) {
            swap(permutation, first++, toSwap--);
        }
        return permutation;
    }

    private static int getFirstIndexToSwap(int[] permutation) {
        for (int i = permutation.length - 2; i >= 0; --i) {
            if (permutation[i] < permutation[i + 1]) {
                return i;
            }
        }
        return -1;
    }

    private static void swap(int[] permutation, int index1, int index2) {
        int temp = permutation[index1];
        permutation[index1] = permutation[index2];
        permutation[index2] = temp;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
