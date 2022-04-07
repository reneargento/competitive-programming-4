package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Rene Argento on 06/04/22.
 */
// Based on https://ioi2010.org/competitiontask/day1/quality/index.html
// Task: https://ioi2010.org/Tasks/Day1/Quality_of_Living.shtml
public class QualityOfLiving {

    private static final String FILE_INPUT_PATH = "/Users/rene/Desktop/data/Subtask5-data/grader.in.1-5";

    public static void main(String[] args) throws IOException {
        testIO();

//        int[][] blocks1 = {
//                { 5, 11, 12, 16, 25 },
//                { 17, 18, 2, 7, 10 },
//                { 4, 23, 20, 3, 1 },
//                { 24, 21, 19, 14, 9 },
//                { 6, 22, 8, 13, 15 }
//        };
//        int bestMedianQualityRank1 = rectangle(blocks1.length, blocks1[0].length, 3, 3, blocks1);
//        System.out.println("Best median: " + bestMedianQualityRank1 + " Expected: 9");
//
//        int[][] blocks2 = {
//                { 6,  1,  2, 11,  7,  5 },
//                { 9,  3,  4, 10, 12,  8 }
//        };
//        int bestMedianQualityRank2 = rectangle(blocks2.length, blocks2[0].length, 1, 5, blocks2);
//        System.out.println("Best median: " + bestMedianQualityRank2 + " Expected: 5");
    }

    private static void testIO() {
        List<String> lines = readFileInput();
        String[] data = lines.get(0).split(" ");
        int rows = Integer.parseInt(data[0]);
        int columns = Integer.parseInt(data[1]);
        int height = Integer.parseInt(data[2]);
        int width = Integer.parseInt(data[3]);
        int[][] blocks = new int[rows][columns];

        for(int i = 1; i < lines.size(); i++) {
            String[] row = lines.get(i).split(" ");
            for (int column = 0; column < row.length; column++) {
                blocks[i - 1][column] = Integer.parseInt(row[column]);
            }
        }
        int bestMedianQuality = rectangle(blocks.length, blocks[0].length, height, width, blocks);
        System.out.println(bestMedianQuality);
    }

    private static int rectangle(int rows, int columns, int height, int width, int[][] blocks) {
        return getBestMedianQuality(blocks, height, width);
    }

    private static int getBestMedianQuality(int[][] blocks, int height, int width) {
        int low = 1;
        int high = blocks.length * blocks[0].length;
        int bestMedianQuality = Integer.MAX_VALUE;

        while (low <= high) {
            int middle = low + (high - low) / 2;

            boolean existsMedianEqualOrLower = existsMedianEqualOrLower(blocks, height, width, middle);
            if (existsMedianEqualOrLower) {
                bestMedianQuality = middle;
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }
        return bestMedianQuality;
    }

    private static boolean existsMedianEqualOrLower(int[][] blocks, int height, int width, int value) {
        int[][] adjustedValues = computeAdjustedValues(blocks, value);
        int[][] cumulativeSum = computeCumulativeSum(adjustedValues);

        for (int row = height - 1; row < blocks.length; row++) {
            for (int column = width - 1; column < blocks[0].length; column++) {
                int sum = cumulativeSum[row][column];
                if (row - height >= 0) {
                    sum -= cumulativeSum[row - height][column];
                }
                if (column - width >= 0) {
                    sum -= cumulativeSum[row][column - width];
                }
                if (row - height >= 0 && column - width >= 0) {
                    sum += cumulativeSum[row - height][column - width];
                }
                if (sum < 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int[][] computeAdjustedValues(int[][] blocks, int value) {
        int[][] adjustedValues = new int[blocks.length][blocks[0].length];

        for (int row = 0; row < blocks.length; row++) {
            for (int column = 0; column < blocks[0].length; column++) {
                if (blocks[row][column] <= value) {
                    adjustedValues[row][column] = -1;
                } else {
                    adjustedValues[row][column] = 1;
                }
            }
        }
        return adjustedValues;
    }

    private static int[][] computeCumulativeSum(int[][] blocks) {
        int[][] cumulativeSum = new int[blocks.length][blocks[0].length];
        cumulativeSum[0][0] = blocks[0][0];

        for (int row = 1; row < blocks.length; row++) {
            cumulativeSum[row][0] += cumulativeSum[row - 1][0] + blocks[row][0];
        }
        for (int column = 1; column < blocks[0].length; column++) {
            cumulativeSum[0][column] += cumulativeSum[0][column - 1] + blocks[0][column];
        }

        for (int row = 1; row < blocks.length; row++) {
            for (int column = 1; column < blocks[0].length; column++) {
                cumulativeSum[row][column] = cumulativeSum[row - 1][column] + cumulativeSum[row][column - 1] -
                        cumulativeSum[row - 1][column - 1] + blocks[row][column];
            }
        }
        return cumulativeSum;
    }

    private static List<String> readFileInput() {
        Path path = Paths.get(FILE_INPUT_PATH);
        List<String> lines = null;

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
