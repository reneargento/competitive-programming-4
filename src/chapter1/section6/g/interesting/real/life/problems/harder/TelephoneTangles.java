package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Rene Argento on 09/11/20.
 */
public class TelephoneTangles {

    private static class Local {
        String name;
        double centsPerMinute;

        public Local(String name, double centsPerMinute) {
            this.name = name;
            this.centsPerMinute = centsPerMinute;
        }
    }

    private static class Call {
        Local local;
        String subscriberNumber;

        public Call(Local local, String subscriberNumber) {
            this.local = local;
            this.subscriberNumber = subscriberNumber;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        Map<String, Local> localMap = new HashMap<>();

        while (!line.equals("000000")) {
            String[] data = getPhoneAndRestOfInput(line);
            String[] nameAndPrice = data[1].split("\\$");
            Local local = new Local(nameAndPrice[0], Double.parseDouble(nameAndPrice[1]));
            localMap.put(data[0], local);

            line = scanner.nextLine();
        }

        line = scanner.nextLine();
        while (!line.equals("#")) {
            String[] data = getCleanInput(line).split(" ");
            String phoneNumber = data[0];
            int duration;
            if (data.length > 1) {
                duration = Integer.parseInt(data[1]);
            } else {
                duration = scanner.nextInt();
                scanner.nextLine();
            }
            Call call = getCallData(phoneNumber, localMap);
            printCall(phoneNumber, duration, call);
            line = scanner.nextLine();
        }
    }

    private static String[] getPhoneAndRestOfInput(String input) {
        StringBuilder phoneNumber = new StringBuilder();
        String rest = null;

        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);

            if (character != ' ') {
                phoneNumber.append(character);
            } else {
                rest = input.substring(i + 1);
                break;
            }
        }
        return new String[]{phoneNumber.toString(), rest};
    }

    private static String getCleanInput(String input) {
        boolean addedSpace = false;
        StringBuilder cleanInput = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);

            if (character == ' ') {
                if (!addedSpace) {
                    cleanInput.append(character);
                    addedSpace = true;
                }
            } else {
                cleanInput.append(character);
            }
        }
        return cleanInput.toString().trim();
    }

    private static Call getCallData(String phoneNumber, Map<String, Local> localMap) {
        if (phoneNumber.charAt(0) != '0') {
            return new Call(new Local("Local", 0), phoneNumber);
        }

        Call call;
        int minPhoneNumberLength;
        int maxPhoneNumberLength;
        int minSubscriberNumberLength = 4;
        int maxSubscriberNumberLength;

        if (phoneNumber.length() >= 2
                && phoneNumber.charAt(0) == '0'
                && phoneNumber.charAt(1) == '0') {
            minPhoneNumberLength = 7;
            maxPhoneNumberLength = 15;
            maxSubscriberNumberLength = 10;
        } else {
            minPhoneNumberLength = 6;
            maxPhoneNumberLength = 13;
            maxSubscriberNumberLength = 7;
        }

        int phoneNumberLength = phoneNumber.length();
        if (minPhoneNumberLength <= phoneNumberLength && phoneNumberLength <= maxPhoneNumberLength) {
            call = getNumber(phoneNumber, localMap, minSubscriberNumberLength, maxSubscriberNumberLength);
            if (call != null) {
                return call;
            }
        }
        return new Call(new Local("Unknown", -1), "");
    }

    private static Call getNumber(String phoneNumber, Map<String, Local> localMap, int minSubscriberNumber,
                                  int maxSubscriberNumber) {
        StringBuilder prefixNumber = new StringBuilder();

        for (int i = 0; i < phoneNumber.length(); i++) {
            prefixNumber.append(phoneNumber.charAt(i));
            String prefixNumberValue = prefixNumber.toString();

            if (localMap.containsKey(prefixNumberValue)) {
                String subscriberNumber = phoneNumber.substring(i + 1);
                if (subscriberNumber.length() < minSubscriberNumber) {
                    break;
                }
                if (subscriberNumber.length() > maxSubscriberNumber) {
                    continue;
                }

                Local local = localMap.get(prefixNumberValue);
                return new Call(local, subscriberNumber);
            }
        }
        return null;
    }

    private static void printCall(String phoneNumber, int duration, Call call) {
        String costPerMinute;
        String totalCost;
        Local local = call.local;

        if (call.subscriberNumber.equals("")) {
            costPerMinute = "";
            totalCost = "-1.00";
        } else {
            double costPerMinuteValue = local.centsPerMinute / 100;
            double totalCostValue = duration * costPerMinuteValue;
            costPerMinute = String.format("%.2f", costPerMinuteValue);
            totalCost = String.format("%.2f", totalCostValue);
        }
        System.out.printf("%-15s %-24s %10s %4s %5s %6s\n", phoneNumber, local.name, call.subscriberNumber, duration,
                costPerMinute, totalCost);
    }
}
