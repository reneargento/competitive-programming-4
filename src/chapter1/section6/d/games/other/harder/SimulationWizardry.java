package chapter1.section6.d.games.other.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 24/10/20.
 */
public class SimulationWizardry {

    private static class Bumper {
        int value;
        int cost;

        public Bumper(int value, int cost) {
            this.value = value;
            this.cost = cost;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int columns = scanner.nextInt();
        int rows = scanner.nextInt();
        int wallCost = scanner.nextInt();
        int bumpers = scanner.nextInt();

        Bumper[][] pinball = new Bumper[rows + 1][columns + 1];

        for (int i = 0; i < bumpers; i++) {
            int column = scanner.nextInt();
            int row = scanner.nextInt();
            int value = scanner.nextInt();
            int cost = scanner.nextInt();

            Bumper bumper = new Bumper(value, cost);
            pinball[row][column] = bumper;
        }

        int totalPoints = 0;

        while (scanner.hasNext()) {
            int column = scanner.nextInt();
            int row = scanner.nextInt();
            int direction = scanner.nextInt();
            int lifetime = scanner.nextInt();

            int points = getBallPoints(pinball, row, column, direction, lifetime, wallCost);
            System.out.println(points);
            totalPoints += points;
        }
        System.out.println(totalPoints);
    }

    private static int getBallPoints(Bumper[][] pinball, int row, int column, int direction, int lifetime, int wallCost) {
        int points = 0;

        while (lifetime > 0) {
            int[] nextPosition = getNextPosition(row, column, direction);
            lifetime--;

            if (isNotWall(pinball, nextPosition[0], nextPosition[1])
                    && pinball[nextPosition[0]][nextPosition[1]] == null) {
                row = nextPosition[0];
                column = nextPosition[1];
            } else {
                direction--;
                if (direction < 0) {
                    direction = 3;
                }

                int nextRow = nextPosition[0];
                int nextColumn = nextPosition[1];
                if (isNotWall(pinball, nextRow, nextColumn)
                        || (isInGrid(pinball, nextRow, nextColumn)) && pinball[nextRow][nextColumn] != null) {
                    Bumper bumper = pinball[nextRow][nextColumn];

                    if (lifetime > 0) {
                        points += bumper.value;
                        lifetime -= bumper.cost;
                    }
                } else if (lifetime > 0) {
                    lifetime -= wallCost;
                }
            }
        }
        return points;
    }

    private static int[] getNextPosition(int row, int column, int direction) {
        switch (direction) {
            case 0: column++; break;
            case 1: row++; break;
            case 2: column--; break;
            case 3: row--; break;
        }
        return new int[]{row, column};
    }

    private static boolean isNotWall(Bumper[][] pinball, int row, int column) {
        return row >= 2 && row < pinball.length - 1 && column >= 2 && column < pinball[0].length - 1;
    }

    private static boolean isInGrid(Bumper[][] pinball, int row, int column) {
        return row >= 1 && row < pinball.length && column >= 1 && column < pinball[0].length;
    }

}
