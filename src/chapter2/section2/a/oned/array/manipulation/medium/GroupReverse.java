package chapter2.section2.a.oned.array.manipulation.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 23/01/21.
 */
public class GroupReverse {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int groups = scanner.nextInt();

        while (groups != 0) {
            String string = scanner.next();
            int groupSize = string.length() / groups;

            for (int i = groupSize - 1; i < string.length(); i += groupSize) {
                int endIndex = i - groupSize;
                for (int j = i; j > endIndex; j--) {
                    System.out.print(string.charAt(j));
                }
            }
            System.out.println();
            groups = scanner.nextInt();
        }
    }
}
