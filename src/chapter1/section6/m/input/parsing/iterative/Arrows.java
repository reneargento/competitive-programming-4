package chapter1.section6.m.input.parsing.iterative;

import java.util.Scanner;

/**
 * Created by Rene Argento on 09/12/20.
 */
public class Arrows {

    private static final char UNKNOWN_TYPE = '.';
    private static final char SINGLE_TYPE = '-';
    private static final char DOUBLE_TYPE = '=';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            String arrows = scanner.next();
            int leftArrowMaxLength = getMaxLength(arrows, 0, arrows.length(), 1, '<');
            int rightArrowMaxLength = getMaxLength(arrows, arrows.length() - 1, -1, -1, '>');
            int maxLength = Math.max(leftArrowMaxLength, rightArrowMaxLength);
            System.out.printf("Case %d: %d\n", t, maxLength);
        }
    }

    private static int getMaxLength(String arrows, int startIndex, int endIndex, int increment, char arrow) {
        int maxLength = -1;

        for (int i = startIndex; i != endIndex; i += increment) {
            if (arrows.charAt(i) == arrow) {
                int currentLength = 1;
                char type = UNKNOWN_TYPE;

                for (int j = i + increment; j != endIndex; j += increment) {
                    char nextSymbol = arrows.charAt(j);
                    if (nextSymbol != SINGLE_TYPE && nextSymbol != DOUBLE_TYPE) {
                        break;
                    }

                    if (type == UNKNOWN_TYPE) {
                        type = nextSymbol;
                    } else if (type != nextSymbol) {
                        break;
                    }
                    currentLength++;
                    i += increment;
                }
                maxLength = Math.max(maxLength, currentLength);
            }
        }
        return maxLength;
    }
}
