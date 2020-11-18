package chapter1.section4.i.still.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 18/09/20.
 */
public class PeaSoupAndPancakes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int restaurants = scanner.nextInt();
        String restaurantChosen = null;

        for (int r = 0; r < restaurants; r++) {
            int items = scanner.nextInt();
            scanner.nextLine();
            String name = scanner.nextLine();
            boolean hasPeaSoup = false;
            boolean hasPancakes = false;

            for (int i = 0; i < items; i++) {
                String item = scanner.nextLine();

                if (item.equals("pea soup")) {
                    hasPeaSoup = true;
                    if (hasPancakes) {
                        restaurantChosen = name;
                        break;
                    }
                } else if (item.equals("pancakes")) {
                    hasPancakes = true;
                    if (hasPeaSoup) {
                        restaurantChosen = name;
                        break;
                    }
                }
            }
            if (restaurantChosen != null) {
                break;
            }
        }
        System.out.println(restaurantChosen != null ? restaurantChosen : "Anywhere is fine I guess");
    }

}
