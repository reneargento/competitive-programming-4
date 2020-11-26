package chapter1.section6.f.interesting.real.life.problems.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/11/20.
 */
public class WordCloud {

    private static class Word {
        String word;
        int frequency;

        public Word(String word, int frequency) {
            this.word = word;
            this.frequency = frequency;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cloudWidth = scanner.nextInt();
        int wordsCount = scanner.nextInt();
        int cloudNumber = 1;

        while (cloudWidth != 0 || wordsCount != 0) {
            int maxFrequency = 0;

            Word[] words = new Word[wordsCount];
            for (int i = 0; i < wordsCount; i++) {
                words[i] = new Word(scanner.next(), scanner.nextInt());
                maxFrequency = Math.max(maxFrequency, words[i].frequency);
            }

            int totalHeight = 0;
            int currentLineWidth = 0;
            int currentLineHeight = 0;

            for (int i = 0; i < wordsCount; i++) {
                int pointSize = 8 + (int) Math.ceil((40 * (words[i].frequency - 4)) / (maxFrequency - 4.0));
                int width = (int) Math.ceil(9 / 16.0 * words[i].word.length() * pointSize);

                if (currentLineWidth == 0
                        || currentLineWidth + 10 + width <= cloudWidth) {
                    if (currentLineWidth != 0) {
                        currentLineWidth += 10;
                    }

                    currentLineWidth += width;
                    currentLineHeight = Math.max(currentLineHeight, pointSize);
                } else {
                    totalHeight += currentLineHeight;

                    currentLineWidth = width;
                    currentLineHeight = pointSize;
                }
            }
            totalHeight += currentLineHeight;

            System.out.printf("CLOUD %d: %d\n", cloudNumber, totalHeight);
            cloudNumber++;

            cloudWidth = scanner.nextInt();
            wordsCount = scanner.nextInt();
        }
    }
}
