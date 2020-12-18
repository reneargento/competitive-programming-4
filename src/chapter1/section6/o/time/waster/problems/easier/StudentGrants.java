package chapter1.section6.o.time.waster.problems.easier;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Rene Argento on 17/12/20.
 */
public class StudentGrants {

    private static class Student {
        int id;
        int paid;

        public Student(int id) {
            this.id = id;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int studentsNumber = scanner.nextInt();
        int k = scanner.nextInt();

        while (studentsNumber != 0 || k != 0) {
            LinkedList<Student> students = initializeStudents(studentsNumber);
            payStudents(students, k);
            studentsNumber = scanner.nextInt();
            k = scanner.nextInt();
        }
    }

    private static LinkedList<Student> initializeStudents(int studentsNumber) {
        LinkedList<Student> students = new LinkedList<>();
        for (int i = 0; i < studentsNumber; i++) {
            students.offer(new Student(i + 1));
        }
        return students;
    }

    private static void payStudents(LinkedList<Student> students, int k) {
        int extraCoins = 0;
        int increment = 1;

        while (!students.isEmpty()) {
            Student student = students.poll();
            if (extraCoins > 0) {
                student.paid += extraCoins;
                extraCoins = 0;
            } else {
                student.paid += increment;
            }

            if (student.paid >= 40) {
                if (student.paid > 40) {
                    extraCoins = student.paid - 40;
                }
                System.out.printf("%3d", student.id);
            } else {
                students.offer(student);
            }

            if (extraCoins == 0) {
                increment++;
                if (increment > k) {
                    increment = 1;
                }
            }
        }
        System.out.println();
    }
}
