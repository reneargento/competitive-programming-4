package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 08/11/20.
 */
public class CreditCardPayment {

    private static final double EPSILON = .0000001;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            double monthlyInterestRate = scanner.nextDouble() / 100;
            double balance = scanner.nextDouble();
            double payment = scanner.nextDouble();
            int numberOfPayments = 0;

            while (numberOfPayments <= 1200) {
                numberOfPayments++;

                double monthlyInterest = balance * monthlyInterestRate;
                monthlyInterest = Math.round(monthlyInterest * 100) / 100.0;
                balance += monthlyInterest;

                balance = Math.round(balance * 100) / 100.0;

                balance -= payment;

                if (balance - EPSILON <= 0) {
                    break;
                }
            }

            if (numberOfPayments <= 1200) {
                System.out.println(numberOfPayments);
            } else {
                System.out.println("impossible");
            }
        }
    }
}
