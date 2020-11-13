package chapter1.section4.e.control.flow;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class SaveSetu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int operations = scanner.nextInt();
        long money = 0;

        for (int i = 0; i < operations; i++) {
            String operation = scanner.next();
            if (operation.equals("donate")) {
                money += scanner.nextInt();
            } else {
                System.out.println(money);
            }
        }
    }

}
