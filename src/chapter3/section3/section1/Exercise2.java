package chapter3.section3.section1;

/**
 * Created by Rene Argento on 09/03/22.
 */
public class Exercise2 {

    private static final double EPS = 000000000.1;

    public static void main(String[] args) {
        int low = 0;
        int high = 10000;
        while (Math.abs(high - low) > EPS) {
            int middle = (low + high) / 2;
            if (can(middle)) {
                high = middle;
            } else {
                low = middle + 1;
            }
        }
        System.out.println(high);
    }

    private static boolean can(int x) {
        return x >= 999;
    }
}
