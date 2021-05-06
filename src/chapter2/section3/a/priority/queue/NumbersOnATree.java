package chapter2.section3.a.priority.queue;

import java.util.Scanner;

/**
 * Created by Rene Argento on 29/04/21.
 */
public class NumbersOnATree {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int treeHeight = scanner.nextInt();

        String path = "";
        if (scanner.hasNext()) {
            path = scanner.next();
        }

        long rootValue = (long) Math.pow(2, treeHeight + 1) - 1;
        int index = 1;

        for (char child : path.toCharArray()) {
            if (child == 'L') {
                index *= 2;
            } else {
                index = index * 2 + 1;
            }
        }

        long targetLabel = rootValue - (index - 1);
        System.out.println(targetLabel);
    }
}
