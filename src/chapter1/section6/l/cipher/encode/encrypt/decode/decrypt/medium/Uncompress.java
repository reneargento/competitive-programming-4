package chapter1.section6.l.cipher.encode.encrypt.decode.decrypt.medium;

import java.util.*;

/**
 * Created by Rene Argento on 29/11/20.
 */
public class Uncompress {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        LinkedList<String> wordList = new LinkedList<>();
        Set<String> existingWords = new HashSet<>();
        StringBuilder uncompressedFile = new StringBuilder();

        while (!line.equals("0")) {
            StringBuilder currentWord = new StringBuilder();
            for (int i = 0; i < line.length(); i++) {
                char character = line.charAt(i);

                if (Character.isLetter(character)) {
                    currentWord.append(character);
                } else if (Character.isDigit(character)) {
                    String number = getDigits(line, i);
                    i += number.length() - 1;

                    int position = Integer.parseInt(number) - 1;
                    String word = wordList.get(position);
                    wordList.remove(position);
                    wordList.addFirst(word);
                    uncompressedFile.append(word);
                } else {
                    if (currentWord.length() > 0) {
                        appendWord(currentWord, existingWords, wordList, uncompressedFile);
                        currentWord = new StringBuilder();
                    }
                    uncompressedFile.append(character);
                }
            }

            if (currentWord.length() > 0) {
                appendWord(currentWord, existingWords, wordList, uncompressedFile);
            }

            uncompressedFile.append("\n");
            line = scanner.nextLine();
        }
        System.out.print(uncompressedFile);
    }

    private static String getDigits(String line, int index) {
        StringBuilder number = new StringBuilder(String.valueOf(line.charAt(index)));

        for (int j = index + 1; j < line.length(); j++) {
            char nextCharacter = line.charAt(j);

            if (Character.isDigit(nextCharacter)) {
                number.append(Character.getNumericValue(nextCharacter));
            } else {
                break;
            }
        }
        return number.toString();
    }

    private static void appendWord(StringBuilder currentWord, Set<String> existingWords, LinkedList<String> wordList,
                                   StringBuilder uncompressedFile) {
        String word = currentWord.toString();

        if (!existingWords.contains(word)) {
            existingWords.add(word);
        } else {
            wordList.remove(word);
        }
        wordList.addFirst(word);
        uncompressedFile.append(word);
    }
}
