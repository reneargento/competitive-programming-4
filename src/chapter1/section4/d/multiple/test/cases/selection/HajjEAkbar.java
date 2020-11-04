package chapter1.section4.d.multiple.test.cases.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class HajjEAkbar {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int test = 1;

        while (scanner.hasNext()) {
            String ritual = scanner.next();
            if (ritual.equals("*")) {
                break;
            }

            System.out.print("Case " + test + ": ");
            if (ritual.equals("Hajj")) {
                System.out.println("Hajj-e-Akbar");
            } else {
                System.out.println("Hajj-e-Asghar");
            }
            test++;
        }
    }

}
