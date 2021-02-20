package chapter2.section2.d.twod.array.manipulation.harder;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 09/02/21.
 */
public class MattysBlocks {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int tableSize = scanner.nextInt();
            Map<Integer, Integer> frontHeights = new HashMap<>();
            Map<Integer, Integer> sideHeights = new HashMap<>();

            int[] blocksOnVerticalColumns = new int[8];
            int[] blocksOnHorizontalColumns = new int[8];

            for (int i = 0; i < tableSize; i++) {
                int blocks = scanner.nextInt();
                int frequency = frontHeights.getOrDefault(blocks, 0);
                frontHeights.put(blocks, frequency + 1);

                for (int j = 0; j < blocks; j++) {
                    blocksOnVerticalColumns[j]++;
                }
            }
            for (int i = 0; i < tableSize; i++) {
                int blocks = scanner.nextInt();
                int frequency = sideHeights.getOrDefault(blocks, 0);
                sideHeights.put(blocks, frequency + 1);

                for (int j = 0; j < blocks; j++) {
                    blocksOnHorizontalColumns[j]++;
                }
            }

            int minimalBlocks = computeMinimumBlocks(frontHeights, sideHeights);
            int maximumBlocks = computeMaximumBlocks(blocksOnVerticalColumns, blocksOnHorizontalColumns);
            int extraBlocks = maximumBlocks - minimalBlocks;
            System.out.printf("Matty needs at least %d blocks, and can add at most %d extra blocks.\n",
                    minimalBlocks, extraBlocks);
        }
    }

    private static int computeMinimumBlocks(Map<Integer, Integer> frontHeights, Map<Integer, Integer> sideHeights) {
        int minimalBlocks = 0;

        for (int frontHeight: frontHeights.keySet()) {
            int blocksOnFront = frontHeights.get(frontHeight);
            if (!sideHeights.containsKey(frontHeight)) {
                minimalBlocks += blocksOnFront * frontHeight;
            } else {
                minimalBlocks += Math.max(blocksOnFront, sideHeights.get(frontHeight)) * frontHeight;
            }
        }

        for (int sideHeight: sideHeights.keySet()) {
            if (!frontHeights.containsKey(sideHeight)) {
                minimalBlocks += sideHeights.get(sideHeight) * sideHeight;
            }
        }
        return minimalBlocks;
    }

    private static int computeMaximumBlocks(int[] blocksOnVerticalColumns, int[] blocksOnHorizontalColumns) {
        int maximumBlocks = 0;
        for (int i = 0; i < blocksOnVerticalColumns.length; i++) {
            maximumBlocks += blocksOnVerticalColumns[i] * blocksOnHorizontalColumns[i];
        }
        return maximumBlocks;
    }
}
