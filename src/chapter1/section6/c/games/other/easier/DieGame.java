package chapter1.section6.c.games.other.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 19/10/20.
 */
public class DieGame {

    private static class Die {
        int top;
        int north;
        int bottom;
        int south;
        int left;
        int right;

        public Die(int top, int north, int bottom, int south, int left, int right) {
            this.top = top;
            this.north = north;
            this.bottom = bottom;
            this.south = south;
            this.left = left;
            this.right = right;
        }

        private void rotateNorth() {
            int newTop = south;
            int newNorth = top;
            int newBottom = north;
            int newSouth = bottom;

            top = newTop;
            north = newNorth;
            bottom = newBottom;
            south = newSouth;
        }

        private void rotateSouth() {
            int newTop = north;
            int newNorth = bottom;
            int newBottom = south;
            int newSouth = top;

            top = newTop;
            north = newNorth;
            bottom = newBottom;
            south = newSouth;
        }

        private void rotateEast() {
            int newLeft = bottom;
            int newTop = left;
            int newRight = top;
            int newBottom = right;

            left = newLeft;
            top = newTop;
            right = newRight;
            bottom = newBottom;
        }

        private void rotateWest() {
            int newLeft = top;
            int newTop = right;
            int newRight = bottom;
            int newBottom = left;

            left = newLeft;
            top = newTop;
            right = newRight;
            bottom = newBottom;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int commands = scanner.nextInt();

        while (commands != 0) {
            Die die = new Die(1, 2, 6, 5, 3, 4);

            for (int i = 0; i < commands; i++) {
                roll(die, scanner.next());
            }
            System.out.println(die.top);
            commands = scanner.nextInt();
        }
    }

    private static void roll(Die die, String command) {
        switch (command) {
            case "north": die.rotateNorth(); break;
            case "south": die.rotateSouth(); break;
            case "east": die.rotateEast(); break;
            case "west": die.rotateWest(); break;
        }
    }
}
