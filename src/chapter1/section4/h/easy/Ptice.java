package chapter1.section4.h.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class Ptice {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int questions = scanner.nextInt();
        String answers = scanner.next();

        char[] adrianAnswers = { 'A', 'B', 'C' };
        char[] brunoAnswers = { 'B', 'A', 'B', 'C' };
        char[] goranAnswers = { 'C', 'C', 'A', 'A', 'B', 'B' };

        int[] scores = new int[3];

        for (int i = 0; i < questions; i++) {
            char correctAnswer = answers.charAt(i);
            if (correctAnswer == adrianAnswers[i % adrianAnswers.length]) {
                scores[0]++;
            }
            if (correctAnswer == brunoAnswers[i % brunoAnswers.length]) {
                scores[1]++;
            }
            if (correctAnswer == goranAnswers[i % goranAnswers.length]) {
                scores[2]++;
            }
        }

        int highestScore = 0;

        for (int score : scores) {
            highestScore = Math.max(highestScore, score);
        }

        System.out.println(highestScore);
        if (scores[0] == highestScore) {
            System.out.println("Adrian");
        }
        if (scores[1] == highestScore) {
            System.out.println("Bruno");
        }
        if (scores[2] == highestScore) {
            System.out.println("Goran");
        }
    }

}
