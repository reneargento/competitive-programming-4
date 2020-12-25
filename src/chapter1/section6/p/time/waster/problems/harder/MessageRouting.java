package chapter1.section6.p.time.waster.problems.harder;

import java.util.*;

/**
 * Created by Rene Argento on 24/12/20.
 */
public class MessageRouting {

    private static class MTAEntry {
        String MTAName;
        String country;
        String administrativeManagementDomain;
        String privateManagementDomain;
        String organizationName;

        MTAEntry(List<String> values) {
            MTAName = values.get(0);
            country = values.get(1);
            administrativeManagementDomain = values.get(2);
            privateManagementDomain = values.get(3);
            organizationName = values.get(4);
        }

        private boolean matches(MTAEntry values) {
            return (values.country.equals(country) || country.equals("*")) &&
                    (values.administrativeManagementDomain.equals(administrativeManagementDomain)
                            || administrativeManagementDomain.equals("*")) &&
                    (values.privateManagementDomain.equals(privateManagementDomain)
                            || privateManagementDomain.equals("*")) &&
                    (values.organizationName.equals(organizationName) || organizationName.equals("*"));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int scenarioId = 1;

        while (scanner.hasNext()) {
            System.out.printf("Scenario # %d\n", scenarioId);
            int MTAs = scanner.nextInt();
            scanner.nextLine();
            Map<String, List<MTAEntry>> MTAMap = new HashMap<>();

            for (int i = 0; i < MTAs; i++) {
                List<String> MTAData = getWords(scanner.nextLine());
                String name = MTAData.get(0);
                int entriesNumber = Integer.parseInt(MTAData.get(1));
                List<MTAEntry> entries = new ArrayList<>();

                for (int e = 0; e < entriesNumber; e++) {
                    List<String> entryData = getWords(scanner.nextLine());
                    MTAEntry entry = new MTAEntry(entryData);
                    entries.add(entry);
                }
                MTAMap.put(name, entries);
            }

            int messages = Integer.parseInt(scanner.nextLine());
            for (int m = 1; m <= messages; m++) {
                List<String> messageData = getWords(scanner.nextLine());
                String startMTA = messageData.get(0);
                MTAEntry message = new MTAEntry(messageData);
                routeMessage(startMTA, message, MTAMap, m);
            }
            scenarioId++;
            System.out.println();
        }
    }

    private static void routeMessage(String currentMTA, MTAEntry message, Map<String, List<MTAEntry>> MTAMap,
                                     int messageId) {
        Set<String> visitedMTAs = new HashSet<>();
        while (true) {
            String MTAToProcess = currentMTA;
            if (visitedMTAs.contains(currentMTA)) {
                System.out.printf("%d -- circular routing detected by %s\n", messageId, currentMTA);
                break;
            }

            boolean routed = false;
            boolean delivered = false;
            for (MTAEntry entry : MTAMap.get(currentMTA)) {
                if (entry.matches(message)) {
                    if (currentMTA.equals(entry.MTAName)) {
                        System.out.printf("%d -- delivered to %s\n", messageId, currentMTA);
                        delivered = true;
                        break;
                    }
                    currentMTA = entry.MTAName;
                    routed = true;
                    break;
                }
            }

            if (delivered) {
                break;
            }
            if (!routed) {
                System.out.printf("%d -- unable to route at %s\n", messageId, currentMTA);
                break;
            }
            visitedMTAs.add(MTAToProcess);
        }
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(character);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
    }
}
