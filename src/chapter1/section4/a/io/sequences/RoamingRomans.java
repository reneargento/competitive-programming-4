package chapter1.section4.a.io.sequences;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class RoamingRomans {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double englishMiles = scanner.nextDouble();
        double romanMiles = Math.round(englishMiles * 1000 * (5280.0/4854.0));
        System.out.println((int) romanMiles);
    }

}
