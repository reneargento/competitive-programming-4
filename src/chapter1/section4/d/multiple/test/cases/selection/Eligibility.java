package chapter1.section4.d.multiple.test.cases.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class Eligibility {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < tests; i++) {
            String[] information = scanner.nextLine().split(" ");
            String name = information[0];
            boolean studiesAfter2010OrLater = Integer.parseInt(information[1].substring(0, 4)) >= 2010;
            boolean born91OrLater = Integer.parseInt(information[2].substring(0, 4)) >= 1991;
            int courses = Integer.parseInt(information[3]);

            System.out.print(name + " ");
            if (studiesAfter2010OrLater || born91OrLater) {
                System.out.println("eligible");
            } else if (courses > 40) {
                System.out.println("ineligible");
            } else {
                System.out.println("coach petitions");
            }
        }
    }

}
