package chapter2.section2.a.oned.array.manipulation.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 24/01/21.
 */
public class MusicalLoop {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int samplesNumber = scanner.nextInt();

        while (samplesNumber != 0) {
            int[] samples = new int[samplesNumber];
            for (int i = 0; i < samples.length; i++) {
                samples[i] = scanner.nextInt();
            }

            int peaks = countPeaks(samples);
            System.out.println(peaks);
            samplesNumber = scanner.nextInt();
        }
    }

    private static int countPeaks(int[] samples) {
        int peaks = 0;

        for (int i = 0; i < samples.length; i++) {
            int previousIndex = i > 0 ? i - 1 : samples.length - 1;
            int nextIndex = i < samples.length - 1 ? i + 1 : 0;

            if ((samples[i] > samples[previousIndex] && samples[i] > samples[nextIndex])
                    || (samples[i] < samples[previousIndex] && samples[i] < samples[nextIndex])) {
                peaks++;
            }
        }
        return peaks;
    }
}
