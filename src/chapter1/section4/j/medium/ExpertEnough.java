package chapter1.section4.j.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 24/09/20.
 */
public class ExpertEnough {

    private static class Car {
        String name;
        int lowestPrice;
        int highestPrice;

        public Car(String name, int lowestPrice, int highestPrice) {
            this.name = name;
            this.lowestPrice = lowestPrice;
            this.highestPrice = highestPrice;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int databaseSize = scanner.nextInt();
            Car[] cars = new Car[databaseSize];

            for (int i = 0; i < cars.length; i++) {
                cars[i] = new Car(scanner.next(), scanner.nextInt(), scanner.nextInt());
            }

            int queries = scanner.nextInt();

            if (t > 0) {
                System.out.println();
            }

            for (int q = 0; q < queries; q++) {
                int query = scanner.nextInt();

                String maker = null;
                boolean moreThanOneMaker = false;

                for (Car car : cars) {
                    if (car.lowestPrice <= query && query <= car.highestPrice) {
                        if (maker == null) {
                            maker = car.name;
                        } else {
                            moreThanOneMaker = true;
                        }
                    }
                }

                if (maker == null || moreThanOneMaker) {
                    System.out.println("UNDETERMINED");
                } else {
                    System.out.println(maker);
                }
            }
        }
    }

}
