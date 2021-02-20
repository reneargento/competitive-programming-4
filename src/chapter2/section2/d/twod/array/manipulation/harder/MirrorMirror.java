package chapter2.section2.d.twod.array.manipulation.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 06/02/21.
 */
public class MirrorMirror {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int patternId = 1;

        while (scanner.hasNext()) {
            int dimension = scanner.nextInt();
            scanner.nextLine();
            char[][] original = new char[dimension][dimension];
            char[][] transformed = new char[dimension][dimension];

            for (int row = 0; row < dimension; row++) {
                String[] values = scanner.nextLine().split(" ");
                original[row] = values[0].toCharArray();
                transformed[row] = values[1].toCharArray();
            }

            String transformation = checkTransformation(original, transformed);
            System.out.printf("Pattern %d was %s.\n", patternId, transformation);
            patternId++;
        }
    }

    private static String checkTransformation(char[][] original, char[][] transformed) {
        if (isEqual(original, transformed)) {
            return "preserved";
        }

        String rotation = checkRotations(original, transformed);
        if (rotation != null) {
            return rotation;
        }

        char[][] reflected = reflect(original);
        if (isEqual(transformed, reflected)) {
            return "reflected vertically";
        }

        String combination = checkRotations(reflected, transformed);
        if (combination != null) {
            return "reflected vertically and " + combination;
        }
        return "improperly transformed";
    }

    private static String checkRotations(char[][] pattern, char[][] transformed) {
        char[][] rotated90Degrees = rotate90Degrees(pattern);
        if (isEqual(transformed, rotated90Degrees)) {
            return "rotated 90 degrees";
        }
        char[][] rotated180Degrees = rotate90Degrees(rotated90Degrees);
        if (isEqual(transformed, rotated180Degrees)) {
            return "rotated 180 degrees";
        }
        char[][] rotated270Degrees = rotate90Degrees(rotated180Degrees);
        if (isEqual(transformed, rotated270Degrees)) {
            return "rotated 270 degrees";
        }
        return null;
    }

    private static boolean isEqual(char[][] pattern1, char[][] pattern2) {
        for (int row = 0; row < pattern1.length; row++) {
            for (int column = 0; column < pattern1[0].length; column++) {
                if (pattern1[row][column] != pattern2[row][column]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static char[][] rotate90Degrees(char[][] pattern) {
        char[][] rotated = new char[pattern.length][pattern[0].length];

        for (int row = 0; row < pattern.length; row++) {
            for (int column = 0; column < pattern[0].length; column++) {
                int rotatedRow = column;
                int rotatedColumn = pattern[0].length - 1 - row;
                rotated[rotatedRow][rotatedColumn] = pattern[row][column];
            }
        }
        return rotated;
    }

    private static char[][] reflect(char[][] pattern) {
        char[][] reflected = new char[pattern.length][pattern[0].length];

        for (int row = 0; row <= pattern.length / 2; row++) {
            for (int column = 0; column < pattern[0].length; column++) {
                int reflectedRow = pattern.length - 1 - row;
                reflected[reflectedRow][column] = pattern[row][column];
                reflected[row][column] = pattern[reflectedRow][column];
            }
        }
        return reflected;
    }
}
