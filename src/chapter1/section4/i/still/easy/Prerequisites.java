package chapter1.section4.i.still.easy;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Rene Argento on 19/09/20.
 */
public class Prerequisites {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int courses = scanner.nextInt();

        while (courses != 0) {
            int categories = scanner.nextInt();
            Set<Integer> chosenCourses = new HashSet<>();
            boolean willGraduate = true;

            for (int c = 0; c < courses; c++) {
                chosenCourses.add(scanner.nextInt());
            }

            for (int c = 0; c < categories; c++) {
                int coursesInCategory = scanner.nextInt();
                int required = scanner.nextInt();
                int taken = 0;

                for (int i = 0; i < coursesInCategory; i++) {
                    if (chosenCourses.contains(scanner.nextInt())) {
                        taken++;
                    }
                }

                if (taken < required) {
                    willGraduate = false;
                }
            }

            System.out.println(willGraduate ? "yes" : "no");
            courses = scanner.nextInt();
        }
    }

}
