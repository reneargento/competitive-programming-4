package chapter1.section6.f.interesting.real.life.problems.medium;

import java.util.*;

/**
 * Created by Rene Argento on 04/11/20.
 */
public class MajorScales {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] orderedNotes = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        Map<String, Set<String>> notesMap = createNotesMap(orderedNotes);

        while (!line.equals("END")) {
            String[] notes = line.split(" ");
            printPossibleKeys(notesMap, notes, orderedNotes);
            line = scanner.nextLine();
        }
    }

    private static Map<String, Set<String>> createNotesMap(String[] notes) {
        Map<String, Set<String>> notesMap = new HashMap<>();
        int[] increments = {0, 2, 2, 1, 2, 2, 2, 1};

        for (int i = 0; i < 12; i++) {
            Set<String> composition = new HashSet<>();
            int index = i;

            for (int j = 0; j < increments.length; j++) {
                index += increments[j];
                index %= notes.length;
                composition.add(notes[index]);
            }
            notesMap.put(notes[i], composition);
        }
        return notesMap;
    }

    private static void printPossibleKeys(Map<String, Set<String>> notesMap, String[] notes, String[] orderedNotes) {
        StringJoiner possibleKeys = new StringJoiner(" ");

        for (String note : orderedNotes) {
            Set<String> composition = notesMap.get(note);
            boolean isPossible = true;

            for (String currentNote : notes) {
                if (!composition.contains(currentNote)) {
                    isPossible = false;
                    break;
                }
            }
            if (isPossible) {
                possibleKeys.add(note);
            }
        }
        System.out.println(possibleKeys);
    }

}
