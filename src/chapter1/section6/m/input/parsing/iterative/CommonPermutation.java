package chapter1.section6.m.input.parsing.iterative;

import java.util.*;

/**
 * Created by Rene Argento on 09/12/20.
 */
public class CommonPermutation {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String stringA = scanner.nextLine();
            String stringB = scanner.nextLine();

            Map<Character, Integer> charactersFrequency = new HashMap<>();
            addCharactersFromString(charactersFrequency, stringA);

            List<Character> commonCharactersList = new ArrayList<>();
            for (char character : stringB.toCharArray()) {
                int frequency = charactersFrequency.getOrDefault(character, 0);

                if (frequency > 0) {
                    commonCharactersList.add(character);
                    charactersFrequency.put(character, frequency - 1);
                }
            }

            Collections.sort(commonCharactersList);
            for (char character : commonCharactersList) {
                System.out.print(character);
            }
            System.out.println();
        }
    }

    private static void addCharactersFromString(Map<Character, Integer> charactersFrequency, String string) {
        for (char character : string.toCharArray()) {
            int frequency = charactersFrequency.getOrDefault(character, 0);
            charactersFrequency.put(character, frequency + 1);
        }
    }
}
