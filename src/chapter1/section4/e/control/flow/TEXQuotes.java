package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class TEXQuotes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int replacements = 0;

        while (scanner.hasNextLine()) {
            String phrase = scanner.nextLine();
            StringBuilder updatedPhrase = new StringBuilder();

            for (char c : phrase.toCharArray()) {
                if (c == '"') {
                    if (replacements % 2 == 0) {
                        updatedPhrase.append("``");
                    } else {
                        updatedPhrase.append("''");
                    }
                    replacements++;
                } else {
                    updatedPhrase.append(c);
                }
            }
            System.out.println(updatedPhrase);
        }
    }

}
