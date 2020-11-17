package chapter1.section4.h.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class HangingOutOnTheTerrace {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int capacity = scanner.nextInt();
        int events = scanner.nextInt();
        int deniedGroups = 0;
        int currentPeople = 0;

        scanner.nextLine();
        for (int i = 0; i < events; i++) {
            String[] event = scanner.nextLine().split(" ");
            int people = Integer.parseInt(event[1]);

            if (event[0].equals("leave")) {
                currentPeople -= people;
            } else {
                if (currentPeople + people > capacity) {
                    deniedGroups++;
                } else {
                    currentPeople += people;
                }
            }
        }
        System.out.println(deniedGroups);
    }

}
