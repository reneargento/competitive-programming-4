package chapter4.session6.e.bipartite.graph;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 14/08/24.
 */
@SuppressWarnings("unchecked")
public class PrimePairs {

    private static class PartitionedNumbers {
        int[] partitionedNumbers;
        int leftPartitionSize;

        public PartitionedNumbers(int[] partitionedNumbers, int leftPartitionSize) {
            this.partitionedNumbers = partitionedNumbers;
            this.leftPartitionSize = leftPartitionSize;
        }
    }

    public static int[] matches(int[] numbers) {
        List<Integer> matchesWithFirstNumber = new ArrayList<>();
        int targetNumberOfMatches = numbers.length / 2;
        boolean[] isPrime = eratosthenesSieve(2001);

        PartitionedNumbers partitionedNumbersClassification = partitionNumbers(numbers);
        int[] partitionedNumbers = partitionedNumbersClassification.partitionedNumbers;
        int leftPartitionSize = partitionedNumbersClassification.leftPartitionSize;

        for (int numberId = leftPartitionSize; numberId < numbers.length; numberId++) {
            int sum = partitionedNumbers[0] + partitionedNumbers[numberId];
            if (isPrime[sum]) {
                List<Integer>[] adjacencyList = new List[numbers.length];
                for (int i = 0; i < adjacencyList.length; i++) {
                    adjacencyList[i] = new ArrayList<>();
                }
                adjacencyList[0].add(numberId);

                for (int numberId1 = 1; numberId1 < leftPartitionSize; numberId1++) {
                    for (int numberId2 = leftPartitionSize; numberId2 < partitionedNumbers.length; numberId2++) {
                        if (numberId2 == numberId) {
                            continue;
                        }
                        int numbersSum = partitionedNumbers[numberId1] + partitionedNumbers[numberId2];
                        if (isPrime[numbersSum]) {
                            adjacencyList[numberId1].add(numberId2);
                        }
                    }
                }

                int maximumCardinality = computeMaximumCardinality(adjacencyList, leftPartitionSize);
                if (maximumCardinality == targetNumberOfMatches) {
                    matchesWithFirstNumber.add(partitionedNumbers[numberId]);
                }
            }
        }

        int[] matches = new int[matchesWithFirstNumber.size()];
        for (int i = 0; i < matches.length; i++) {
            matches[i] = matchesWithFirstNumber.get(i);
        }
        Arrays.sort(matches);
        return matches;
    }

    private static boolean[] eratosthenesSieve(long number) {
        int maxNumberToCheck = (int) Math.floor(Math.sqrt(number));
        boolean[] isPrime = new boolean[(int) number + 1];

        // 1- Mark all numbers as prime
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        // 2- Remove numbers multiple of the first element
        // 3- Repeat until we finish verifying the maxNumberToCheck
        for (long i = 2; i <= maxNumberToCheck; i++) { //maxNumberToCheck is also equal to: i * i <= n
            if (isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }
            }
        }
        return isPrime;
    }

    private static PartitionedNumbers partitionNumbers(int[] numbers) {
        int[] partitionedNumbers = new int[numbers.length];
        int leftPartitionSize = 0;
        boolean firstNumberIsOdd = numbers[0] % 2 == 1;

        int oddIndex;
        int evenIndex;
        if (firstNumberIsOdd) {
            oddIndex = 0;
            evenIndex = partitionedNumbers.length - 1;
        } else {
            oddIndex = partitionedNumbers.length - 1;
            evenIndex = 0;
        }

        for (int number : numbers) {
            if (number % 2 == 1) {
                if (firstNumberIsOdd) {
                    partitionedNumbers[oddIndex++] = number;
                    leftPartitionSize++;
                } else {
                    partitionedNumbers[oddIndex--] = number;
                }
            } else {
                if (firstNumberIsOdd) {
                    partitionedNumbers[evenIndex--] = number;
                } else {
                    partitionedNumbers[evenIndex++] = number;
                    leftPartitionSize++;
                }
            }
        }
        return new PartitionedNumbers(partitionedNumbers, leftPartitionSize);
    }

    private static int computeMaximumCardinality(List<Integer>[] adjacencyList, int leftPartitionVertexMaxId) {
        int maximumMatches = 0;
        int[] match = new int[adjacencyList.length];
        Arrays.fill(match, -1);

        for (int vertexID = 0; vertexID < leftPartitionVertexMaxId; vertexID++) {
            boolean[] visited = new boolean[adjacencyList.length];
            maximumMatches += tryToMatch(adjacencyList, match, visited, vertexID);
        }
        return maximumMatches;
    }

    private static int tryToMatch(List<Integer>[] adjacencyList, int[] match, boolean[] visited, int vertexID) {
        if (visited[vertexID]) {
            return 0;
        }
        visited[vertexID] = true;

        for (int neighbor : adjacencyList[vertexID]) {
            if (match[neighbor] == -1 || tryToMatch(adjacencyList, match, visited, match[neighbor]) == 1) {
                match[neighbor] = vertexID; // flip status
                return 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) throws IOException {
        int[][] numbers = {
                { 1, 4, 7, 10, 11, 12 },
                { 11, 1, 4, 7, 10, 12 },
                { 8, 9, 1, 14 },
                { 34, 39, 32, 4, 9, 35, 14, 17 },
                { 941, 902, 873, 841, 948, 851, 945, 854, 815, 898,
                        806, 826, 976, 878, 861, 919, 926, 901, 875, 864 }
        };
        int[][] expected = {
                { 4, 10 },
                { 12 },
                { },
                { 9, 39 },
                { 806, 926 }
        };

        boolean accepted = true;
        for (int testId = 0; testId < numbers.length; testId++) {
            int[] matches = matches(numbers[testId]);

            if (!validateTest(matches, expected[testId])) {
                System.out.println("Wrong answer on test " + (testId + 1));
                accepted = false;
                break;
            }
        }

        if (accepted) {
            System.out.println("ACCEPTED");
        }
    }

    private static boolean validateTest(int[] matches, int[] expected) {
        if (matches.length != expected.length) {
            return false;
        }
        for (int i = 0; i < matches.length; i++) {
            if (matches[i] != expected[i]) {
                return false;
            }
        }
        return true;
    }
}
