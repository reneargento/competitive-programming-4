package chapter2.section2.b.oned.array.manipulation.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/01/21.
 */
public class GoodTeacher {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int studentsNumber = scanner.nextInt();
        String[] students = new String[studentsNumber];

        for (int i = 0; i < students.length; i++) {
            students[i] = scanner.next();
        }

        int queries = scanner.nextInt();
        for (int q = 0; q < queries; q++) {
            int studentIndex = scanner.nextInt() - 1;
            if (!students[studentIndex].equals("?")) {
                System.out.println(students[studentIndex]);
            } else {
                String closestName = getClosestName(students, studentIndex);
                System.out.println(closestName);
            }
        }
    }

    private static String getClosestName(String[] students, int index) {
        int leftDistance = Integer.MAX_VALUE;
        int rightDistance = Integer.MAX_VALUE;
        String leftName = null;
        String rightName = null;

        for (int i = index - 1; i >= 0; i--) {
            if (!students[i].equals("?")) {
                leftName = students[i];
                leftDistance = index - i;
                break;
            }
        }

        for (int i = index + 1; i < students.length; i++) {
            if (!students[i].equals("?")) {
                rightName = students[i];
                rightDistance = i - index;
                break;
            }
        }

        StringBuilder closestName = new StringBuilder();
        if (leftDistance == rightDistance) {
            closestName.append("middle of ").append(leftName).append(" and ").append(rightName);
        } else if (leftDistance < rightDistance) {
            for (int i = 0; i < leftDistance; i++) {
                closestName.append("right of ");
            }
            closestName.append(leftName);
        } else {
            for (int i = 0; i < rightDistance; i++) {
                closestName.append("left of ");
            }
            closestName.append(rightName);
        }
        return closestName.toString();
    }
}
