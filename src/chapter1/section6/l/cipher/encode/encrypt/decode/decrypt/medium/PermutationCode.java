package chapter1.section6.l.cipher.encode.encrypt.decode.decrypt.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 01/12/20.
 */
public class PermutationCode {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();

        while (x != 0) {
            String s = scanner.next();
            String p = scanner.next();
            String encryptedMessage = scanner.next();

            int length = encryptedMessage.length();
            int d = (int) (Math.pow(length, 1.5) + x) % length;

            char[] decryptedMessage = new char[length];
            int dIndex = s.indexOf(encryptedMessage.charAt(d));

            decryptedMessage[d] = p.charAt(dIndex);

            int stopCondition = length > 1 ? d + 1 : d;

            for (int i = d; i != stopCondition; i--) {
                int sIndex = s.indexOf(decryptedMessage[i]);
                int previousEncryptedMessageIndex = i - 1;
                if (previousEncryptedMessageIndex < 0) {
                    previousEncryptedMessageIndex = length - 1;
                }

                int sResultIndex = s.indexOf(encryptedMessage.charAt(previousEncryptedMessageIndex));
                int pIndex = sIndex ^ sResultIndex;
                decryptedMessage[previousEncryptedMessageIndex] = p.charAt(pIndex);

                if (i == 0) {
                    i = length;
                }
            }
            System.out.println(new String(decryptedMessage));

            x = scanner.nextInt();
        }
    }
}
