package chapter1.section6.f.interesting.real.life.problems.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 07/11/20.
 */
public class ScalingRecipes {

    private static class Ingredient {
        String name;
        double percentage;

        public Ingredient(String name, double percentage) {
            this.name = name;
            this.percentage = percentage;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int ingredientsCount = scanner.nextInt();
            int portions = scanner.nextInt();
            int desiredPortions = scanner.nextInt();
            double scalingFactor = desiredPortions / (double) portions;

            double referenceWeight = 0;
            Ingredient[] ingredients = new Ingredient[ingredientsCount];
            for (int i = 0; i < ingredientsCount; i++) {
                String name = scanner.next();
                double weight = scanner.nextDouble();
                double percentage = scanner.nextDouble();
                ingredients[i] = new Ingredient(name, percentage);

                if (percentage == 100) {
                    referenceWeight = weight * scalingFactor;
                }
            }
            System.out.printf("Recipe # %d\n", t);
            printRecipe(ingredients, referenceWeight);
        }
    }

    private static void printRecipe(Ingredient[] ingredients, double referenceWeight) {
        for (Ingredient ingredient : ingredients) {
            double weight = referenceWeight * ingredient.percentage / 100;
            System.out.printf("%s %.1f\n", ingredient.name, weight);
        }
        System.out.println("----------------------------------------");
    }
}
