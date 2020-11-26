package chapter1.section6.f.interesting.real.life.problems.medium;

import java.util.*;

/**
 * Created by Rene Argento on 07/11/20.
 */
public class MusicalScales {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int notesCount = scanner.nextInt();

        String[] notes = new String[notesCount];
        for (int i = 0; i < notesCount; i++) {
            notes[i] = scanner.next();
        }

        String[] orderedNotes = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
        Map<String, Set<String>> notesMap = createNotesMap(orderedNotes);

        printPossibleKeys(notesMap, notes, orderedNotes);
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
        boolean none = true;

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
                none = false;
            }
        }

        if (none) {
            System.out.println("none");
        } else {
            System.out.println(possibleKeys);
        }
    }

}
