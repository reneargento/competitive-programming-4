package chapter1.section6.i.time.harder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Created by Rene Argento on 18/11/20.
 */
public class CancerOrScorpio {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            String date = scanner.next();
            int month = Integer.parseInt(date.substring(0, 2));
            int day = Integer.parseInt(date.substring(2, 4));
            int year = Integer.parseInt(date.substring(4));
            LocalDate localDate = getLocalDate(day, month, year);
            localDate = localDate.plusDays(280);

            String zodiacSign = getZodiacSign(localDate);
            System.out.printf("%d %02d/%02d/%d %s\n", t, localDate.getMonthValue(), localDate.getDayOfMonth(),
                    localDate.getYear(), zodiacSign);
        }
    }

    private static String getZodiacSign(LocalDate date) {
        int year = date.getYear();
        LocalDate aquariusStart = LocalDate.parse("20-01-" + year, dateTimeFormatter);
        LocalDate aquariusEnd = LocalDate.parse("20-02-" + year, dateTimeFormatter);
        LocalDate piscesStart = aquariusEnd.minusDays(1);
        LocalDate piscesEnd = LocalDate.parse("21-03-" + year, dateTimeFormatter);
        LocalDate ariesStart = piscesEnd.minusDays(1);
        LocalDate ariesEnd = LocalDate.parse("21-04-" + year, dateTimeFormatter);
        LocalDate taurusStart = ariesEnd.minusDays(1);
        LocalDate taurusEnd = LocalDate.parse("22-05-" + year, dateTimeFormatter);
        LocalDate geminiStart = taurusEnd.minusDays(1);
        LocalDate geminiEnd = LocalDate.parse("22-06-" + year, dateTimeFormatter);
        LocalDate cancerStart = geminiEnd.minusDays(1);
        LocalDate cancerEnd = LocalDate.parse("23-07-" + year, dateTimeFormatter);
        LocalDate leoStart = cancerEnd.minusDays(1);
        LocalDate leoEnd = LocalDate.parse("22-08-" + year, dateTimeFormatter);
        LocalDate virgoStart = leoEnd.minusDays(1);
        LocalDate virgoEnd = LocalDate.parse("24-09-" + year, dateTimeFormatter);
        LocalDate libraStart = virgoEnd.minusDays(1);
        LocalDate libraEnd = LocalDate.parse("24-10-" + year, dateTimeFormatter);
        LocalDate scorpioStart = libraEnd.minusDays(1);
        LocalDate scorpioEnd = LocalDate.parse("23-11-" + year, dateTimeFormatter);
        LocalDate sagittariusStart = scorpioEnd.minusDays(1);
        LocalDate sagittariusEnd = LocalDate.parse("23-12-" + year, dateTimeFormatter);

        if (aquariusStart.isBefore(date) && date.isBefore(aquariusEnd)) {
            return "aquarius";
        } else if (piscesStart.isBefore(date) && date.isBefore(piscesEnd)) {
            return "pisces";
        } else if (ariesStart.isBefore(date) && date.isBefore(ariesEnd)) {
            return "aries";
        } else if (taurusStart.isBefore(date) && date.isBefore(taurusEnd)) {
            return "taurus";
        } else if (geminiStart.isBefore(date) && date.isBefore(geminiEnd)) {
            return "gemini";
        } else if (cancerStart.isBefore(date) && date.isBefore(cancerEnd)) {
            return "cancer";
        } else if (leoStart.isBefore(date) && date.isBefore(leoEnd)) {
            return "leo";
        } else if (virgoStart.isBefore(date) && date.isBefore(virgoEnd)) {
            return "virgo";
        } else if (libraStart.isBefore(date) && date.isBefore(libraEnd)) {
            return "libra";
        } else if (scorpioStart.isBefore(date) && date.isBefore(scorpioEnd)) {
            return "scorpio";
        } else if (sagittariusStart.isBefore(date) && date.isBefore(sagittariusEnd)) {
            return "sagittarius";
        }
        return "capricorn";
    }

    private static LocalDate getLocalDate(int day, int month, int year) {
        String formattedDay = String.format("%02d", day);
        String formattedMonth = String.format("%02d", month);
        String date = formattedDay + "-" + formattedMonth + "-" + year;
        return LocalDate.parse(date, dateTimeFormatter);
    }
}
