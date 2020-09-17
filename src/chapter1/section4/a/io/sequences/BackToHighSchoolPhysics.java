package chapter1.section4.a.io.sequences;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class BackToHighSchoolPhysics {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            int velocity = scanner.nextInt();
            int time = scanner.nextInt();
            System.out.println(time * velocity * 2);
        }
    }

}
