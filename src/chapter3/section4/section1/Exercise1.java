package chapter3.section4.section1;

import java.util.Arrays;

/**
 * Created by Rene Argento on 01/05/22.
 */
// Based on https://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.57.3243&rep=rep1&type=pdf
// And https://math.stackexchange.com/questions/4139845/change-making-problem-pearson-algorithm-to-check-the-optimality-of-greedy-solu
public class Exercise1 {

    private static final int MAX_VALUE = 100000;

    public static void main(String[] args) {
        int[] coins1 = { 10, 7, 5, 4, 1 };
        findSmallestCounterExample(coins1, "S1");

        int[] coins2 = { 64, 32, 16, 8, 4, 2, 1 };
        findSmallestCounterExample(coins2, "S2");

        int[] coins3 = { 13, 11, 7, 5, 3, 2, 1 };
        findSmallestCounterExample(coins3, "S3");

        int[] coins4 = { 7, 6, 5, 4, 3, 2, 1 };
        findSmallestCounterExample(coins4, "S4");

        int[] coins5 = { 21, 17, 11, 10, 1 };
        findSmallestCounterExample(coins5, "S5");
    }

    private static void findSmallestCounterExample(int[] coins, String label) {
        int smallestCounterExample = MAX_VALUE;

        // Construct possible smallest counter examples
        for (int i = 1; i <= coins.length; i++) {
            int target = coins[i - 1] - 1;
            int[] coinsUsed = coinChange(coins, target);

            for (int j = i; j < coins.length; j++) {
                int result = checkSmallestCounterExample(coins, coinsUsed, j);
                smallestCounterExample = Math.min(smallestCounterExample, result);
            }
        }

        System.out.print(label + ": ");
        if (smallestCounterExample == MAX_VALUE) {
            System.out.println("Greedy algorithm works!");
        } else {
            System.out.printf("Greedy algorithm does not work. Smallest counter example: %d\n", smallestCounterExample);
        }
    }

    private static int[] coinChange(int[] coins, int target) {
        int[] coinsUsed = new int[coins.length];

        for (int i = 0; i < coins.length; i++) {
            if (target >= coins[i]) {
                coinsUsed[i] = target / coins[i];
                target %= coins[i];
            }
        }
        return coinsUsed;
    }

    private static int checkSmallestCounterExample(int[] coins, int[] coinsUsed, int jIndex) {
        int[] coinsToUse = new int[coinsUsed.length];
        System.arraycopy(coinsUsed, 0, coinsToUse, 0, jIndex);
        coinsToUse[jIndex] = coinsUsed[jIndex] + 1;

        int value = getValueFromCoins(coins, coinsToUse);
        int[] coinsUsed2 = coinChange(coins, value);

        if (countCoins(coinsToUse) < countCoins(coinsUsed2)) {
            return value;
        }
        return MAX_VALUE;
    }

    private static int getValueFromCoins(int[] coins, int[] coinsUsed) {
        int value = 0;
        for (int i = 0; i < coinsUsed.length; i++) {
            value += coinsUsed[i] * coins[i];
        }
        return value;
    }

    private static int countCoins(int[] coinsUsed) {
        int coins = 0;
        for (int quantity : coinsUsed) {
            coins += quantity;
        }
        return coins;
    }

    // Alternative way of testing the greedy algorithm
    private static void findSmallestCounterExample2(int[] coins, String label) {
        int smallestCounterExample = -1;

        for (int target = 1; target <= 1000; target++) {
            int coinsUsedDP = coinChangeDP(coins, target);
            int coinsUsedGreedy = coinChangeGreedy(coins, target);

            if (coinsUsedDP != coinsUsedGreedy) {
                smallestCounterExample = target;
                break;
            }
        }

        System.out.print(label + ": ");
        if (smallestCounterExample == -1) {
            System.out.println("Greedy algorithm works!");
        } else {
            System.out.printf("Greedy algorithm does not work. Smallest counter example: %d\n", smallestCounterExample);
        }
    }

    private static int coinChangeDP(int[] coins, int target) {
        int[] dp = new int[target + 1];

        Arrays.fill(dp, MAX_VALUE);
        for (int coin : coins) {
            if (coin < dp.length) {
                dp[coin] = 1;
            }
        }

        for (int coin : coins) {
            for (int j = coin; j <= target && j < dp.length; j++) {
                dp[j] = Math.min(dp[j], dp[j - coin] + 1);
            }
        }

        if (dp[target] == MAX_VALUE) {
            return -1;
        }
        return dp[target];
    }

    private static int coinChangeGreedy(int[] coins, int target) {
        int coinsUsed = 0;

        for (int coin : coins) {
            if (target >= coin) {
                coinsUsed += target / coin;
                target %= coin;
            }
        }
        return coinsUsed;
    }
}
