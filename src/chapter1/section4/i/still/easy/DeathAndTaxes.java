package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 21/09/20.
 */
public class DeathAndTaxes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int totalShares = 0;
        double averageValue = 0;

        while (true) {
            String[] event = scanner.nextLine().split(" ");
            String eventAction = event[0];

            if (eventAction.equals("buy")) {
                int shares = Integer.parseInt(event[1]);
                double value = Double.parseDouble(event[2]);

                averageValue = ((totalShares * averageValue) + (shares * value)) / (totalShares + shares);
                totalShares += shares;
            } else if (eventAction.equals("sell")) {
                int shares = Integer.parseInt(event[1]);
                totalShares -= shares;
            } else if (eventAction.equals("split")) {
                int quantityToSplit = Integer.parseInt(event[1]);
                totalShares *= quantityToSplit;
                averageValue /= quantityToSplit;
            } else if (eventAction.equals("merge")) {
                int quantityToMerge = Integer.parseInt(event[1]);
                totalShares = totalShares / quantityToMerge;
                averageValue = averageValue * quantityToMerge;
            } else {
                double valueSold = Double.parseDouble(event[1]);
                double profit = valueSold - averageValue;
                double crownsObtained = totalShares * valueSold;
                if (profit > 0) {
                    crownsObtained = totalShares * (valueSold - (profit * 0.3));
                }
                System.out.println(crownsObtained);
                break;
            }
        }
    }

}
