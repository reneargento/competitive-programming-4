package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.*;

/**
 * Created by Rene Argento on 10/11/20.
 */
public class GettingChorded {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> notesMap = createNotesMap();
        Map<String, String> replaceNotesMap = createReplaceNotesMap();

        while (scanner.hasNext()) {
            String line = scanner.nextLine().trim();
            String sortedNotes = mapInputNotes(line, replaceNotesMap);

            System.out.printf("%s is ", line);
            if (notesMap.containsKey(sortedNotes)) {
                System.out.println("a " + notesMap.get(sortedNotes));
            } else {
                System.out.println("unrecognized.");
            }
        }
    }

    private static Map<String, String> createNotesMap() {
        Map<String, String> notesMap = new HashMap<>();
        String[] notes = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};

        int[] majorIncrements = {0, 4, 7};
        int[] minorIncrements = {0, 3, 7};
        for (int i = 0; i < notes.length; i++) {
            List<String> major = new ArrayList<>();
            List<String> minor = new ArrayList<>();

            for (int increment : majorIncrements) {
                int index = (i + increment) % notes.length;
                major.add(notes[index]);
            }

            for (int increment : minorIncrements) {
                int index = (i + increment) % notes.length;
                minor.add(notes[index]);
            }

            Collections.sort(major);
            Collections.sort(minor);

            String majorDescription = getNoteListDescription(major);
            String minorDescription = getNoteListDescription(minor);
            String note = notes[i];
            notesMap.put(majorDescription, note + " Major chord.");
            notesMap.put(minorDescription, note + " Minor chord.");
        }
        return notesMap;
    }

    private static Map<String, String> createReplaceNotesMap() {
        Map<String, String> replaceNotesMap = new HashMap<>();
        replaceNotesMap.put("BB", "A#");
        replaceNotesMap.put("DB", "C#");
        replaceNotesMap.put("EB", "D#");
        replaceNotesMap.put("GB", "F#");
        replaceNotesMap.put("AB", "G#");
        return replaceNotesMap;
    }

    private static String getNoteListDescription(List<String> notes) {
        StringJoiner joiner = new StringJoiner("-");
        for (String note : notes) {
            joiner.add(note);
        }
        return joiner.toString();
    }

    private static String mapInputNotes(String input, Map<String, String> replaceNotesMap) {
        String[] values = input.split(" ");
        List<String> notes = new ArrayList<>();

        for (String value : values) {
            if (!value.equals("")) {
                value = value.toUpperCase();
                if (replaceNotesMap.containsKey(value)) {
                    value = replaceNotesMap.get(value);
                }
                notes.add(value);
            }
        }
        Collections.sort(notes);
        return getNoteListDescription(notes);
    }
}
