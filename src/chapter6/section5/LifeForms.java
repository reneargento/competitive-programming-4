package chapter6.section5;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 10/09/2026.
 */
public class LifeForms {

    private static class SuffixData {
        int index;
        int length;

        public SuffixData(int index, int length) {
            this.index = index;
            this.length = length;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int caseId = 1;
        int lifeForms = FastReader.nextInt();
        while (lifeForms != 0) {
            String[] dnaSequences = new String[lifeForms];
            for (int i = 0; i < dnaSequences.length; i++) {
                dnaSequences[i] = FastReader.next();
            }

            if (caseId > 1) {
                outputWriter.printLine();
            }
            List<String> longestSharedDna = computeLongestSharedDna(dnaSequences);
            if (longestSharedDna.isEmpty()) {
                outputWriter.printLine("?");
            } else {
                for (String dna : longestSharedDna) {
                    outputWriter.printLine(dna);
                }
            }
            caseId++;
            lifeForms = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<String> computeLongestSharedDna(String[] dnaSequences) {
        String allDna = concatenateAllDna(dnaSequences);
        SuffixArrayNlgN suffixArray = new SuffixArrayNlgN(allDna);
        int[] owners = computeOwners(suffixArray, allDna, dnaSequences);
        return computeLongestSharedDna(dnaSequences, suffixArray, owners);
    }

    private static String concatenateAllDna(String[] dnaSequences) {
        StringBuilder allDna = new StringBuilder();
        for (String dna : dnaSequences) {
            allDna.append(dna).append("$");
        }
        return allDna.toString();
    }

    private static int[] computeOwners(SuffixArrayNlgN suffixArray, String allDna, String[] dnaSequences) {
        int[] ownerAtIndex = new int[allDna.length()];
        int index = 0;
        for (int i = 0; i < dnaSequences.length; i++) {
            for (int j = 0; j < dnaSequences[i].length(); j++) {
                ownerAtIndex[index] = i;
                index++;
            }
            ownerAtIndex[index] = -1;
            index++;
        }

        int[] owners = new int[suffixArray.suffixArray.length];
        for (int i = 0; i < owners.length; i++) {
            owners[i] = ownerAtIndex[suffixArray.suffixArray[i]];
        }
        return owners;
    }

    private static List<String> computeLongestSharedDna(String[] dnaSequences, SuffixArrayNlgN suffixArray,
                                                        int[] owners) {
        List<SuffixData> suffixDataList = new ArrayList<>();
        int bestLength = 0;

        Deque<Integer> minDeque = new ArrayDeque<>();
        int targetStrings = dnaSequences.length / 2 + 1;
        int[] ownersCount = new int[dnaSequences.length];
        int distinctOwners = 0;
        int left = 0;

        for (int right = 0; right < suffixArray.lcp.length; right++) {
            int ownerId = owners[right];
            if (ownerId >= 0) {
                if (ownersCount[ownerId] == 0) {
                    distinctOwners++;
                }
                ownersCount[ownerId]++;
            }

            if (right > 0) {
                int currentLcp = suffixArray.lcp[right];
                while (!minDeque.isEmpty()
                        && suffixArray.lcp[minDeque.peekLast()] >= currentLcp) {
                    minDeque.pollLast();
                }
                minDeque.offerLast(right);
            }

            while (distinctOwners >= targetStrings) {
                int lcp = minDeque.isEmpty() ? 0 : suffixArray.lcp[minDeque.peekFirst()];
                if (lcp > bestLength) {
                    bestLength = lcp;
                    suffixDataList = new ArrayList<>();
                    suffixDataList.add(new SuffixData(suffixArray.suffixArray[right], lcp));
                } else if (lcp == bestLength) {
                    suffixDataList.add(new SuffixData(suffixArray.suffixArray[right], lcp));
                }

                int ownerLeft = owners[left];
                if (ownerLeft >= 0) {
                    ownersCount[ownerLeft]--;
                    if (ownersCount[ownerLeft] == 0) {
                        distinctOwners--;
                    }
                }

                int leftLcpIndex = left + 1;
                if (!minDeque.isEmpty()
                        && minDeque.peekFirst() == leftLcpIndex) {
                    minDeque.pollFirst();
                }
                left++;
            }
        }

        if (bestLength == 0) {
            return new ArrayList<>();
        }

        Set<String> longestSharedDnaSet = new HashSet<>();
        for (SuffixData suffixData : suffixDataList) {
            longestSharedDnaSet.add(new String(suffixArray.string, suffixData.index, suffixData.length));
        }
        List<String> longestSharedDnaList = new ArrayList<>(longestSharedDnaSet);
        Collections.sort(longestSharedDnaList);
        return longestSharedDnaList;
    }

    private static class SuffixArrayNlgN {
        public int[] suffixArray;
        public int[] lcp;

        private final char[] string;
        private final int stringLength;

        public SuffixArrayNlgN(String string) {
            this.string = string.toCharArray();
            stringLength = this.string.length;
            constructSuffixArray();
            computeLcp();
        }

        private void countingSort(int[] ranks, int k) {
            int sum = 0;
            int[] tempSuffixArray = new int[stringLength];
            int maxi = Math.max(301, stringLength + 1);
            int[] count = new int[maxi];
            for (int i = 0; i < stringLength; i++) {
                count[i + k < stringLength ? ranks[i + k] : 0]++;
            }
            for (int i = 0; i < maxi; i++) {
                int aux = count[i];
                count[i] = sum;
                sum += aux;
            }
            for (int i = 0; i < stringLength; i++) {
                tempSuffixArray[count[suffixArray[i] + k < stringLength ? ranks[suffixArray[i] + k] : 0]++] = suffixArray[i];
            }
            for (int i = 0; i < stringLength; i++) {
                suffixArray[i] = tempSuffixArray[i];
            }
        }

        private void constructSuffixArray() {
            suffixArray = new int[stringLength];
            int[] ranks = new int[stringLength];
            int[] tempRanks = new int[stringLength];

            for (int i = 0; i < stringLength; i++) {
                ranks[i] = string[i];
            }
            for (int i = 0; i < stringLength; i++) {
                suffixArray[i] = i;
            }
            for (int k = 1; k < stringLength; k <<= 1) {
                countingSort(ranks, k);
                countingSort(ranks, 0);

                int rank = 0;
                tempRanks[suffixArray[0]] = 1;
                for (int i = 1; i < stringLength; i++) {
                    boolean areTheSame =
                            ranks[suffixArray[i]] == ranks[suffixArray[i - 1]]
                                    && (suffixArray[i] + k < stringLength ? ranks[suffixArray[i] + k] : 0)
                                         == (suffixArray[i - 1] + k < stringLength ? ranks[suffixArray[i - 1] + k] : 0);
                    if (!areTheSame) {
                        rank++;
                    }
                    tempRanks[suffixArray[i]] = rank + 1;
                }
                for (int i = 0; i < stringLength; i++) {
                    ranks[i] = tempRanks[i];
                }
                if (rank == stringLength - 1) {
                    break;
                }
            }
        }

        private void computeLcp() {
            int length = 0;
            lcp = new int[stringLength];
            int[] plcp = new int[stringLength];
            int[] phi = new int[stringLength];
            phi[suffixArray[0]] = -1;
            int[] remainingCharacters = computeRemainingCharactersBeforeSeparator();

            for (int i = 1; i < stringLength; i++) {
                phi[suffixArray[i]] = suffixArray[i - 1];
            }
            for (int i = 0; i < stringLength; i++) {
                if (phi[i] == -1) {
                    plcp[i] = 0;
                    continue;
                }
                int maxLength = Math.min(remainingCharacters[i], remainingCharacters[phi[i]]);
                while (length < maxLength
                        && string[i + length] == string[phi[i] + length]) {
                    length++;
                }
                plcp[i] = length;
                length = Math.max(length - 1, 0);
            }
            for (int i = 1; i < stringLength; i++) {
                lcp[i] = plcp[suffixArray[i]];
            }
        }

        private int[] computeRemainingCharactersBeforeSeparator() {
            int[] remainingCharacters = new int[string.length];
            int characters = 0;
            for (int i = string.length - 1; i >= 0; i--) {
                if (string[i] == '$') {
                    characters = 0;
                } else {
                    characters++;
                }
                remainingCharacters[i] = characters;
            }
            return remainingCharacters;
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

        public void flush() {
            writer.flush();
        }
    }
}