package chapter1.section6.m.input.parsing.iterative;

import java.util.Scanner;

/**
 * Created by Rene Argento on 10/12/20.
 */
public class Acronyms {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String[] authors = scanner.nextLine().split(" ");
            String[] name = scanner.nextLine().split(" ");

            boolean possible = true;
            if (authors.length != name.length) {
                possible = false;
            } else {
                for (int i = 0; i < authors.length; i++) {
                    if (authors[i].charAt(0) != name[i].charAt(0)) {
                        possible = false;
                        break;
                    }
                }
            }
            System.out.println(possible? "yes" : "no");
        }
    }
}
