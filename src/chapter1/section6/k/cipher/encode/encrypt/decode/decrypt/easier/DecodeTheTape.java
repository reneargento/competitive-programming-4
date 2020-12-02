package chapter1.section6.k.cipher.encode.encrypt.decode.decrypt.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 28/11/20.
 */
public class DecodeTheTape {

    private static final int CARRIAGE_RETURN_ASCII = 13;
    private static final String END_TAPE = "___________";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        String line = scanner.nextLine();

        while (!line.equals(END_TAPE)) {
            int ascII = 0;
            int multiplier = 1;

            for (int i = line.length() - 2; i >= 2; i--) {
                char symbol = line.charAt(i);
                if (symbol == '.') {
                    continue;
                }

                if (symbol == 'o') {
                    ascII += multiplier;
                }
                multiplier *= 2;
            }

            if (ascII == CARRIAGE_RETURN_ASCII) {
                System.out.println();
            } else {
                System.out.print((char) ascII);
            }

            line = scanner.nextLine();
        }
    }
}
