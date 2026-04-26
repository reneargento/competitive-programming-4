package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/04/26.
 */
public class PlayfairCipherUVa {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int tests = FastReader.nextInt();
        for (int t = 0; t < tests; t++) {
            String keyPhrase = FastReader.getLine().toUpperCase();
            String text = FastReader.getLine().toUpperCase();

            String encryptedText = encrypt(keyPhrase, text);
            outputWriter.printLine(encryptedText);
        }
        outputWriter.flush();
    }

    private static String encrypt(String keyPhrase, String text) {
        text = text.replaceAll(" ", "");
        StringBuilder encryptedText = new StringBuilder();
        char[][] encryptionTable = buildEncryptionTable(keyPhrase);

        for (int i = 0; i < text.length(); i += 2) {
            char character1 = text.charAt(i);
            char character2;

            if (i == text.length() - 1) {
                character2 = 'X';
            } else {
                character2 = text.charAt(i + 1);
            }

            if (character1 == character2) {
                character2 = 'X';
                i--;
            }

            char encryptedCharacter1;
            char encryptedCharacter2;
            Cell character1Cell = getCharacterCell(encryptionTable, character1);
            Cell character2Cell = getCharacterCell(encryptionTable, character2);

            if (character1Cell.row == character2Cell.row) {
                int column1 = (character1Cell.column + 1) % encryptionTable[0].length;
                int column2 = (character2Cell.column + 1) % encryptionTable[0].length;
                encryptedCharacter1 = encryptionTable[character1Cell.row][column1];
                encryptedCharacter2 = encryptionTable[character2Cell.row][column2];
            } else if (character1Cell.column == character2Cell.column) {
                int row1 = (character1Cell.row + 1) % encryptionTable.length;
                int row2 = (character2Cell.row + 1) % encryptionTable.length;
                encryptedCharacter1 = encryptionTable[row1][character1Cell.column];
                encryptedCharacter2 = encryptionTable[row2][character2Cell.column];
            } else {
                encryptedCharacter1 = encryptionTable[character1Cell.row][character2Cell.column];
                encryptedCharacter2 = encryptionTable[character2Cell.row][character1Cell.column];
            }
            encryptedText.append(encryptedCharacter1).append(encryptedCharacter2);
        }
        return encryptedText.toString();
    }

    private static char[][] buildEncryptionTable(String keyPhrase) {
        char[][] encryptionTable = new char[5][5];
        Set<Character> charactersUsed = new HashSet<>();
        int keyPhraseIndex = 0;
        char alphabetChar = 'A';
        keyPhrase = keyPhrase.replaceAll(" ", "");

        for (int row = 0; row < encryptionTable.length; row++) {
            for (int column = 0; column < encryptionTable[row].length; column++) {
                boolean filled = false;
                while (keyPhraseIndex < keyPhrase.length()) {
                    char keyPhraseCharacter = keyPhrase.charAt(keyPhraseIndex);
                    keyPhraseIndex++;

                    if (!charactersUsed.contains(keyPhraseCharacter)) {
                        encryptionTable[row][column] = keyPhraseCharacter;
                        charactersUsed.add(keyPhraseCharacter);
                        filled = true;
                        break;
                    }
                }

                if (!filled) {
                    while (alphabetChar == 'Q'
                            || charactersUsed.contains(alphabetChar)) {
                        alphabetChar++;
                    }
                    encryptionTable[row][column] = alphabetChar;
                    charactersUsed.add(alphabetChar);
                }
            }
        }
        return encryptionTable;
    }

    private static Cell getCharacterCell(char[][] encryptionTable, char character) {
        for (int row = 0; row < encryptionTable.length; row++) {
            for (int column = 0; column < encryptionTable[0].length; column++) {
                if (encryptionTable[row][column] == character) {
                    return new Cell(row, column);
                }
            }
        }
        return null;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void flush() {
            writer.flush();
        }
    }
}
