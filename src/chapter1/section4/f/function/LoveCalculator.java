package chapter1.section4.f.function;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class LoveCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String name1 = scanner.nextLine();
            String name2 = scanner.nextLine();

            double score1 = getScore(name1);
            double score2 = getScore(name2);

            double high = Math.max(score1, score2);
            double low = Math.min(score1, score2);
            System.out.printf("%.2f %%\n", low / high * 100);
        }
    }

    private static int getScore(String name) {
        int sum = 0;

        for (int i = 0; i < name.length(); i++) {
            char character = Character.toLowerCase(name.charAt(i));

            if ('a' <= character && character <= 'z') {
                sum += character - 'a' + 1;
            }
        }

        String sumString = String.valueOf(sum);
        while (sumString.length() > 1) {
            sum = 0;

            for (int i = 0; i < sumString.length(); i++) {
                sum += Integer.parseInt(String.valueOf(sumString.charAt(i)));
            }

            sumString = String.valueOf(sum);
        }

        return sum;
    }

}
