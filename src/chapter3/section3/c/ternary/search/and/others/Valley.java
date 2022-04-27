package chapter3.section3.c.ternary.search.and.others;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Rene Argento on 26/04/22.
 */
// Problem located at https://ioi.te.lv/locations/ioi11/contest/day0_tasks.shtml
public class Valley {

    private static class Input {
        int[] heights;
        Set<Integer> producedValues;
        int target;

        public Input(int[] heights, Set<Integer> producedValues, int target) {
            this.heights = heights;
            this.producedValues = producedValues;
            this.target = target;
        }
    }

    private static int countOfQueries;

    public static void main(String[] args) throws IOException {
        boolean accepted = true;

        for (int i = 0; i < 100; i++) {
            Input input = generateRandomInput();
            int target = input.target;
            countOfQueries = 0;

            int sensorExists = find(input.heights, target);
            if ((sensorExists == 1 && !input.producedValues.contains(target))
                    || (sensorExists == 0 && input.producedValues.contains(target))) {
                accepted = false;
                break;
            }
        }

        if (accepted) {
            System.out.println("Accepted");
        } else {
            System.out.println("Wrong answer");
        }
    }

    private static int find(int[] heights, int target) {
        int low = 0;
        int high = heights.length - 1;

        while (low < high) {
            double thirdLength = (high - low) / 3.0;
            int section1 = (int) Math.round(low + thirdLength);
            int section2 = (int) Math.round(high - thirdLength);

            if (section1 == section2) {
                break;
            }

            int height1 = query(heights, section1);
            int height2 = query(heights, section2);

            if (height1 == target || height2 == target) {
                return 1;
            }

            if (height1 > height2) {
                low = section1;
            } else if (height2 > height1) {
                high = section2;
            } else {
                break;
            }
        }

        int lowestPoint = (low + high) / 2;
        return binarySearch(heights, 0, lowestPoint, target, true) +
                binarySearch(heights, lowestPoint + 1, heights.length - 1, target, false);
    }

    private static int query(int[] heights, int index) {
        countOfQueries++;
        if (countOfQueries > 50) {
            throw new IllegalStateException("Cannot call query more than 50 times");
        }
        return heights[index];
    }

    private static int binarySearch(int[] heights, int low, int high, int target, boolean isDescending) {
        while (low <= high) {
            int middle = low + (high - low) / 2;
            int height = query(heights, middle);
            if (height == target) {
                return 1;
            }

            if (isDescending) {
                if (height < target) {
                    high = middle - 1;
                } else {
                    low = middle + 1;
                }
            } else {
                if (height < target) {
                    low = middle + 1;
                } else {
                    high = middle - 1;
                }
            }
        }
        return 0;
    }

    private static Input generateRandomInput() {
        Random random = new Random();
        int[] randomInput = new int[1000];
        Set<Integer> producedValues = new HashSet<>();

        for (int i = 0; i < randomInput.length; i++) {
            int value = random.nextInt(1000000001);
            while (producedValues.contains(value)) {
                value = random.nextInt(1000000001);
            }
            randomInput[i] = value;
            producedValues.add(value);
        }

        int randomK = random.nextInt(randomInput.length);
        Arrays.sort(randomInput);

        int[] heights = new int[randomInput.length];
        heights[randomK] = randomInput[0];

        int lastIndex = randomInput.length - 1;
        int heightsIndex;
        for (heightsIndex = 0; heightsIndex < randomK; heightsIndex++, lastIndex--) {
            heights[heightsIndex] = randomInput[lastIndex];
        }

        heightsIndex = randomK + 1;
        for (int i = 1; i <= lastIndex; i++) {
            heights[heightsIndex] = randomInput[i];
            heightsIndex++;
        }

        int target = random.nextInt(1000000001);
        return new Input(heights, producedValues, target);
    }
}
