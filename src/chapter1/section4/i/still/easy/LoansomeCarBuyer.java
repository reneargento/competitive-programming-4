package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 18/09/20.
 */
public class LoansomeCarBuyer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int months = scanner.nextInt();
        double downPayment = scanner.nextDouble();
        double loan = scanner.nextDouble();
        int depreciationRecords = scanner.nextInt();

        while (months >= 0) {
            int currentMonth = 1;
            int lastMonthOwingMoreThanValue = -1;

            double payment = loan / (double) months;
            int month = scanner.nextInt();
            double depreciation = scanner.nextDouble();

            double carValue = (downPayment + loan) * (1 - depreciation);
            double owedMoney = loan;

            if (owedMoney < carValue) {
                lastMonthOwingMoreThanValue = 0;
            }

            for (int d = 1; d < depreciationRecords; d++) {
                month = scanner.nextInt();

                while (currentMonth < month) {
                    carValue = carValue * (1 - depreciation);
                    owedMoney -= payment;

                    if (owedMoney < carValue && lastMonthOwingMoreThanValue == -1) {
                        lastMonthOwingMoreThanValue = currentMonth;
                        break;
                    }

                    currentMonth++;
                }

                depreciation = scanner.nextDouble();
                carValue = carValue * (1 - depreciation);
                owedMoney -= payment;

                if (owedMoney < carValue && lastMonthOwingMoreThanValue == -1) {
                    lastMonthOwingMoreThanValue = currentMonth;
                }

                currentMonth++;
            }

            // If month has not been found yet
            while (lastMonthOwingMoreThanValue == -1) {
                carValue = carValue * (1 - depreciation);
                owedMoney -= payment;

                if (owedMoney < carValue) {
                    lastMonthOwingMoreThanValue = currentMonth;
                    break;
                }

                currentMonth++;
            }

            System.out.printf("%d month", lastMonthOwingMoreThanValue);
            if (lastMonthOwingMoreThanValue == 1) {
                System.out.println();
            } else {
                System.out.println("s");
            }

            months = scanner.nextInt();
            downPayment = scanner.nextDouble();
            loan = scanner.nextDouble();
            depreciationRecords = scanner.nextInt();
        }
    }

}
