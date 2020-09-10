package chapter1.section3.section4.exercise1;

/**
 * Created by Rene Argento on 29/08/20.
 */
public class Task10 {

    public static void main(String[] args) {
        String string = "line: a70 and z72 will be replaced, aa24 and a872 will not";
        System.out.println(string.replaceAll("\\b+[a-z][0-9]{2}\\b+", "***"));
    }

}
