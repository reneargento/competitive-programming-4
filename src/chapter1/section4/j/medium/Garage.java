package chapter1.section4.j.medium;

import java.util.*;

/**
 * Created by Rene Argento on 26/09/20.
 */
public class Garage {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int parkingSpaces = scanner.nextInt();
        int cars = scanner.nextInt();
        long money = 0;

        PriorityQueue<Integer> freeSpaces = new PriorityQueue<>();
        Map<Integer, Integer> occupiedSpaces = new HashMap<>();
        Queue<Integer> carsQueue = new LinkedList<>();

        int[] rates = new int[parkingSpaces];
        for (int i = 0; i < rates.length; i++) {
            rates[i] = scanner.nextInt();
            freeSpaces.offer(i);
        }

        int[] carWeights = new int[cars];
        for (int i = 0; i < carWeights.length; i++) {
            carWeights[i] = scanner.nextInt();
        }

        for (int i = 0; i < 2 * cars; i++) {
            int carEvent = scanner.nextInt();

            if (carEvent > 0) {
                carEvent--; // adjust for 0-index

                if (!freeSpaces.isEmpty()) {
                    int nextSpace = freeSpaces.poll();
                    occupiedSpaces.put(carEvent, nextSpace);
                    money += carWeights[carEvent] * rates[nextSpace];
                } else {
                    carsQueue.offer(carEvent);
                }
            } else {
                carEvent++; // adjust for 0-index
                carEvent *= -1;

                int freedSpace = occupiedSpaces.get(carEvent);
                occupiedSpaces.remove(carEvent);

                if (!carsQueue.isEmpty()) {
                    int nextCar = carsQueue.poll();
                    occupiedSpaces.put(nextCar, freedSpace);
                    money += carWeights[nextCar] * rates[freedSpace];
                } else {
                    freeSpaces.offer(freedSpace);
                }
            }
        }
        System.out.println(money);
    }

}
