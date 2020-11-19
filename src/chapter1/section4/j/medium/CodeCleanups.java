package chapter1.section4.j.medium;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Rene Argento on 25/09/20.
 */
public class CodeCleanups {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int pushes = scanner.nextInt();
        Set<Integer> oldPushes = new HashSet<>();
        int cleanups = 0;

        for (int p = 0; p < pushes; p++) {
            int day = scanner.nextInt();

            int dirt = 0;

            for (int oldPush : oldPushes) {
                dirt += day - oldPush;
            }

            if (dirt >= 20) {
                cleanups++;
                oldPushes.clear();
            }
            oldPushes.add(day);
        }
        System.out.println(cleanups + 1);
    }

}
