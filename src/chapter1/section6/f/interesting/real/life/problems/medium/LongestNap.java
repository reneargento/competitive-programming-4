package chapter1.section6.f.interesting.real.life.problems.medium;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 06/11/20.
 */
public class LongestNap {

    private static class Appointment implements Comparable<Appointment> {
        int startHours;
        int startMinutes;
        int endHours;
        int endMinutes;

        public Appointment(int startHours, int startMinutes, int endHours, int endMinutes) {
            this.startHours = startHours;
            this.startMinutes = startMinutes;
            this.endHours = endHours;
            this.endMinutes = endMinutes;
        }

        @Override
        public int compareTo(Appointment other) {
            if (startHours != other.startHours) {
                return startHours - other.startHours;
            }
            if (startMinutes != other.startMinutes) {
                return startMinutes - other.startMinutes;
            }
            if (endHours != other.endHours) {
                return endHours - other.endHours;
            }
            return endMinutes - other.endMinutes;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int day = 1;

        while (scanner.hasNext()) {
            int appointmentsCount = scanner.nextInt();
            scanner.nextLine();

            Appointment[] appointments = new Appointment[appointmentsCount + 2];
            appointments[0] = new Appointment(10, 0, 10, 0);

            for (int i = 1; i <= appointmentsCount; i++) {
                String[] values = scanner.nextLine().split( " ");
                String[] start = values[0].split(":");
                int startHours = Integer.parseInt(start[0]);
                int startMinutes = Integer.parseInt(start[1]);

                String[] end = values[1].split(":");
                int endHours = Integer.parseInt(end[0]);
                int endMinutes = Integer.parseInt(end[1]);
                appointments[i] = new Appointment(startHours, startMinutes, endHours, endMinutes);
            }
            appointments[appointments.length - 1] = new Appointment(18, 0, 18, 0);

            int napStartHours = -1;
            int napStartMinutes = -1;
            int maxNapTime = 0;

            Arrays.sort(appointments);

            for (int i = 1; i < appointments.length; i++) {
                Appointment previousAppointment = appointments[i - 1];
                Appointment appointment = appointments[i];

                int napTime = getMinutesDifference(previousAppointment.endHours, previousAppointment.endMinutes,
                        appointment.startHours, appointment.startMinutes);
                if (napTime > maxNapTime) {
                    maxNapTime = napTime;
                    napStartHours = previousAppointment.endHours;
                    napStartMinutes = previousAppointment.endMinutes;
                }
            }

            int napTimeHours = maxNapTime / 60;
            int napTimeMinutes = maxNapTime % 60;
            System.out.printf("Day #%d: the longest nap starts at %d:%02d and will last for ", day, napStartHours,
                    napStartMinutes);
            if (napTimeHours > 0) {
                System.out.printf("%d hours and %d minutes.\n", napTimeHours, napTimeMinutes);
            } else {
                System.out.printf("%d minutes.\n", napTimeMinutes);
            }
            day++;
        }
    }

    private static int getMinutesDifference(int hours1, int minutes1, int hours2, int minutes2) {
        if (hours1 == hours2) {
            return minutes2 - minutes1;
        }
        int minutesDifference = 60 - minutes1;
        minutesDifference += 60 * (hours2 - (hours1 + 1));
        minutesDifference += minutes2;
        return minutesDifference;
    }

}
