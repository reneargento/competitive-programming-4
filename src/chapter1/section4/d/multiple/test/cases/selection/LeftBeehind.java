package chapter1.section4.d.multiple.test.cases.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class LeftBeehind {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            int sweetJars = scanner.nextInt();
            int sourJars = scanner.nextInt();

            if (sweetJars == 0 && sourJars == 0) {
                break;
            }

            if (sweetJars + sourJars == 13) {
                System.out.println("Never speak again.");
            } else if (sweetJars > sourJars) {
                System.out.println("To the convention.");
            } else if (sweetJars < sourJars) {
                System.out.println("Left beehind.");
            } else {
                System.out.println("Undecided.");
            }
        }
    }

}
