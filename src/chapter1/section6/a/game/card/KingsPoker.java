package chapter1.section6.a.game.card;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 10/10/20.
 */
public class KingsPoker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int card1 = scanner.nextInt();
        int card2 = scanner.nextInt();
        int card3 = scanner.nextInt();

        while (card1 != 0 || card2 != 0 || card3 != 0) {
            int isSet = isSet(card1, card2, card3);

            if (isSet != -1) {
                if (isSet == 13) {
                    System.out.println("*");
                } else {
                    int nextValue = isSet + 1;
                    System.out.printf("%d %d %d\n", nextValue, nextValue, nextValue);
                }
            } else {
                int[] isPair = isPair(card1, card2, card3);

                if (isPair[0] != -1) {
                    if (isPair[1] != 13) {
                        int nextValue = isPair[1] + 1;

                        if (nextValue == isPair[0]) {
                            nextValue++;
                        }
                        if (nextValue == 14) {
                            System.out.println("1 1 1");
                        } else {
                            int[] values = {isPair[0], isPair[0], nextValue};
                            Arrays.sort(values);
                            System.out.printf("%d %d %d\n", values[0], values[1], values[2]);
                        }
                    } else {
                        int nextValue = isPair[0] + 1;
                        System.out.printf("1 %d %d\n", nextValue, nextValue);
                    }
                } else {
                    System.out.println("1 1 2");
                }
            }

            card1 = scanner.nextInt();
            card2 = scanner.nextInt();
            card3 = scanner.nextInt();
        }
    }

    private static int isSet(int card1, int card2, int card3) {
        if (card1 == card2 && card2 == card3) {
            return card1;
        }
        return -1;
    }

    private static int[] isPair(int card1, int card2, int card3) {
        int[] pairResult = {-1, -1};

        if (card1 == card2) {
            pairResult[0] = card1;
            pairResult[1] = card3;
        } else if (card1 == card3) {
            pairResult[0] = card1;
            pairResult[1] = card2;
        } else if (card2 == card3) {
            pairResult[0] = card2;
            pairResult[1] = card1;
        }
        return pairResult;
    }

}
