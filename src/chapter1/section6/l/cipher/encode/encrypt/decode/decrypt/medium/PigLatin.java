package chapter1.section6.l.cipher.encode.encrypt.decode.decrypt.medium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Rene Argento on 29/11/20.
 */
// For some reason reading the characters with scanner or BufferedReader.readLine() gives presentation error.
// BufferedReader.read() had to be used.
// UVA-492
public class PigLatin {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder pigLatinSentence = new StringBuilder();
        StringBuilder currentWord = new StringBuilder();

        int read = bufferedReader.read();

        while (read != -1) {
            char character = (char) read;

            if (Character.isLetter(character)) {
                currentWord.append(character);
            } else {
                if (currentWord.length() > 0) {
                    convertWord(currentWord.toString(), pigLatinSentence);
                    currentWord = new StringBuilder();
                }
                pigLatinSentence.append(character);
            }
            read = bufferedReader.read();
        }

        if (currentWord.length() > 0) {
            convertWord(currentWord.toString(), pigLatinSentence);
        }

        System.out.print(pigLatinSentence);
    }

    private static void convertWord(String word, StringBuilder pigLatinSentence) {
        char firstLetter = word.charAt(0);

        if (isVowel(firstLetter)) {
            pigLatinSentence.append(word).append("ay");
        } else {
            String wordWithoutFirstLetter = "";
            if (word.length() > 1) {
                wordWithoutFirstLetter = word.substring(1);
            }

            pigLatinSentence.append(wordWithoutFirstLetter).append(firstLetter).append("ay");
        }
    }

    private static boolean isVowel(char character) {
        char lowerCaseCharacter = Character.toLowerCase(character);
        return lowerCaseCharacter == 'a'
                || lowerCaseCharacter == 'e'
                || lowerCaseCharacter == 'i'
                || lowerCaseCharacter == 'o'
                || lowerCaseCharacter == 'u';
    }
}
