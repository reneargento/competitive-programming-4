package chapter1.section4.h.easy;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class ASCIIArea {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            int height = scanner.nextInt();
            int width = scanner.nextInt();
            int area = 0;

            for (int h = 0; h < height; h++) {
                String line = scanner.next();
                boolean inside = false;
                int start = -1;

                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) != '.') {
                        if (inside) {
                            area += i - start;
                        } else {
                            start = i;
                        }
                        inside = !inside;
                    }
                }
            }
            System.out.println(area);
        }
    }

}
