package chapter1.section4.a.io.sequences;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
// https://oeis.org/A028400
public class Planina {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int iterations = scanner.nextInt();
        long power = (long) Math.pow(2, iterations) + 1;
        long points = power * power;
        System.out.println(points);
    }

}
