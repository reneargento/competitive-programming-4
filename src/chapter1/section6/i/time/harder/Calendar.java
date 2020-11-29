package chapter1.section6.i.time.harder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rene Argento on 20/11/20.
 */
public class Calendar {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static class Anniversary implements Comparable<Anniversary> {
        LocalDate date;
        int priority;
        String event;
        int numberOfStars;
        int index;

        public Anniversary(LocalDate date, int priority, String event, int index) {
            this.date = date;
            this.priority = priority;
            this.event = event;
            this.index = index;
        }

        @Override
        public int compareTo(Anniversary other) {
            if (numberOfStars != other.numberOfStars) {
                return other.numberOfStars - numberOfStars;
            }
            return index - other.index;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int year = scanner.nextInt();
        scanner.nextLine();
        String input = scanner.nextLine();
        int blockNumber = 1;

        List<Anniversary> anniversaries = new ArrayList<>();

        while (!input.equals("#")) {
            String[] values = cleanInput(input);
            int day = Integer.parseInt(values[1]);
            int month = Integer.parseInt(values[2]);
            LocalDate date = getLocalDate(day, month, year);

            if (values[0].equals("A")) {
                int priority = Integer.parseInt(values[3]);
                Anniversary anniversary = new Anniversary(date, priority, values[4], anniversaries.size());
                anniversaries.add(anniversary);
            } else {
                if (blockNumber > 1) {
                    System.out.println();
                }
                showEvents(date, anniversaries);
                blockNumber++;
            }
            input = scanner.nextLine();
        }
    }

    private static void showEvents(LocalDate date, List<Anniversary> anniversaries) {
        System.out.printf("Today is:%3d%3d\n", date.getDayOfMonth(), date.getMonthValue());

        for (int daysAhead = 0; daysAhead <= 7; daysAhead++) {
            List<Anniversary> eventsToPrint = new ArrayList<>();
            LocalDate newDate = date.plusDays(daysAhead);

            for (Anniversary anniversary : anniversaries) {
                LocalDate nextYearAnniversary = anniversary.date.plusYears(1);

                boolean shouldPrintCurrentYear = ChronoUnit.DAYS.between(date, anniversary.date) <= anniversary.priority;
                boolean shouldPrintNextYear = ChronoUnit.DAYS.between(date, nextYearAnniversary) <= anniversary.priority;

                if ((anniversary.date.equals(newDate) && shouldPrintCurrentYear)
                        || (nextYearAnniversary.equals(newDate) && shouldPrintNextYear)) {
                    anniversary.numberOfStars = (daysAhead == 0) ? 10 : anniversary.priority - daysAhead + 1;
                    eventsToPrint.add(anniversary);
                }
            }

            Collections.sort(eventsToPrint);

            for (Anniversary anniversary : eventsToPrint) {
                LocalDate anniversaryDate = anniversary.date;
                String priorityStars = getStars(anniversary.numberOfStars, daysAhead);
                System.out.printf("%3d%3d %-7s %s\n", anniversaryDate.getDayOfMonth(), anniversaryDate.getMonthValue(),
                        priorityStars, anniversary.event);
            }
        }
    }

    private static String getStars(int numberOfStars, int daysAhead) {
        if (daysAhead == 0) {
            return "*TODAY*";
        }
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < numberOfStars; i++) {
            stars.append("*");
        }
        return stars.toString();
    }

    private static String[] cleanInput(String input) {
        int size = input.charAt(0) == 'A' ? 5 : 3;
        String[] cleanInput = new String[size];
        int index = 0;
        StringBuilder data = new StringBuilder();
        boolean eventStarted = false;

        for (int c = 0; c < input.length(); c++) {
            char character = input.charAt(c);
            if (character != ' ' || eventStarted) {
                if (index == 4) {
                    eventStarted = true;
                }
                data.append(character);
            } else {
                if (data.length() > 0) {
                    cleanInput[index++] = data.toString();
                    data = new StringBuilder();
                }
            }
        }
        if (data.length() > 0) {
            cleanInput[index] = data.toString();
        }
        return cleanInput;
    }

    private static LocalDate getLocalDate(int day, int month, int year) {
        String formattedDay = String.format("%02d", day);
        String formattedMonth = String.format("%02d", month);
        String date = formattedDay + "-" + formattedMonth + "-" + year;
        return LocalDate.parse(date, dateTimeFormatter);
    }
}
