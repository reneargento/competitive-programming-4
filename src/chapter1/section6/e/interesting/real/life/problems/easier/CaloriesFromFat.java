package chapter1.section6.e.interesting.real.life.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 01/11/20.
 */
public class CaloriesFromFat {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        int[] caloriesPerGram = { 9, 4, 4, 4, 7 };

        double totalCalories = 0;
        double caloriesFromFat = 0;

        while (!line.equals("-")) {
            String[] data = line.split(" ");

            double currentTotalCalories = 0;
            double currentCaloriesFromFat = 0;
            double percentFromFat = 0;
            double percent = 0;

            for (int i = 0; i < data.length; i++) {
                char type = data[i].charAt(data[i].length() - 1);
                int value = Integer.parseInt(data[i].substring(0, data[i].length() - 1));

                switch (type) {
                    case 'g':
                        int calories = value * caloriesPerGram[i];
                        if (i == 0) {
                            currentCaloriesFromFat += calories;
                        }
                        currentTotalCalories += calories;
                        break;
                    case 'C':
                        if (i == 0) {
                            currentCaloriesFromFat += value;
                        }
                        currentTotalCalories += value;
                        break;
                    default:
                        percent += value;
                        if (i == 0) {
                            percentFromFat += value;
                        }
                }
            }

            if (percent > 0) {
                double originalTotalCalories = currentTotalCalories;

                double remainingPercent = 100 - percent;
                // totalCalories -> remainingPercent
                // ? -> percent
                double otherCalories = currentTotalCalories * percent / remainingPercent;
                currentTotalCalories += otherCalories;

                if (percentFromFat > 0) {
                    double fatCalories = originalTotalCalories * percentFromFat / remainingPercent;
                    currentCaloriesFromFat += fatCalories;
                }
            }

            totalCalories += currentTotalCalories;
            caloriesFromFat += currentCaloriesFromFat;

            line = scanner.nextLine();
            if (line.equals("-")) {
                int caloriesFromFatRounded = (int) Math.round(caloriesFromFat / totalCalories * 100);
                System.out.println(caloriesFromFatRounded + "%");

                totalCalories = 0;
                caloriesFromFat = 0;
                line = scanner.nextLine();
            }
        }
    }
}
