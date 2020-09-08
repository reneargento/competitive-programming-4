package chapter1.section3.section4.exercise1;

/**
 * Created by Rene Argento on 29/08/20.
 */
public class Task8 {

    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        generateSubsets(numbers);
    }

    // O(2^N)
    private static void generateSubsets(int[] numbers) {
        for (int subsetId = 0; subsetId < (1 << numbers.length); subsetId++) {
            int bit = 0;

            for (int id = subsetId; id > 0; id >>= 1) {
                if ((id & 1) == 1) {
                    System.out.print(numbers[bit] + " ");
                }
                bit++;
            }
            System.out.println();
        }
    }
}
