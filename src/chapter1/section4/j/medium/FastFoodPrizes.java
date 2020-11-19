package chapter1.section4.j.medium;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rene Argento on 24/09/20.
 */
@SuppressWarnings("unchecked")
public class FastFoodPrizes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int numberOfPrizes = scanner.nextInt();
            int numberOfStickers = scanner.nextInt();

            List<Integer>[] prizes = new ArrayList[numberOfPrizes];
            for (int p = 0; p < numberOfPrizes; p++) {
                List<Integer> prizeList = new ArrayList<>();
                int stickersNeeded = scanner.nextInt();

                for (int s = 0; s < stickersNeeded + 1; s++) {
                    prizeList.add(scanner.nextInt());
                }
                prizes[p] = prizeList;
            }

            int[] stickers = new int[numberOfStickers];
            for (int s = 0; s < stickers.length; s++) {
                stickers[s] = scanner.nextInt();
            }

            long cashPrizes = 0;

            for (int p = 0; p < numberOfPrizes; p++) {
                int listSize = prizes[p].size();
                int cash = prizes[p].get(listSize - 1);
                int min = Integer.MAX_VALUE;

                for (int s = 0; s < listSize - 1; s++) {
                    int stickerNeeded = prizes[p].get(s);
                    int stickersAvailable = stickers[stickerNeeded - 1];
                    min = Math.min(min, stickersAvailable);
                }

                cashPrizes += min * cash;
            }
            System.out.println(cashPrizes);
        }
    }

}
