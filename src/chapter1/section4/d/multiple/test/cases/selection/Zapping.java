package chapter1.section4.d.multiple.test.cases.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class Zapping {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            int current = scanner.nextInt();
            int target = scanner.nextInt();

            if (current == -1 && target == -1) {
                break;
            }

            int minChannel = Math.min(current, target);
            int maxChannel = Math.max(current, target);

            int presses1 = maxChannel - minChannel;
            int presses2 = minChannel + (100 - maxChannel);
            System.out.println(Math.min(presses1, presses2));
        }
    }

}
