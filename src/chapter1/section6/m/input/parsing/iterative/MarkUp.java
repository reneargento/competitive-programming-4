package chapter1.section6.m.input.parsing.iterative;

import java.util.Scanner;

/**
 * Created by Rene Argento on 07/12/20.
 */
public class MarkUp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isMarkUpOn = true;

        while (scanner.hasNext()) {
            String line = scanner.nextLine();

            for (int i = 0; i < line.length(); i++) {
                char symbol = line.charAt(i);

                if (symbol == '\\') {
                    if (i < line.length() - 1) {
                        char nextSymbol = line.charAt(i + 1);
                        if (nextSymbol == '*') {
                            isMarkUpOn = !isMarkUpOn;
                            i++;
                        } else if (!isMarkUpOn) {
                            System.out.print("\\");
                        } else if (nextSymbol == 'b' || nextSymbol == 'i') {
                            i++;
                        } else if (nextSymbol == 's') {
                            boolean usedPoint = false;
                            i++;

                            for (int j = i + 1; j < line.length(); j++) {
                                char fontSizeSection = line.charAt(j);

                                if (fontSizeSection == '.') {
                                    if (!usedPoint) {
                                        usedPoint = true;
                                        i++;
                                    } else {
                                        break;
                                    }
                                } else if (Character.isDigit(fontSizeSection)) {
                                    i++;
                                } else {
                                    break;
                                }
                            }
                        } else {
                            System.out.print(nextSymbol);
                            i++;
                        }
                    } else if (!isMarkUpOn) {
                        System.out.print("\\");
                    }
                } else {
                    System.out.print(symbol);
                }
            }
            System.out.println();
        }
    }
}
