package chapter1.section6.e.interesting.real.life.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 31/10/20.
 */
public class A18000SecondsRemaining {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int fileSize = scanner.nextInt();
        int dataSet = 1;

        while (fileSize != 0) {
            int totalBytesTransferred = 0;
            int second = 1;
            double recentlyTransferred = 0;

            System.out.printf("Output for data set %d, %d bytes:\n", dataSet, fileSize);

            while (totalBytesTransferred != fileSize) {
                int bytesTransferred = scanner.nextInt();

                totalBytesTransferred += bytesTransferred;
                recentlyTransferred += bytesTransferred;

                if (second % 5 == 0) {
                    System.out.print("   Time remaining: ");

                    if (recentlyTransferred == 0) {
                        System.out.println("stalled");
                    } else {
                        double transferRate = 0.2 * recentlyTransferred; // Dividing by 5.0 gives WA
                        double bytesToTransfer = fileSize - totalBytesTransferred;

                        int timeRemaining = (int) Math.ceil(bytesToTransfer/ transferRate);
                        System.out.printf("%d seconds\n", timeRemaining);
                    }
                    recentlyTransferred = 0;
                }
                second++;
            }
            System.out.printf("Total time: %d seconds\n", second - 1);
            System.out.println();

            dataSet++;
            fileSize = scanner.nextInt();
        }
    }
}
