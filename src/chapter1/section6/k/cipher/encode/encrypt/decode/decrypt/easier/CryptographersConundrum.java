package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/11/20.
 */
public class CryptographersConundrum {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cipherText = scanner.nextLine();

        int days = 0;
        for (int i = 0; i < cipherText.length(); i++) {
            char character = cipherText.charAt(i);

            if ((i % 3 == 0 && character != 'P')
                    || (i % 3 == 1 && character != 'E')
                    || (i % 3 == 2 && character != 'R')) {
                days++;
            }
        }
        System.out.println(days);
    }
}
