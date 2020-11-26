package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 12/11/20.
 */
public class FittingTextIntoSlides {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int paragraphsCount = scanner.nextInt();
            scanner.nextLine();

            String[] paragraphs = new String[paragraphsCount];
            for (int i = 0; i < paragraphsCount; i++) {
                paragraphs[i] = scanner.nextLine();
            }

            int xSize = scanner.nextInt();
            int ySize = scanner.nextInt();

            int pointSize = -1;
            for (int p = 28; p >= 8; p--) {
                if (fitParagraphsInSlide(paragraphs, p, xSize, ySize)) {
                    pointSize = p;
                    break;
                }
            }

            if (pointSize != -1) {
                System.out.println(pointSize);
            } else {
                System.out.println("No solution");
            }
        }
    }

    private static boolean fitParagraphsInSlide(String[] paragraphs, int pointSize, int xSize, int ySize) {
        int maxLines = ySize / pointSize;
        int maxCharacters = xSize / pointSize;
        int linesUsed = 0;

        for (String paragraph : paragraphs) {
            linesUsed++;
            int paragraphLength = paragraph.length();

            while (paragraphLength > maxCharacters) {
                linesUsed++;
                int lastIndexUsed = -1;

                for (int i = maxCharacters; i >= 0; i--) {
                    if (paragraph.charAt(i) == ' ') {
                        lastIndexUsed = i;
                        break;
                    }
                }

                if (lastIndexUsed == -1) {
                    return false;
                }
                paragraph = paragraph.substring(lastIndexUsed + 1);
                paragraphLength = paragraph.length();
            }

            if (linesUsed > maxLines) {
                return false;
            }
        }
        return true;
    }
}
