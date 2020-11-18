package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 18/09/20.
 */
public class BossBattle {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int pillars = scanner.nextInt();
        System.out.println(pillars >= 4 ? pillars - 2 : 1);
    }

}
