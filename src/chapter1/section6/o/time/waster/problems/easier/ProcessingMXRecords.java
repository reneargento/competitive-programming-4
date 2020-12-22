package chapter1.section6.o.time.waster.problems.easier;

import java.util.*;

/**
 * Created by Rene Argento on 18/12/20.
 */
public class ProcessingMXRecords {

    private static class Machine implements Comparable<Machine>{
        String name;
        int priority;

        public Machine(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }

        @Override
        public int compareTo(Machine otherMachine) {
            return priority - otherMachine.priority;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int records = scanner.nextInt();
        scanner.nextLine();

        Map<String, List<Machine>> redirectMap = new HashMap<>();
        Map<String, Boolean> machineAvailabilityMap = new HashMap<>();
        String previousName = null;

        for (int i = 0; i < records; i++) {
            String recordData = scanner.nextLine();
            List<String> cleanRecordData = getWords(recordData);
            int priority;
            String targetRedirect;

            if (cleanRecordData.size() == 3) {
                String name = cleanRecordData.get(0);
                machineAvailabilityMap.put(name, true);
                priority = Integer.parseInt(cleanRecordData.get(1));
                targetRedirect = cleanRecordData.get(2);
                previousName = name;
            } else {
                priority = Integer.parseInt(cleanRecordData.get(0));
                targetRedirect = cleanRecordData.get(1);
            }
            machineAvailabilityMap.put(targetRedirect, true);
            Machine machine = new Machine(targetRedirect, priority);

            if (!redirectMap.containsKey(previousName)) {
                redirectMap.put(previousName, new ArrayList<>());
            }
            redirectMap.get(previousName).add(machine);
        }

        while (scanner.hasNextLine()) {
            String eventData = scanner.nextLine();
            List<String> cleanEventData = getWords(eventData);
            String event = cleanEventData.get(0);
            if (event.equals("X")) {
                break;
            }
            String machineName = cleanEventData.get(1);

            switch (event) {
                case "A":
                    String redirect = getRedirect(machineName, redirectMap, machineAvailabilityMap);
                    System.out.println(machineName + " => " + redirect);
                    break;
                case "U":
                    machineAvailabilityMap.put(machineName, true);
                    break;
                default: machineAvailabilityMap.put(machineName, false);
            }
        }
    }

    private static String getRedirect(String machineName, Map<String, List<Machine>> redirectMap,
                                      Map<String, Boolean> machineAvailabilityMap) {
        String redirect = "";

        List<Machine> redirectMachines = new ArrayList<>();
        if (redirectMap.containsKey(machineName)) {
            redirectMachines.addAll(redirectMap.get(machineName));
        }

        List<String> wildcardVariations = getWildcardVariations(machineName);
        for (String wildcardVariation : wildcardVariations) {
            if (redirectMap.containsKey(wildcardVariation)) {
                redirectMachines.addAll(redirectMap.get(wildcardVariation));
            }
        }
        if (redirectMachines.isEmpty()) {
            return redirect;
        }

        Collections.sort(redirectMachines);
        for (Machine machine : redirectMachines) {
            if (machineAvailabilityMap.get(machine.name)) {
                return machine.name;
            }
        }
        return redirect;
    }

    private static List<String> getWildcardVariations(String machineName) {
        List<String> wildcardVariations = new ArrayList<>();

        while (machineName.contains(".")) {
            int indexOfFirstPeriod = machineName.indexOf(".");
            String wildcardVariation = "*" + machineName.substring(indexOfFirstPeriod);
            wildcardVariations.add(wildcardVariation);
            machineName = machineName.substring(indexOfFirstPeriod + 1);
        }
        return wildcardVariations;
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
