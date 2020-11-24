package chapter1.section6.e.interesting.real.life.problems.easier;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 31/10/20.
 */
public class Preludes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> notesMap = getNotesMap();
        int caseNumber = 1;

        while (scanner.hasNext()) {
            String[] line = scanner.nextLine().split(" ");
            String note = line[0];
            System.out.printf("Case %d: ", caseNumber);

            if (notesMap.containsKey(note)) {
                System.out.println(notesMap.get(note) + " " + line[1]);
            } else {
                System.out.println("UNIQUE");
            }
            caseNumber++;
        }

    }

    private static Map<String, String> getNotesMap() {
        Map<String, String> notesMap = new HashMap<>();
        notesMap.put("A#", "Bb");
        notesMap.put("Bb", "A#");
        notesMap.put("C#", "Db");
        notesMap.put("Db", "C#");
        notesMap.put("D#", "Eb");
        notesMap.put("Eb", "D#");
        notesMap.put("F#", "Gb");
        notesMap.put("Gb", "F#");
        notesMap.put("G#", "Ab");
        notesMap.put("Ab", "G#");
        return notesMap;
    }

}
