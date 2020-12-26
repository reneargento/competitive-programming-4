package chapter1.section6.p.time.waster.problems.harder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rene Argento on 25/12/20.
 */
public class Froggie {

    private static class Lane {
        int carInterval;
        int carSpeed;
        boolean isLeftToRightDirection;
        int width;
        List<Integer> cars;

        public Lane(int offset, int carInterval, int carSpeed, boolean isLeftToRightDirection, int width) {
            this.carInterval = carInterval;
            this.carSpeed = carSpeed;
            this.isLeftToRightDirection = isLeftToRightDirection;
            this.width = width;
            cars = new ArrayList<>();
            offset = isLeftToRightDirection ? offset : width - 1 - offset;
            placeCars(cars, offset);
        }

        private void placeCars(List<Integer> carList, int offset) {
            if (isLeftToRightDirection) {
                for (int i = offset; i < width; i += carInterval) {
                    carList.add(i);
                }
            } else {
                for (int i = offset; i >= 0; i -= carInterval) {
                    carList.add(i);
                }
            }
        }

        public void moveCars() {
            List<Integer> newCars = new ArrayList<>();

            for (int car : cars) {
                if (isLeftToRightDirection) {
                    car += carSpeed;
                    if (car < width) {
                        newCars.add(car);
                    }
                } else {
                    car -= carSpeed;
                    if (car >= 0) {
                        newCars.add(car);
                    }
                }
            }

            if (isLeftToRightDirection) {
                if (!newCars.isEmpty() && newCars.get(0) >= carInterval) {
                    newCars.add(0, newCars.get(0) - carInterval);
                } else if (newCars.isEmpty()) {
                    newCars.add(0, carInterval - 1);
                }
            } else {
                if (!newCars.isEmpty() && newCars.get(0) < width - 1 - carInterval) {
                    newCars.add(0, newCars.get(0) + carInterval);
                } else if (newCars.isEmpty()) {
                    newCars.add(0, width - 1 - carInterval);
                }
            }
            cars = newCars;
        }

        private boolean willSquish(int position) {
            for (int car : cars) {
                if (car == position && carSpeed == 0) {
                    return true;
                }

                if (isLeftToRightDirection) {
                    if (car < position && position <= car + carSpeed) {
                        return true;
                    }
                } else {
                    if (car - carSpeed <= position && position < car) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int lanesNumber = scanner.nextInt();
        int width = scanner.nextInt();
        boolean isLeftToRightDirection = true;
        List<Lane> lanes = new ArrayList<>();

        for (int l = 0; l < lanesNumber; l++) {
            int offset = scanner.nextInt();
            int interval = scanner.nextInt();
            int speed = scanner.nextInt();
            lanes.add(new Lane(offset, interval, speed, isLeftToRightDirection, width));
            isLeftToRightDirection = !isLeftToRightDirection;
        }

        int froggieRow = lanes.size();
        int froggieColumn = scanner.nextInt();
        String moves = scanner.next();
        boolean squished = false;

        for (char move : moves.toCharArray()) {
            switch (move) {
                case 'U': froggieRow--; break;
                case 'D': froggieRow++; break;
                case 'L': froggieColumn--; break;
                default: froggieColumn++;
            }

            if (froggieRow >= 0 && froggieRow < lanes.size() && lanes.get(froggieRow).willSquish(froggieColumn)) {
                squished = true;
                break;
            }
            moveCars(lanes);
        }

        if (squished || froggieRow >= 0) {
            System.out.println("squish");
        } else {
            System.out.println("safe");
        }
    }

    private static void moveCars(List<Lane> lanes) {
        for (Lane lane : lanes) {
            lane.moveCars();
        }
    }
}
