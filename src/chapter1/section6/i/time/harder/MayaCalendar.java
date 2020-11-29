package chapter1.section6.i.time.harder;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 20/11/20.
 */
public class MayaCalendar {

    private static class TzolkinDate {
        int period;
        String name;
        int year;

        public TzolkinDate(int period, String name, int year) {
            this.period = period;
            this.name = name;
            this.year = year;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();

        Map<String, Integer> haabMonthToIndexMap = createHaabMonthMap();
        String[] tzolkinDays = {"imix", "ik", "akbal", "kan", "chicchan", "cimi", "manik", "lamat", "muluk", "ok", "chuen",
                "eb", "ben", "ix", "mem", "cib", "caban", "eznab", "canac", "ahau"};

        System.out.println(tests);
        for (int t = 0; t < tests; t++) {
            String haabString = scanner.nextLine();
            int daysInHaabDate = getDaysInHaabDate(haabString, haabMonthToIndexMap);
            TzolkinDate tzolkinDate = getTzolkinDate(daysInHaabDate, tzolkinDays);
            System.out.printf("%d %s %d\n", tzolkinDate.period, tzolkinDate.name, tzolkinDate.year);
        }
    }

    private static Map<String, Integer> createHaabMonthMap() {
        String[] haabMonths = {"pop", "no", "zip", "zotz", "tzec", "xul", "yoxkin", "mol", "chen", "yax", "zac", "ceh",
                "mac", "kankin", "muan", "pax", "koyab", "cumhu", "uayet"};

        Map<String, Integer> haabMonthToIndexMap = new HashMap<>();
        for (int i = 0; i < haabMonths.length; i++) {
            haabMonthToIndexMap.put(haabMonths[i], i);
        }
        return haabMonthToIndexMap;
    }

    private static int getDaysInHaabDate(String haabString, Map<String, Integer> haabMonthToIndexMap) {
        String[] values = haabString.split(" ");
        int day = Integer.parseInt(values[0].substring(0, values[0].length() - 1));
        int month = haabMonthToIndexMap.get(values[1]);
        int year = Integer.parseInt(values[2]);

        return day + (month * 20) + (year * 365);
    }

    private static TzolkinDate getTzolkinDate(int haabDays, String[] tzolkinDays) {
        int year = haabDays / 260;
        int period = ((haabDays % 260) % 13) + 1;
        int day = (haabDays % 260) % 20;
        return new TzolkinDate(period, tzolkinDays[day], year);
    }
}
