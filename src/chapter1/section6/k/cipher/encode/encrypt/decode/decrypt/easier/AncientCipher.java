package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.*;

/**
 * Created by Rene Argento on 28/11/20.
 */
public class AncientCipher {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            String conjecture = scanner.nextLine();
            System.out.println(canBeEncryption(message, conjecture) ? "YES" : "NO");
        }
    }

    private static boolean canBeEncryption(String message, String conjecture) {
        Map<Character, Integer> messageFrequencyMap = getFrequencyMap(message);
        Map<Character, Integer> conjectureFrequencyMap = getFrequencyMap(conjecture);

        List<Integer> messageFrequencyList = new ArrayList<>(messageFrequencyMap.values());
        List<Integer> conjectureFrequencyList = new ArrayList<>(conjectureFrequencyMap.values());

        Collections.sort(messageFrequencyList);
        Collections.sort(conjectureFrequencyList);
        return messageFrequencyList.equals(conjectureFrequencyList);
    }

    private static Map<Character, Integer> getFrequencyMap(String string) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char character : string.toCharArray()) {
            int frequency = frequencyMap.getOrDefault(character, 0);
            frequencyMap.put(character, frequency + 1);
        }
        return frequencyMap;
    }
}
