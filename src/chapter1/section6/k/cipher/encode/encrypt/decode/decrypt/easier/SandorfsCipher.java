package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 27/11/20.
 */
public class SandorfsCipher {

    private static class Cell implements Comparable<Cell> {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public int compareTo(Cell otherCell) {
            if (row != otherCell.row) {
                return otherCell.row - row;
            }
            return otherCell.column - column;
        }
    }

    private static class CipherKey {
        Cell[] cells;

        public CipherKey(Cell[] cells) {
            this.cells = cells;
            Arrays.sort(cells);
        }

        private CipherKey rotateClockwise() {
            Cell[] rotatedCells = new Cell[cells.length];

            for (int i = 0; i < cells.length; i++) {
                Cell cell = cells[i];
                int newRow = cell.column;
                int newColumn = 5 - cell.row;
                rotatedCells[i] = new Cell(newRow, newColumn);
            }
            return new CipherKey(rotatedCells);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CipherKey[] cipherKeys = createCipherKeyList();

        while (scanner.hasNextLine()) {
            String encryptedMessage = scanner.nextLine();

            if (encryptedMessage.isEmpty()) {
                System.out.println();
                continue;
            }

            StringBuilder decryptedMessage = new StringBuilder();
            for (int i = 0; i < encryptedMessage.length(); i += 36) {
                String messageSection = decryptSandorfCipher(encryptedMessage.substring(i, i + 36), cipherKeys);
                decryptedMessage.insert(0, messageSection);
            }

            String finalDecryptedMessage = removeAppendedHashes(decryptedMessage);
            System.out.println(finalDecryptedMessage);
        }
    }

    private static String decryptSandorfCipher(String encryptedMessage, CipherKey[] cipherKeys) {
        char[][] matrix = new char[6][6];
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[0].length; c++) {
                int index = r * 6 + c;
                matrix[r][c] = encryptedMessage.charAt(index);
            }
        }
        return decryptMessage(matrix, cipherKeys);
    }

    private static String decryptMessage(char[][] matrix, CipherKey[] cipherKeys) {
        StringBuilder message = new StringBuilder();

        for (CipherKey cipherKey : cipherKeys) {
            for (Cell cell : cipherKey.cells) {
                message.append(matrix[cell.row][cell.column]);
            }
        }
        return message.toString();
    }

    private static String removeAppendedHashes(StringBuilder message) {
        int messageEnd = message.length();

        for (int i = message.length() - 1; i >= 0; i--) {
            if (message.charAt(i) != '#') {
                messageEnd = i + 1;
                break;
            }
        }
        return message.substring(0, messageEnd);
    }

    private static CipherKey[] createCipherKeyList() {
        CipherKey cipherKey1 = new CipherKey(new Cell[] {new Cell(0,1),
                new Cell(0,3), new Cell(0,5),
                new Cell(1,4), new Cell(2,2),
                new Cell(3,1), new Cell(3,4),
                new Cell(4,5), new Cell(5,3)
        });
        CipherKey cipherKey2 = cipherKey1.rotateClockwise();
        CipherKey cipherKey3 = cipherKey2.rotateClockwise();
        CipherKey cipherKey4 = cipherKey3.rotateClockwise();
        return new CipherKey[]{cipherKey4, cipherKey3, cipherKey2, cipherKey1};
    }
}
