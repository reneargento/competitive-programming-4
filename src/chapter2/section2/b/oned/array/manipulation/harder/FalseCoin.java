package chapter2.section2.b.oned.array.manipulation.harder;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Rene Argento on 28/01/21.
 */
public class FalseCoin {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                System.out.println();
            }

            int coins = scanner.nextInt();
            int weightings = scanner.nextInt();
            Set<Integer> realCoins = new HashSet<>();
            Set<Integer>[] lowerThan = new HashSet[coins + 1];
            Set<Integer>[] higherThan = new HashSet[coins + 1];

            for (int i = 1; i <= coins; i++) {
                lowerThan[i] = new HashSet<>();
                higherThan[i] = new HashSet<>();
            }

            for (int w = 0; w < weightings; w++) {
                int coinsOnEachSide = scanner.nextInt();
                Set<Integer> leftCoins = new HashSet<>();
                Set<Integer> rightCoins = new HashSet<>();

                for (int c = 0; c < coinsOnEachSide; c++) {
                    leftCoins.add(scanner.nextInt());
                }
                for (int c = 0; c < coinsOnEachSide; c++) {
                    rightCoins.add(scanner.nextInt());
                }

                char result = scanner.next().charAt(0);
                if (result == '=') {
                    realCoins.addAll(leftCoins);
                    realCoins.addAll(rightCoins);
                } else if (result == '<') {
                    for (int leftCoin : leftCoins) {
                        lowerThan[leftCoin].addAll(rightCoins);
                    }
                    for (int rightCoin : rightCoins) {
                        higherThan[rightCoin].addAll(leftCoins);
                    }
                } else {
                    for (int leftCoin : leftCoins) {
                        higherThan[leftCoin].addAll(rightCoins);
                    }
                    for (int rightCoin : rightCoins) {
                        lowerThan[rightCoin].addAll(leftCoins);
                    }
                }
            }

            int coinLowerThanAllOthers = getCoinLowerOrHigherThanAllOthers(lowerThan);
            int coinHigherThanAllOthers = getCoinLowerOrHigherThanAllOthers(higherThan);
            if (coinLowerThanAllOthers != 0 && coinHigherThanAllOthers == 0) {
                System.out.println(coinLowerThanAllOthers);
            } else if (coinLowerThanAllOthers == 0 && coinHigherThanAllOthers != 0) {
                System.out.println(coinHigherThanAllOthers);
            } else if (realCoins.size() != coins - 1) {
                System.out.println("0");
            } else {
                int falseCoin = getFalseCoin(coins, realCoins);
                System.out.println(falseCoin);
            }
        }
    }

    private static int getCoinLowerOrHigherThanAllOthers(Set<Integer>[] comparisons) {
        int totalCoinsMinus1 = comparisons.length - 2;
        for (int coin = 1; coin < comparisons.length; coin++) {
            if (comparisons[coin].size() == totalCoinsMinus1) {
                return coin;
            }
        }
        return 0;
    }

    private static int getFalseCoin(int coins, Set<Integer> realCoins) {
        for (int i = 1; i <= coins; i++) {
            if (!realCoins.contains(i)) {
                return i;
            }
        }
        return 0;
    }
}
