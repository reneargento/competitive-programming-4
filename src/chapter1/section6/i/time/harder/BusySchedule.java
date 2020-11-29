package chapter1.section6.i.time.harder;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 22/11/20.
 */
public class BusySchedule {

    private static class Appointment implements Comparable<Appointment> {
        int hour;
        int minutes;
        int amPm;
        String description;

        public Appointment(int hour, int minutes, int amPm, String description) {
            this.hour = hour == 12 ? -1 : hour;
            this.minutes = minutes;
            this.amPm = amPm;
            this.description = description;
        }

        @Override
        public int compareTo(Appointment other) {
            if (amPm != other.amPm) {
                return amPm - other.amPm;
            }
            if (hour != other.hour) {
                return hour - other.hour;
            }
            return minutes - other.minutes;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int appointmentsCount = scanner.nextInt();
        int caseNumber = 1;

        while (appointmentsCount != 0) {
            Appointment[] appointments = new Appointment[appointmentsCount];

            for (int i = 0; i < appointments.length; i++) {
                String hourMinutes = scanner.next();
                String[] time = hourMinutes.split(":");
                String amPmString = scanner.next();

                int hour = Integer.parseInt(time[0]);
                int minutes = Integer.parseInt(time[1]);
                int amPm = amPmString.charAt(0) == 'a' ? 0 : 1;
                String description = hourMinutes + " " + amPmString;
                appointments[i] = new Appointment(hour, minutes, amPm, description);
            }

            Arrays.sort(appointments);

            if (caseNumber > 1) {
                System.out.println();
            }
            for (Appointment appointment : appointments) {
                System.out.println(appointment.description);
            }

            caseNumber++;
            appointmentsCount = scanner.nextInt();
        }
    }
}
