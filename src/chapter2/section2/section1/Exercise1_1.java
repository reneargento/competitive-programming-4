package chapter2.section2.section1;

import java.util.Arrays;

/**
 * Created by Rene Argento on 29/12/20.
 */
public class Exercise1_1 {

    private static class Tuple {
        int age;
        String firstName;
        String lastName;

        public Tuple(int age, String firstName, String lastName) {
            this.age = age;
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    public static void main(String[] args) {
        Tuple tuple1 = new Tuple(31, "Rene", "Argento");
        Tuple tuple2 = new Tuple(31, "Ab", "Cd");
        Tuple tuple3 = new Tuple(31, "Aa", "Cd");
        Tuple tuple4 = new Tuple(20, "Test", "LastName");
        Tuple[] values = { tuple1, tuple2, tuple3, tuple4 };
        sort(values);

        for (Tuple tuple : values) {
            System.out.printf("%d %s %s\n", tuple.age, tuple.firstName, tuple.lastName);
        }
    }

    private static void sort(Tuple[] values) {
        Arrays.sort(values, (tuple1, tuple2) -> {
            if (tuple1.age != tuple2.age) {
                return tuple1.age - tuple2.age;
            }
            if (!tuple1.lastName.equals(tuple2.lastName)) {
                return tuple2.lastName.compareTo(tuple1.lastName);
            }
            return tuple1.firstName.compareTo(tuple2.firstName);
        });
    }
}
