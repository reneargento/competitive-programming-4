package chapter3.section5.section1;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/09/22.
 */
// Output: 25
// It is possible to take the third model of garment 0 (cost 8), the first model of garment 1 (cost 10) and the
// first model of garment 2 (cost 7) for a total of 25.
public class Exercise1 {

    public static void main(String[] args) throws IOException {
        int budget = 25;
        int[][] prices = {
                { 6, 4, 8 },
                { 10, 6 },
                { 7, 3, 1, 5 }
        };

        int maximumMoneyNecessary = computeMaxMoney(budget, prices);
        if (maximumMoneyNecessary < 0) {
            System.out.println("no solution");
        } else {
            System.out.println(maximumMoneyNecessary);
        }
    }

    private static int computeMaxMoney(int budget, int[][] prices) {
        boolean[][] dp = buildDpTable(budget, prices);
        int remainingBudget = -1;

        for (int i = 0; i < dp[dp.length - 1].length; i++) {
            if (dp[dp.length - 1][i]) {
                remainingBudget = i;
                break;
            }
        }

        if (remainingBudget < 0) {
            return -1;
        } else {
            return budget - remainingBudget;
        }
    }

    // Bottom-up
    private static boolean[][] buildDpTable(int budget, int[][] prices) {
        boolean[][] dp = new boolean[prices.length][budget + 1];
        // Base cases
        for (int m = 0; m < prices[0].length; m++) {
            int remainingBudget = budget - prices[0][m];
            if (remainingBudget >= 0) {
                dp[0][remainingBudget] = true;
            }
        }

        for (int g = 1; g < prices.length; g++) {
            for (int b = 0; b < dp[g].length; b++) {
                if (dp[g - 1][b]) {
                    for (int m = 0; m < prices[g].length; m++) {
                        int remainingBudget = b - prices[g][m];
                        if (remainingBudget >= 0) {
                            dp[g][remainingBudget] = true;
                        }
                    }
                }
            }
        }
        return dp;
    }
}
