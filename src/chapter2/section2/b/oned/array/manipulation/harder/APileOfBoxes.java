package chapter2.section2.b.oned.array.manipulation.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 28/01/21.
 */
public class APileOfBoxes {

    private static class Box {
        int height;
        int usedCapacity;
        int level;

        public Box(int height) {
            this.height = height;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int boxesNumber = scanner.nextInt();
            Box[] boxes = new Box[boxesNumber];
            int totalHeight = 0;
            int currentLevel = 0;

            for (int i = 0; i < boxesNumber; i++) {
                int height = scanner.nextInt();
                boxes[i] = new Box(height);

                Box parentBox = null;
                for (int b = 0; b < i; b++) {
                    Box currentBox = boxes[b];

                    if (currentBox.usedCapacity + height <= currentBox.height) {
                        if (parentBox == null
                                || currentBox.level < parentBox.level
                                || (currentBox.level == parentBox.level && currentBox.height < parentBox.height)) {
                            parentBox = currentBox;
                        }
                    }
                }

                if (parentBox != null) {
                    parentBox.usedCapacity += height;
                    boxes[i].level = parentBox.level;
                } else {
                    totalHeight += height;
                    boxes[i].level = currentLevel;
                    currentLevel++;
                }
            }
            System.out.println(totalHeight);
        }
    }
}
