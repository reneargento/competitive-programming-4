package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.*;

/**
 * Created by Rene Argento on 11/11/20.
 */
public class MajoringInScales {

    private static class Note {
        String name;
        String name2;

        public Note(String name, String name2) {
            this.name = name;
            this.name2 = name2;
        }
    }

    private static class MajorScale {
        List<String> notes;

        public MajorScale(List<String> notes) {
            this.notes = notes;
        }

        private String getNote(String note, int offset) {
            int noteIndex = -1;
            int size = notes.size();
            for (int i = 0; i < size; i++) {
                if (notes.get(i).equals(note)) {
                    noteIndex = i;
                    break;
                }
            }

            if (noteIndex == -1) {
                return null;
            }

            if (offset == 7) {
                return note;
            }

            int targetIndex = noteIndex + offset;
            if (targetIndex >= size) {
                targetIndex %= size;
            } else if (targetIndex < 0) {
                targetIndex = size + targetIndex;
            }
            return notes.get(targetIndex);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, MajorScale> majorScaleMap = createMajorScaleMap();

        while (scanner.hasNext()) {
            String majorScale = scanner.nextLine();
            String[] intervals = scanner.nextLine().split(";");

            System.out.printf("Key of %s\n", majorScale);
            MajorScale majorScaleNotes = majorScaleMap.get(majorScale);

            for (int i = 0; i < intervals.length; i++) {
                String[] intervalData = intervals[i].split(" ");
                String note = intervalData[0];
                String direction = intervalData[1];
                String interval = intervalData[2];

                System.out.printf("%s: ", note);

                int offset = getOffset(direction, interval);
                String targetNote = majorScaleNotes.getNote(note, offset);
                if (targetNote == null) {
                    System.out.println("invalid note for this key");
                } else {
                    System.out.printf("%s %s > %s\n", direction, interval, targetNote);
                }
            }
            System.out.println();
        }
    }

    private static Map<String, MajorScale> createMajorScaleMap() {
        Note[] notes = {
                new Note("A", null), new Note("A#", "Bb"), new Note("B", "Cb"),
                new Note("C", "B#"), new Note("C#", "Db"), new Note("D", null),
                new Note("D#", "Eb"), new Note("E", "Fb"), new Note("F", "E#"),
                new Note("F#", "Gb"), new Note("G", null), new Note("G#", "Ab")
        };
        int[] increments = {0, 2, 2, 1, 2, 2, 2};
        Map<String, MajorScale> majorScaleMap = new HashMap<>();

        for (int i = 0; i < notes.length; i++) {
            buildMajorScale(majorScaleMap, notes, new ArrayList<>(), increments, i, 0, notes[i].name);

            if (notes[i].name2 != null) {
                buildMajorScale(majorScaleMap, notes, new ArrayList<>(), increments, i, 0, notes[i].name2);
            }
        }
        return majorScaleMap;
    }

    private static void buildMajorScale(Map<String, MajorScale> majorScaleMap, Note[] notes, List<String> major,
                                        int[] increments, int notesIndex, int incrementIndex, String note) {
        if (incrementIndex == increments.length) {
            if (isValidMajor(major, note)) {
                majorScaleMap.put(note, new MajorScale(major));
            }
            return;
        }

        notesIndex = (notesIndex + increments[incrementIndex]) % notes.length;
        Note nextNote = notes[notesIndex];

        if (nextNote.name2 != null) {
            List<String> copyMajor = new ArrayList<>(major);
            copyMajor.add(nextNote.name2);
            buildMajorScale(majorScaleMap, notes, copyMajor, increments, notesIndex, incrementIndex + 1, note);
        }
        major.add(nextNote.name);
        buildMajorScale(majorScaleMap, notes, major, increments, notesIndex, incrementIndex + 1, note);
    }

    private static boolean isValidMajor(List<String> major, String note) {
        boolean containsHash = note.contains("#");
        boolean containsB = note.contains("b");
        Set<Character> lettersSeen = new HashSet<>();

        for (String currentNote : major) {
            if (currentNote.contains("#")) {
                containsHash = true;
                if (containsB) {
                    return false;
                }
            } else if (currentNote.contains("b")) {
                containsB = true;
                if (containsHash) {
                    return false;
                }
            }

            char character = currentNote.charAt(0);
            if (lettersSeen.contains(character)) {
                return false;
            } else {
                lettersSeen.add(character);
            }
        }
        return true;
    }

    private static int getOffset(String direction, String interval) {
        int offset = 0;
        switch (interval) {
            case "SECOND": offset = 1; break;
            case "THIRD": offset = 2; break;
            case "FOURTH": offset = 3; break;
            case "FIFTH": offset = 4; break;
            case "SIXTH": offset = 5; break;
            case "SEVENTH": offset = 6; break;
            case "OCTAVE": offset = 7; break;
        }
        if (direction.equals("DOWN")) {
            offset *= -1;
        }
        return offset;
    }
}
