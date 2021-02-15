package chapter2.section2.b.oned.array.manipulation.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/02/21.
 */
public class BumperToBumperTraffic {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int car1Position = FastReader.nextInt();
        int car2Position = FastReader.nextInt();

        int[] speedChangeTimes1 = getSpeedChanges();
        int[] speedChangeTimes2 = getSpeedChanges();

        if (car1Position > car2Position) {
            int tempPosition = car1Position;
            car1Position = car2Position;
            car2Position = tempPosition;

            int[] tempTimes = speedChangeTimes1;
            speedChangeTimes1 = speedChangeTimes2;
            speedChangeTimes2 = tempTimes;
        }

        int bumpTime = getBumpTime(car1Position, car2Position, speedChangeTimes1, speedChangeTimes2);
        if (bumpTime != -1) {
            System.out.printf("bumper tap at time %d\n", bumpTime);
        } else {
            System.out.println("safe and sound");
        }
    }

    private static int[] getSpeedChanges() throws IOException {
        int speedChanges = FastReader.nextInt() + 1;
        int[] speedChangeTimes = new int[speedChanges];

        for (int i = 0; i < speedChangeTimes.length - 1; i++) {
            speedChangeTimes[i] = FastReader.nextInt();
        }
        speedChangeTimes[speedChangeTimes.length - 1] = 10000000;
        return speedChangeTimes;
    }

    private static int getBumpTime(int car1Position, int car2Position, int[] speedChangeTimes1,
                                   int[] speedChangeTimes2) {
        int bumpTime = -1;
        int times1Index = 0;
        int times2Index = 0;
        boolean car1Moving = false;
        boolean car2Moving = false;
        int minTime = 0;

        while (times1Index < speedChangeTimes1.length || times2Index < speedChangeTimes2.length) {
            boolean updateCar1 = false;
            boolean updateCar2 = false;
            int previousTime = minTime;

            if (times2Index >= speedChangeTimes2.length ||
                    (times1Index < speedChangeTimes1.length
                            && speedChangeTimes1[times1Index] <= speedChangeTimes2[times2Index])) {
                minTime = speedChangeTimes1[times1Index];
                updateCar1 = true;

                if (times2Index < speedChangeTimes2.length
                        && speedChangeTimes1[times1Index] == speedChangeTimes2[times2Index]) {
                    updateCar2 = true;
                    times2Index++;
                }
                times1Index++;
            } else {
                minTime = speedChangeTimes2[times2Index];
                updateCar2 = true;
                times2Index++;
            }

            if (car1Moving) {
                car1Position += (minTime - previousTime);
            }
            if (car2Moving) {
                car2Position += (minTime - previousTime);
            }

            if (updateCar1) {
                car1Moving = !car1Moving;
            }
            if (updateCar2) {
                car2Moving = !car2Moving;
            }

            int car1FrontPosition = car1Position + 4;
            if (car1FrontPosition >= car2Position) {
                int extraTime = car1FrontPosition - car2Position;
                return minTime - extraTime;
            }
        }
        return bumpTime;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
