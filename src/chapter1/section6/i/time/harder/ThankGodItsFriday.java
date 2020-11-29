package chapter1.section6.i.time.harder;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 22/11/20.
 */
public class ThankGodItsFriday {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int day = scanner.nextInt();
        String monthString = scanner.next();
        String weekDayString = scanner.next();

        String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        String[] weekDays = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};

        Map<String, Integer> monthMap = getIndexedMap(months);
        Map<String, Integer> weekDaysMap = getIndexedMap(weekDays);

        int month = monthMap.get(monthString);
        int daysSinceJan1 = getNumberOfDays(day, month);

        int weekDayStartingMonday = daysSinceJan1 % 7;

        int weekDay = weekDaysMap.get(weekDayString);
        int finalWeekDay = (weekDayStartingMonday + weekDay) % 7;

        if ((month == 0 || (month == 1 && day <= 29))) {
            if (finalWeekDay == 4) {
                System.out.println("TGIF");
            } else {
                System.out.println(":(");
            }
        } else if (finalWeekDay == 3 || finalWeekDay == 4) {
            System.out.println("not sure");
        } else {
            System.out.println(":(");
        }
    }

    private static int getNumberOfDays(int day, int month) {
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int daysSinceJan1 = 0;

        for (int m = 0; m < month; m++) {
            daysSinceJan1 += daysInMonth[m];
        }
        daysSinceJan1 += (day - 1);
        return daysSinceJan1;
    }

    private static Map<String, Integer> getIndexedMap(String[] values) {
        Map<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            indexMap.put(values[i], i);
        }
        return indexMap;
    }
}
