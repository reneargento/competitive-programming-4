package chapter1.section4.j.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/09/20.
 */
public class CowCrane {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int monicaStart = scanner.nextInt();
        int lydiaStart = scanner.nextInt();
        int monicaEnd = scanner.nextInt();
        int lydiaEnd = scanner.nextInt();
        int monicaEatTime = scanner.nextInt();
        int lydiaEatTime = scanner.nextInt();

        long[] times1 = getTimes(monicaStart, lydiaStart, monicaEnd, lydiaEnd, true);
        long[] times2 = getTimes(lydiaStart, monicaStart, lydiaEnd, monicaEnd, true);
        long[] times3 = getTimes(monicaStart, lydiaStart, monicaEnd, lydiaEnd, false);
        long[] times4 = getTimes(lydiaStart, monicaStart, lydiaEnd, monicaEnd, false);

        if ((times1[0] <= monicaEatTime && times1[1] <= lydiaEatTime)
                || (times2[0] <= lydiaEatTime && times2[1] <= monicaEatTime)
                || (times3[0] <= monicaEatTime && times3[1] <= lydiaEatTime)
                || (times4[0] <= lydiaEatTime && times4[1] <= monicaEatTime) ) {
            System.out.println("possible");
        } else {
            System.out.println("impossible");
        }
    }

    private static long[] getTimes(int cowStart1, int cowStart2, int cowEnd1, int cowEnd2, boolean straightPath) {
        long cowTime1;
        long cowTime2;

        if (straightPath) {
            cowTime1 = Math.abs(cowStart1) + Math.abs(cowStart1 - cowEnd1);
            cowTime2 = cowTime1 + Math.abs(cowEnd1 - cowStart2) + Math.abs(cowStart2 - cowEnd2);
        } else {
            cowTime1 = Math.abs(cowStart2) + Math.abs(cowStart2 - cowStart1) + Math.abs(cowStart1 - cowEnd1);
            cowTime2 = cowTime1 + Math.abs(cowEnd1 - cowStart1) + Math.abs(cowStart1 - cowEnd2);
        }

        return new long[]{ cowTime1, cowTime2 };
    }
}
