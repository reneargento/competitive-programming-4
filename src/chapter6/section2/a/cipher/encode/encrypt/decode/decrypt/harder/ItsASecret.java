package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/04/26.
 */
public class ItsASecret {

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
        int lines = FastReader.nextInt();
        int caseId = 1;

        while (lines != 0) {
            if (caseId > 1) {
                outputWriter.printLine();
            }
            String keyPhrase = FastReader.getLine().toUpperCase();
            char[][] cipher = buildCipher(keyPhrase);

            for (int i = 0; i < lines; i++) {
                String line = FastReader.getLine().toUpperCase();
                String encryptedLine = encryptLine(cipher, line);
                outputWriter.printLine(encryptedLine);
            }
            caseId++;
            lines = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static String encryptLine(char[][] cipher, String line) {
        StringBuilder encryptedLine = new StringBuilder();
        line = line.replaceAll(" ", "");
        line = line.replaceAll("J", "I");

        for (int i = 0; i < line.length(); i += 2) {
            char firstChar = line.charAt(i);
            char secondChar;
            if (i < line.length() - 1) {
                secondChar = line.charAt(i + 1);
            } else {
                secondChar = 'X';
            }

            if (firstChar == secondChar) {
                secondChar = 'X';
                i--;
            }

            if (firstChar == 'X' && secondChar == 'X') {
                encryptedLine.append("YY");
            } else {
                Cell cell1 = getCell(cipher, firstChar);
                Cell cell2 = getCell(cipher, secondChar);
                char newCharacter1;
                char newCharacter2;

                if (cell1.row == cell2.row) {
                    int nextColumn1 = (cell1.column + 1) % cipher[0].length;
                    int nextColumn2 = (cell2.column + 1) % cipher[0].length;
                    newCharacter1 = cipher[cell1.row][nextColumn1];
                    newCharacter2 = cipher[cell2.row][nextColumn2];
                } else if (cell1.column == cell2.column) {
                    int nextRow1 = (cell1.row + 1) % cipher.length;
                    int nextRow2 = (cell2.row + 1) % cipher.length;
                    newCharacter1 = cipher[nextRow1][cell1.column];
                    newCharacter2 = cipher[nextRow2][cell2.column];
                } else {
                    newCharacter1 = cipher[cell1.row][cell2.column];
                    newCharacter2 = cipher[cell2.row][cell1.column];
                }
                encryptedLine.append(newCharacter1).append(newCharacter2);
            }
        }
        return encryptedLine.toString();
    }

    private static char[][] buildCipher(String keyPhrase) {
        keyPhrase = keyPhrase.replaceAll("J", "I");
        char[][] cipher = new char[5][5];
        Set<Character> charactersUsed = new HashSet<>();
        int row = 0;
        int column = 0;

        for (int i = 0; i < keyPhrase.length(); i++) {
            char character = keyPhrase.charAt(i);
            if (charactersUsed.contains(character) || character == ' ') {
                continue;
            }

            cipher[row][column] = character;
            Cell nextCell = getNextCell(cipher[row].length, row, column);
            row = nextCell.row;
            column = nextCell.column;
            charactersUsed.add(character);
        }

        for (char character = 'A'; character <= 'Z'; character++) {
            if (charactersUsed.contains(character) || character == 'J') {
                continue;
            }
            cipher[row][column] = character;
            Cell nextCell = getNextCell(cipher[row].length, row, column);
            row = nextCell.row;
            column = nextCell.column;
        }
        return cipher;
    }

    private static Cell getNextCell(int columnLength, int row, int column) {
        if (column < columnLength - 1) {
            column++;
        } else {
            column = 0;
            row++;
        }
        return new Cell(row, column);
    }

    private static Cell getCell(char[][] cipher, char character) {
        for (int row = 0; row < cipher.length; row++) {
            for (int column = 0; column < cipher[0].length; column++) {
                if (cipher[row][column] == character) {
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
