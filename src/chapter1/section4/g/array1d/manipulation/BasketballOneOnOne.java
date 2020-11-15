package chapter1.section4.g.array1d.manipulation;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class BasketballOneOnOne {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String annotations = scanner.next();
        System.out.println(annotations.charAt(annotations.length() - 2));
    }

}
