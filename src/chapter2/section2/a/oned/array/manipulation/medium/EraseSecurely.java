package chapter2.section2.a.oned.array.manipulation.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/01/21.
 */
public class EraseSecurely {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int deletions = scanner.nextInt();
        String originalBits = scanner.next();
        String afterDeletionBits = scanner.next();

        boolean swapped = (deletions % 2) == 1;
        boolean successful = true;

        for (int i = 0; i < originalBits.length(); i++) {
            if ((originalBits.charAt(i) == afterDeletionBits.charAt(i) && swapped)
                    || (originalBits.charAt(i) != afterDeletionBits.charAt(i) && !swapped)) {
                successful = false;
                break;
            }
        }
        System.out.println(successful ? "Deletion succeeded" : "Deletion failed");
    }
}
