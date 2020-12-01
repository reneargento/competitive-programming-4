package chapter1.section6.j.roman.numerals;

import java.util.*;

/**
 * Created by Rene Argento on 23/11/20.
 */
// UVA-185
public class RomanNumerals {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String equation = scanner.next();
        Map<String, Integer> romanToArabicMap = createRomanToArabicMap();

        while (!equation.equals("#")) {
            int plusIndex = equation.indexOf('+');
            int equalSign = equation.indexOf('=');

            String romanNumeral1 = equation.substring(0, plusIndex);
            String romanNumeral2 = equation.substring(plusIndex + 1, equalSign);;
            String romanResult = equation.substring(equalSign + 1);

            int arabicNumber1 = getArabicNumeral(romanNumeral1, romanToArabicMap);
            int arabicNumber2 = getArabicNumeral(romanNumeral2, romanToArabicMap);
            int arabicNumberResult = getArabicNumeral(romanResult, romanToArabicMap);

            boolean correct = (arabicNumber1 + arabicNumber2) == arabicNumberResult;
            int arabicSums = countArabicSums(romanNumeral1, romanNumeral2, romanResult);

            System.out.print(correct ? "Correct " : "Incorrect ");
            if (arabicSums == 0) {
                System.out.println("impossible");
            } else if (arabicSums == 1) {
                System.out.println("valid");
            } else {
                System.out.println("ambiguous");
            }
            equation = scanner.next();
        }
    }

    private static int countArabicSums(String romanNumeral1, String romanNumeral2, String romanResult) {
        Set<Character> symbols = new HashSet<>();
        addSymbols(symbols, romanNumeral1);
        addSymbols(symbols, romanNumeral2);
        addSymbols(symbols, romanResult);

        Set<Character> nonZeroes = new HashSet<>();
        nonZeroes.add(romanNumeral1.charAt(0));
        nonZeroes.add(romanNumeral2.charAt(0));
        nonZeroes.add(romanResult.charAt(0));

        List<Character> symbolsList = new ArrayList<>(symbols);
        return countArabicSums(romanNumeral1, romanNumeral2, romanResult, symbolsList, new HashMap<>(), 0,
                new HashSet<>(), nonZeroes);
    }

    private static int countArabicSums(String romanNumeral1, String romanNumeral2, String romanResult,
                                       List<Character> symbolsList, Map<Character, Integer> symbolMap, int index,
                                       Set<Integer> usedNumbers, Set<Character> nonZeroes) {
        if (index == symbolsList.size()) {
            return isValidSum(romanNumeral1, romanNumeral2, romanResult, symbolMap) ? 1 : 0;
        }
        int sum = 0;
        char currentSymbol = symbolsList.get(index);

        for (int number = 0; number <= 9; number++) {
            if (number == 0 && nonZeroes.contains(currentSymbol)) {
                continue;
            }

            if (usedNumbers.contains(number)) {
                continue;
            }
            usedNumbers.add(number);

            symbolMap.put(currentSymbol, number);
            sum += countArabicSums(romanNumeral1, romanNumeral2, romanResult, symbolsList, symbolMap, index + 1,
                    usedNumbers, nonZeroes);
            usedNumbers.remove(number);
        }
        return sum;
    }

    private static boolean isValidSum(String romanNumeral1, String romanNumeral2, String romanResult,
                                      Map<Character, Integer> symbolMap) {
        int number1 = getNumber(romanNumeral1, symbolMap);
        int number2 = getNumber(romanNumeral2, symbolMap);
        int result = getNumber(romanResult, symbolMap);
        return (number1 + number2) == result;
    }

    private static int getNumber(String romanNumeral, Map<Character, Integer> symbolMap) {
        StringBuilder number = new StringBuilder();
        for (Character symbol : romanNumeral.toCharArray()) {
            number.append(symbolMap.get(symbol));
        }
        return Integer.parseInt(number.toString());
    }

    private static void addSymbols(Set<Character> symbols, String romanNumeral) {
        for (char character : romanNumeral.toCharArray()) {
            symbols.add(character);
        }
    }

    private static Map<String, Integer> createRomanToArabicMap() {
        Map<String, Integer> romanToArabicMap = new HashMap<>();
        romanToArabicMap.put("M", 1000);
        romanToArabicMap.put("IM", 999);
        romanToArabicMap.put("VM", 995);
        romanToArabicMap.put("XM", 990);
        romanToArabicMap.put("LM", 950);
        romanToArabicMap.put("CM", 900);
        romanToArabicMap.put("D", 500);
        romanToArabicMap.put("ID", 499);
        romanToArabicMap.put("VD", 495);
        romanToArabicMap.put("XD", 490);
        romanToArabicMap.put("LD", 450);
        romanToArabicMap.put("CD", 400);
        romanToArabicMap.put("C", 100);
        romanToArabicMap.put("IC", 99);
        romanToArabicMap.put("VC", 95);
        romanToArabicMap.put("XC", 90);
        romanToArabicMap.put("L", 50);
        romanToArabicMap.put("IL", 49);
        romanToArabicMap.put("VL", 45);
        romanToArabicMap.put("XL", 40);
        romanToArabicMap.put("X", 10);
        romanToArabicMap.put("IX", 9);
        romanToArabicMap.put("V", 5);
        romanToArabicMap.put("IV", 4);
        romanToArabicMap.put("I", 1);
        return romanToArabicMap;
    }

    private static int getArabicNumeral(String romanNumeral, Map<String, Integer> romanToArabicMap) {
        int arabicNumeral = 0;

        for (int i = 0; i < romanNumeral.length(); i++) {
            String symbol = String.valueOf(romanNumeral.charAt(i));

            if (i != romanNumeral.length() - 1) {
                String compositeSymbol = symbol + romanNumeral.charAt(i + 1);
                if (romanToArabicMap.containsKey(compositeSymbol)) {
                    arabicNumeral += romanToArabicMap.get(compositeSymbol);
                    i++;
                } else {
                    arabicNumeral += romanToArabicMap.get(symbol);
                }
            } else {
                arabicNumeral += romanToArabicMap.get(symbol);
            }
        }
        return arabicNumeral;
    }
}
