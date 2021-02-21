package chapter2.section2.e.sorting.easier;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 13/02/21.
 */
public class Birthdates {

    private static class Person implements Comparable<Person> {
        String name;
        int birthdayDay;
        int birthdayMonth;
        int birthdayYear;

        public Person(String name, int birthdayDay, int birthdayMonth, int birthdayYear) {
            this.name = name;
            this.birthdayDay = birthdayDay;
            this.birthdayMonth = birthdayMonth;
            this.birthdayYear = birthdayYear;
        }

        @Override
        public int compareTo(Person other) {
            if (birthdayYear != other.birthdayYear) {
                return other.birthdayYear - birthdayYear;
            }
            if (birthdayMonth != other.birthdayMonth) {
                return other.birthdayMonth - birthdayMonth;
            }
            return other.birthdayDay - birthdayDay;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int peopleNumber = scanner.nextInt();
        Person[] people = new Person[peopleNumber];

        for (int p = 0; p < people.length; p++) {
            people[p] = new Person(scanner.next(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
        }
        Arrays.sort(people);

        System.out.println(people[0].name);
        System.out.println(people[people.length - 1].name);
    }
}
