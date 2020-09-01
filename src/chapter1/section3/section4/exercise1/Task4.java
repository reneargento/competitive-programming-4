package chapter1.section3.section4.exercise1;

import java.util.*;

/**
 * Created by Rene Argento on 28/08/20.
 */
public class Task4 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Set<Integer> numbers = new HashSet<>();
        while (scanner.hasNext()) {
            numbers.add(scanner.nextInt());
        }

        List<Integer> numbersList = new ArrayList<>(numbers);
        Collections.sort(numbersList);

        for (Integer number : numbersList) {
            System.out.println(number);
        }
    }

}
