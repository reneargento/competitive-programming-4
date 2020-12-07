package chapter1.section6.m.input.parsing.iterative;

import java.util.Scanner;

/**
 * Created by Rene Argento on 06/12/20.
 */
public class Autori {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] longVariation = scanner.next().split("-");
        for (String name : longVariation) {
            System.out.print(name.charAt(0));
        }
        System.out.println();
    }
}
