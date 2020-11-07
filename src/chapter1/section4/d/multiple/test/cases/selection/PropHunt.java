package chapter1.section4.d.multiple.test.cases.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class PropHunt {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            int props = scanner.nextInt();
            int hunters = scanner.nextInt();
            int objects = scanner.nextInt();

            if (hunters > objects - props) {
                System.out.println("Hunters win!");
            } else {
                System.out.println("Props win!");
            }
        }
    }

}
