package chapter2.section2.f.sorting.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 27/02/21.
 */
public class GearChanging {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int crankGears = FastReader.nextInt();
        int backWheelGears = FastReader.nextInt();
        double maxCadenceChange = FastReader.nextInt() / 100.0;

        int[] crankGearsTeeth = new int[crankGears];
        int[] backWheelGearsTeeth = new int[backWheelGears];

        for (int i = 0; i < crankGears; i++) {
            crankGearsTeeth[i] = FastReader.nextInt();
        }

        for (int i = 0; i < backWheelGears; i++) {
            backWheelGearsTeeth[i] = FastReader.nextInt();
        }

        double[] gearRatios = computeGearRatios(crankGearsTeeth, backWheelGearsTeeth);
        boolean isCurrentSetupOk = isCurrentSetupOk(gearRatios, maxCadenceChange);

        System.out.println(isCurrentSetupOk ? "Ride on!" : "Time to change gears!");
    }

    private static double[] computeGearRatios(int[] crankGearsTeeth, int[] backWheelGearsTeeth) {
        double[] gearRatios = new double[crankGearsTeeth.length * backWheelGearsTeeth.length];
        int gearRatioIndex = 0;

        for (int crankGear : crankGearsTeeth) {
            for (int backWheelGear : backWheelGearsTeeth) {
                double ratio = crankGear / (double) backWheelGear;
                gearRatios[gearRatioIndex++] = ratio;
            }
        }

        return gearRatios;
    }

    private static boolean isCurrentSetupOk(double[] gearRatios, double maxCadenceChange) {
        Arrays.sort(gearRatios);

        for (int i = 1; i < gearRatios.length; i++) {
            double ratioDifference = gearRatios[i] / gearRatios[i - 1];
            if (ratioDifference > maxCadenceChange + 1) {
                return false;
            }
        }
        return true;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
