package chapter2.section3.b.direct.addressing.table.dat.ascii;

import java.util.*;

/**
 * Created by Rene Argento on 09/12/20.
 */
public class CoolWord {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int caseNumber = 1;

        while (scanner.hasNext()) {
            int words = scanner.nextInt();
            int coolWords = 0;

            for (int i = 0; i < words; i++) {
                String word = scanner.next();
                if (isCool(word)) {
                    coolWords++;
                }
            }
            System.out.printf("Case %d: %d\n", caseNumber, coolWords);
            caseNumber++;
        }
    }

    private static boolean isCool(String word) {
        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (int i = 0; i < word.length(); i++) {
            char symbol = word.charAt(i);

            int frequency = frequencyMap.getOrDefault(symbol, 0);
            frequencyMap.put(symbol, frequency + 1);
        }

        if (frequencyMap.size() == 1) {
            return false;
        }

        Set<Integer> frequencySet = new HashSet<>();

        for (char key : frequencyMap.keySet()) {
            int frequency = frequencyMap.get(key);

            if (frequencySet.contains(frequency)) {
                return false;
            }
            frequencySet.add(frequency);
        }
        return true;
    }
}
