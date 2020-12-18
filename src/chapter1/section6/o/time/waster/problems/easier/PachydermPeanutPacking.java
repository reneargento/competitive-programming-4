package chapter1.section6.o.time.waster.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 17/12/20.
 */
public class PachydermPeanutPacking {

    private static class Box {
        double x1, x2;
        double y1, y2;
        String size;

        public Box(double x1, double y1, double x2, double y2, String size) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.size = size;
        }

        private boolean isInside(Peanut peanut) {
            return x1 <= peanut.x && peanut.x <= x2 && y1 <= peanut.y && peanut.y <= y2;
        }
    }

    private static class Peanut {
        double x;
        double y;
        String size;

        public Peanut(double x, double y, String size) {
            this.x = x;
            this.y = y;
            this.size = size;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int boxesCount = scanner.nextInt();
        int test = 1;

        while (boxesCount != 0) {
            if (test > 1) {
                System.out.println();
            }

            Box[] boxes = new Box[boxesCount];
            for (int i = 0; i < boxes.length; i++) {
                boxes[i] = new Box(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble(),
                        scanner.nextDouble(), scanner.next());
            }

            int peanutCount = scanner.nextInt();
            for (int i = 0; i < peanutCount; i++) {
                Peanut peanut = new Peanut(scanner.nextDouble(), scanner.nextDouble(), scanner.next());
                inspectPeanut(boxes, peanut);
            }
            test++;
            boxesCount = scanner.nextInt();
        }
    }

    private static void inspectPeanut(Box[] boxes, Peanut peanut) {
        String peanutSize = peanut.size;
        System.out.print(peanutSize + " ");

        for (Box box : boxes) {
            if (box.isInside(peanut)) {
                if (peanutSize.equals(box.size)) {
                    System.out.println("correct");
                } else {
                    System.out.println(box.size);
                }
                return;
            }
        }
        System.out.println("floor");
    }
}
