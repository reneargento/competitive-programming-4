package chapter1.section6.n.output.formatting.easier;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Rene Argento on 12/12/20.
 */
public class MusicalNotation {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        String[] notes = scanner.nextLine().split(" ");

        int columnSize = getColumnSize(notes) + 3;
        char[][] songDescription = new char[14][columnSize];
        initializeSongDescription(songDescription);

        addNotes(songDescription, notes);
        display(songDescription);
    }

    private static int getColumnSize(String[] notes) {
        int size = notes.length - 1;
        for (String note : notes) {
            if (note.length() == 2) {
                size += Character.getNumericValue(note.charAt(1));
            } else {
                size++;
            }
        }
        return size;
    }

    private static void initializeSongDescription(char[][] songDescription) {
        char[] notes = "GFEDCBAgfedcba".toCharArray();
        List<Integer> filledRows = new ArrayList<>();
        filledRows.add(1);
        filledRows.add(3);
        filledRows.add(5);
        filledRows.add(7);
        filledRows.add(9);
        filledRows.add(13);

        for (int row = 0; row < songDescription.length; row++) {
            for (int column = 0; column < songDescription[0].length; column++) {
                if (column == 0) {
                    songDescription[row][column] = notes[row];
                } else if (column == 1) {
                    songDescription[row][column] = ':';
                } else if (column == 2) {
                    songDescription[row][column] = ' ';
                } else if (filledRows.contains(row)) {
                    songDescription[row][column] = '-';
                } else {
                    songDescription[row][column] = ' ';
                }
            }
        }
    }

    private static void addNotes(char[][] songDescription, String[] notes) {
        Map<Character, Integer> noteToRowMap = createNotToRowMap();
        int columnIndex = 3;

        for (String note : notes) {
            int row = noteToRowMap.get(note.charAt(0));

            int frequency = 1;
            if (note.length() == 2) {
                frequency = Character.getNumericValue(note.charAt(1));
            }

            for (int i = columnIndex; i < columnIndex + frequency; i++) {
                songDescription[row][i] = '*';
            }

            columnIndex += (frequency + 1);
        }
    }

    private static Map<Character, Integer> createNotToRowMap() {
        Map<Character, Integer> noteToRowMap = new HashMap<>();
        noteToRowMap.put('G', 0);
        noteToRowMap.put('F', 1);
        noteToRowMap.put('E', 2);
        noteToRowMap.put('D', 3);
        noteToRowMap.put('C', 4);
        noteToRowMap.put('B', 5);
        noteToRowMap.put('A', 6);
        noteToRowMap.put('g', 7);
        noteToRowMap.put('f', 8);
        noteToRowMap.put('e', 9);
        noteToRowMap.put('d', 10);
        noteToRowMap.put('c', 11);
        noteToRowMap.put('b', 12);
        noteToRowMap.put('a', 13);
        return noteToRowMap;
    }

    private static void display(char[][] time) {
        OutputWriter outputWriter = new OutputWriter(System.out);

        for (int row = 0; row < time.length; row++) {
            for (int column = 0; column < time[0].length; column++) {
                outputWriter.print(time[row][column]);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++)
            {
                if (i != 0)
                    writer.print(' ');
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
