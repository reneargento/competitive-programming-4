package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rene Argento on 08/11/20.
 */
public class Barcodes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int regionsCount = scanner.nextInt();
        int caseNumber = 1;

        while (regionsCount != 0) {
            int[] regions = new int[regionsCount];
            for (int i = 0; i < regions.length; i++) {
                regions[i] = scanner.nextInt();
            }

            List<Integer> decodedValues = getDecodedValues(regions);

            if (decodedValues == null) {
                regions = invertRegions(regions);
                decodedValues = getDecodedValues(regions);
            }

            if (decodedValues == null) {
                System.out.printf("Case %d: bad code\n", caseNumber);
            } else if (!checkC(decodedValues)) {
                System.out.printf("Case %d: bad C\n", caseNumber);
            } else if (!checkK(decodedValues)) {
                System.out.printf("Case %d: bad K\n", caseNumber);
            } else {
                System.out.printf("Case %d: %s\n", caseNumber, getDecodedMessage(decodedValues));
            }
            caseNumber++;
            regionsCount = scanner.nextInt();
        }
    }

    private static List<Integer> getDecodedValues(int[] regions) {
        double threshold = getThreshold(regions);

        if (regions.length % 6 != 5
                || (regions.length + 1) / 6 < 5
                || !hasValidWidths(regions, threshold)
                || !isValidStartStop(regions, threshold)) {
            return null;
        }

        List<Integer> decodedValues = new ArrayList<>();

        if (!isNarrow(threshold, regions[5])) {
            return null;
        }

        int read = 0;
        StringBuilder currentDecode = new StringBuilder();

        for (int i = 6; i < regions.length - 5; i++) {
            if (isNarrow(threshold, regions[i])) {
                currentDecode.append('0');
            } else {
                currentDecode.append('1');
            }

            read++;
            if (read % 5 == 0) {
                int value = mapCodeToValue(currentDecode.toString());
                if (value == -1) {
                    return null;
                } else {
                    decodedValues.add(value);
                }
                currentDecode = new StringBuilder();

                i++;
                if (!isNarrow(threshold, regions[i])) {
                    return null;
                }
            }
        }

        if (currentDecode.length() > 0) {
            return null;
        }
        return decodedValues;
    }

    private static double getThreshold(int[] regions) {
        double min = Integer.MAX_VALUE;
        double max = 0;
        for (int region : regions) {
            min = Math.min(region, min);
            max = Math.max(region, max);
        }
        return (min + max) / 2.0;
    }

    private static boolean hasValidWidths(int[] regions, double threshold) {
        double min = Integer.MAX_VALUE;
        double max = 0;

        for (int region : regions) {
            if (region < threshold) {
                region *= 2;
            }
            min = Math.min(min, region);
            max = Math.max(max, region);
        }
        return min * 1.05 >= max * 0.95;
    }

    private static boolean isValidStartStop(int[] regions, double threshold) {
        if (regions.length < 10) {
            return false;
        }

        int[] startIndexes = {0, regions.length - 5};
        boolean[] checkWide = {false, false, true, true, false};
        boolean valid = true;

        for (int startIndex = 0; startIndex < 2; startIndex++) {
            int checkIndex = 0;
            for (int i = startIndexes[startIndex]; i < startIndexes[startIndex] + 5; i++) {
                if (checkWide[checkIndex++]) {
                    if (!isWide(threshold, regions[i])) {
                        valid = false;
                        break;
                    }
                } else {
                    if (!isNarrow(threshold, regions[i])) {
                        valid = false;
                        break;
                    }
                }
            }
            if (!valid) {
                break;
            }
        }
        return valid;
    }

    private static boolean isNarrow(double threshold, int value) {
        return value <= threshold;
    }

    private static boolean isWide(double threshold, int value) {
        return value > threshold;
    }

    private static int[] invertRegions(int[] regions) {
        int[] invertedRegions = new int[regions.length];
        int index = 0;

        for (int i = regions.length - 1; i >= 0; i--) {
            invertedRegions[index++] = regions[i];
        }
        return invertedRegions;
    }

    private static int mapCodeToValue(String code) {
        switch (code) {
            case "00001": return 0;
            case "10001": return 1;
            case "01001": return 2;
            case "11000": return 3;
            case "00101": return 4;
            case "10100": return 5;
            case "01100": return 6;
            case "00011": return 7;
            case "10010": return 8;
            case "10000": return 9;
            case "00100": return 10;
        }
        return -1;
    }

    private static boolean checkC(List<Integer> values) {
        long c = 0;
        int valuesSize = values.size() - 2;

        for (int i = 1; i <= valuesSize; i++) {
            c += ((valuesSize - i) % 10 + 1) * values.get(i - 1);
        }
        c %= 11;

        return c == values.get(values.size() - 2);
    }

    private static boolean checkK(List<Integer> values) {
        long k = 0;
        int valuesSize = values.size() - 2;

        for (int i = 1; i <= valuesSize + 1; i++) {
            k += ((valuesSize - i + 1) % 9 + 1) * values.get(i - 1);
        }
        k %= 11;

        return k == values.get(values.size() - 1);
    }

    private static String getDecodedMessage(List<Integer> values) {
        StringBuilder decodedMessage = new StringBuilder();
        for (int i = 0; i < values.size() - 2; i++) {
            if (values.get(i) <= 9) {
                decodedMessage.append(values.get(i));
            } else {
                decodedMessage.append("-");
            }
        }
        return decodedMessage.toString();
    }
}
