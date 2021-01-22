package chapter2.section2.section1;

/**
 * Created by Rene Argento on 09/01/21.
 */
public class Exercise3_7_Majority {

    public static void main(String[] args) {
        int[] array = { 1, 2, 3, 2, 2, 1, 4, 2, 2, 2, 1 };
        Integer majorityElement = getMajorityElement(array);

        if (majorityElement != null) {
            System.out.println("Majority element: " + majorityElement);
        } else {
            System.out.println("There is no majority element.");
        }
        System.out.println("Expected: 2");
    }

    private static Integer getMajorityElement(int[] array) {
        if (array.length == 0) {
            return null;
        }

        int majorityElement = array[0];
        int count = 1;

        for (int i = 1; i < array.length; i++) {
            if (array[i] == majorityElement) {
                count++;
            } else {
                count--;
            }

            if (count == 0) {
                majorityElement = array[i];
                count = 1;
            }
        }

        int majorityCount = 0;
        for (int value : array) {
            if (value == majorityElement) {
                majorityCount++;
            }
        }

        if (majorityCount > (array.length / 2)) {
            return majorityElement;
        } else {
            return null;
        }
    }
}
