package chapter3.section5.section3;

/**
 * Created by Rene Argento on 03/10/22.
 */
public class Exercise1CoinChange {

    public static void main(String[] args) {
        int[] coins1 = { 1, 2, 3 };
        int targetExchange1 = 4;
        long coinChangePossibilities1 = calculateCoinChangePossibilities(coins1, targetExchange1);
        System.out.println("Coin change possibilities 1: " + coinChangePossibilities1 + " Expected: 4");

        int[] coins2 = { 2, 5, 3, 6 };
        int targetExchange2 = 10;
        long coinChangePossibilities2 = calculateCoinChangePossibilities(coins2, targetExchange2);
        System.out.println("Coin change possibilities 2: " + coinChangePossibilities2 + " Expected: 5");
    }

    private static long calculateCoinChangePossibilities(int[] coins, int targetExchange) {
        int[] dp = new int[targetExchange + 1];

        // Base case - for 0 exchange, there is 1 solution (no coins)
        dp[0] = 1;

        for (int coinValue : coins) {
            for (int currentSum = coinValue; currentSum <= targetExchange; currentSum++) {
                dp[currentSum] += dp[currentSum - coinValue];
            }
        }
        return dp[targetExchange];
    }
}
