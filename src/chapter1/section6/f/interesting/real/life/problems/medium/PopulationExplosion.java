package chapter1.section6.f.interesting.real.life.problems.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 05/11/20.
 */
public class PopulationExplosion {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();
        int[] neighborRows = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] neighborColumns = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int t = 0; t < tests; t++) {
            int years = scanner.nextInt();
            scanner.nextLine();
            String location = scanner.nextLine();
            char[][] populationMap = new char[20][20];

            while (!location.equals("")) {
                String[] locationData = location.split(" ");
                int row = populationMap.length - 1 - (Integer.parseInt(locationData[0]) - 1);
                int column = Integer.parseInt(locationData[1]) - 1;
                populationMap[row][column] = 'O';

                if (scanner.hasNext()) {
                    location = scanner.nextLine();
                } else {
                    break;
                }
            }

            if (t > 0) {
                System.out.println();
            }
            System.out.println("********************");
            for (int i = 0; i < years; i++) {
                printMap(populationMap);
                populationMap = passYear(populationMap, neighborRows, neighborColumns);
            }
        }
    }

    private static void printMap(char[][] populationMap) {
        for (int r = populationMap.length - 1; r >= 0; r--) {
            for (int c = 0; c < populationMap[0].length; c++) {
                if (populationMap[r][c] == 'O') {
                    System.out.print("O");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println("********************");
    }

    private static char[][] passYear(char[][] populationMap, int[] neighborRows, int[] neighborColumns) {
        char[][] newMap = new char[populationMap.length][populationMap[0].length];

        for (int r = 0; r < populationMap.length; r++) {
            for (int c = 0; c < populationMap[0].length; c++) {
                int neighbors = 0;

                for (int i = 0; i < neighborRows.length; i++) {
                    int neighborRow = r + neighborRows[i];
                    int neighborColumn = c + neighborColumns[i];

                    if (isValid(populationMap, neighborRow, neighborColumn)) {
                        if (populationMap[neighborRow][neighborColumn] == 'O') {
                            neighbors++;
                        }
                    }
                }

                if (populationMap[r][c] == 'O') {
                    if (neighbors == 2 || neighbors == 3) {
                        newMap[r][c] = 'O';
                    } else {
                        newMap[r][c] = ' ';
                    }
                } else {
                    if (neighbors == 3) {
                        newMap[r][c] = 'O';
                    } else {
                        newMap[r][c] = ' ';
                    }
                }
            }
        }
        return newMap;
    }

    private static boolean isValid(char[][] populationMap, int row, int column) {
        return row >= 0 && row < populationMap.length && column >= 0 && column < populationMap[0].length;
    }
}
