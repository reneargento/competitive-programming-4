package chapter1.section3.section4.exercise1;

import java.time.LocalDate;

/**
 * Created by Rene Argento on 28/08/20.
 */
public class Task3 {

    public static void main(String[] args) {
        LocalDate date = LocalDate.parse("2010-08-09");
        String dayOfWeek = date.getDayOfWeek().toString();
        System.out.println(dayOfWeek.charAt(0) + dayOfWeek.substring(1).toLowerCase());

        long daysElapsed = LocalDate.now().toEpochDay() - date.toEpochDay();
        System.out.println(daysElapsed + " day(s) ago");
    }

}
