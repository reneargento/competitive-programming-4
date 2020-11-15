package chapter1.section4.g.array1d.manipulation;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 16/09/20.
 */
public class GoogleIsFeelingLucky {

    private static class Website implements Comparable<Website> {
        String url;
        int relevance;

        public Website(String url, int relevance) {
            this.url = url;
            this.relevance = relevance;
        }

        @Override
        public int compareTo(Website other) {
            return other.relevance - relevance;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            Website[] websites = new Website[10];

            for (int i = 0; i < 10; i++) {
                websites[i] = new Website(scanner.next(), scanner.nextInt());
            }

            Arrays.sort(websites);
            int highestRelevance = websites[0].relevance;

            System.out.printf("Case #%d:\n", t);
            for (Website website : websites) {
                if (website.relevance != highestRelevance) {
                    break;
                }
                System.out.println(website.url);
            }
        }
    }

}
