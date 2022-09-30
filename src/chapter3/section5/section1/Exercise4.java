package chapter3.section5.section1;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by Rene Argento on 03/09/22.
 */
public class Exercise4 {

    public static void main(String[] args) throws IOException {
        int budget = 20;
        int[][] prices = {
                { 6, 4, 8 },
                { 5, 10 },
                { 1, 5, 3, 5 }
        };

        int[][] dp = new int[prices.length][budget + 1];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }
        int maximumMoneyNecessary = dp(budget, prices, dp, 0, budget);
        if (maximumMoneyNecessary < 0) {
            System.out.println("no solution");
        } else {
            System.out.printf("Solution(s) for total %d\n", maximumMoneyNecessary);
            printDP(budget, prices, dp, 0, budget, new LinkedList<>());
        }
    }

    // Top-down
    private static int dp(int totalBudget, int[][] prices, int[][] dp, int garment, int budget) {
        if (budget < 0) {
            return -1;
        }
        if (garment == prices.length) {
            return totalBudget - budget;
        }

        if (dp[garment][budget] != -1) {
            return dp[garment][budget];
        }

        int answer = -1;
        for (int model = 0; model < prices[garment].length; model++) {
            int remainingBudget = budget - prices[garment][model];
            int resultSelectingModel = dp(totalBudget, prices, dp, garment + 1, remainingBudget);
            answer = Math.max(answer, resultSelectingModel);
        }
        dp[garment][budget] = answer;
        return answer;
    }

    private static void printDP(int totalBudget, int[][] prices, int[][] dp, int garment, int budget,
                                LinkedList<Integer> result) {
        if (garment == prices.length || budget < 0) {
            return;
        }

        for (int model = 0; model < prices[garment].length; model++) {
            int remainingBudget = budget - prices[garment][model];
            int resultSelectingModel = dp(totalBudget, prices, dp, garment + 1, remainingBudget);
            if (resultSelectingModel == dp[garment][budget]) {
                result.add(prices[garment][model]);

                if (garment == prices.length - 1) {
                    System.out.print(result.get(0));
                    for (int i = 1; i < result.size(); i++) {
                        System.out.print(" " + result.get(i));
                    }
                    System.out.println();
                }
                printDP(totalBudget, prices, dp, garment + 1, remainingBudget, result);
                result.removeLast();
            }
        }
    }
}
