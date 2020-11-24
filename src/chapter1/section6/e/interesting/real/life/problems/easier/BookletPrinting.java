package chapter1.section6.e.interesting.real.life.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 29/10/20.
 */
public class BookletPrinting {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int pages = scanner.nextInt();

        while (pages != 0) {
            int pagesBlank;
            if (pages % 4 == 0) {
                pagesBlank = 0;
            } else {
                pagesBlank = 4 - (pages % 4);
            }

            int sheet = 1;
            int pagesPrinted = 0;
            int startIndexPage;
            int endIndexPage = pages;

            if (pagesBlank == 0) {
                endIndexPage -= 2;
            } else if (pagesBlank == 1) {
                endIndexPage--;
            }

            if (pagesBlank <= 2) {
                startIndexPage = 3;
            } else {
                startIndexPage = 5;
            }

            System.out.printf("Printing order for %d pages:\n", pages);

            while (pagesPrinted < pages) {
                if (sheet == 1) {
                    printFirstPage(pagesBlank, pages);
                    pagesPrinted += (4 - pagesBlank);

                    if (pagesBlank == 3 && pages > 4) {
                        System.out.printf("Sheet 2, back : 4, %d\n", endIndexPage--);
                        sheet++;
                        pagesPrinted += 4;
                    }
                } else {
                    System.out.printf("Sheet %d, front: %d, %d\n", sheet, endIndexPage--, startIndexPage++);
                    System.out.printf("Sheet %d, back : %d, %d\n", sheet, startIndexPage++, endIndexPage--);
                    pagesPrinted += 4;
                }
                sheet++;
            }
            pages = scanner.nextInt();
        }
    }

    private static void printFirstPage(int pagesBlank, int pages) {
        if (pagesBlank == 0) {
            System.out.printf("Sheet 1, front: %d, 1\n", pages);
            System.out.printf("Sheet 1, back : 2, %d\n", pages - 1);
        } else if (pagesBlank == 1) {
            System.out.println("Sheet 1, front: Blank, 1");
            System.out.printf("Sheet 1, back : 2, %d\n", pages);
        } else if (pagesBlank == 2) {
            System.out.println("Sheet 1, front: Blank, 1");
            System.out.println("Sheet 1, back : 2, Blank");
        } else if (pagesBlank == 3) {
            System.out.println("Sheet 1, front: Blank, 1");

            if (pages > 1) {
                System.out.println("Sheet 1, back : 2, Blank");
                System.out.println("Sheet 2, front: Blank, 3");
            }
        }
    }

}
