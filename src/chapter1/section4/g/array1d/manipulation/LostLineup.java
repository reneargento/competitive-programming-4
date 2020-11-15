package chapter1.section4.g.array1d.manipulation;

import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class LostLineup {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int people = scanner.nextInt();
        int[] order = new int[people];
        order[0] = 1;

        for (int i = 2; i <= people; i++) {
            int peopleBetween = scanner.nextInt();
            order[peopleBetween + 1] = i;
        }

        StringJoiner orderString = new StringJoiner(" ");
        for (int position : order) {
            orderString.add(String.valueOf(position));
        }
        System.out.println(orderString);
    }

}
