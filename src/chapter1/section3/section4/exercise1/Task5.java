package chapter1.section3.section4.exercise1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 28/08/20.
 */
public class Task5 {

    private static class Birthday implements Comparable<Birthday> {
        int day, month, year;

        Birthday(int day, int month, int year) {
            this.day = day;
            this.month = month;
            this.year = year;
        }

        @Override
        public int compareTo(Birthday other) {
            if (month != other.month) {
                return month - other.month;
            } else if (day != other.day) {
                return day - other.day;
            }
            return other.year - year;
        }
    }

    public static void main(String[] args) {
        List<Birthday> birthdays = new ArrayList<>();
        birthdays.add(new Birthday(24, 5, 1980));
        birthdays.add(new Birthday(24, 5, 1982));
        birthdays.add(new Birthday(13, 11, 1983));
        Collections.sort(birthdays);

        for (Birthday birthday : birthdays) {
            System.out.println(birthday.day + " " + birthday.month + " " + birthday.year);
        }
    }

}
