package chapter1.section6.f.interesting.real.life.problems.medium;

import java.util.*;

/**
 * Created by Rene Argento on 06/11/20.
 */
public class Quantiser {

    private static class Message {
        int code;
        int note;
        int roundedMeasure;
        int roundedBeat;
        int roundedTick;

        public Message(int code, int note, int measure, int beat, int tick) {
            this.code = code;
            this.note = note;
            quantise(measure, beat, tick);
        }

        private void quantise(int measure, int beat, int tick) {
            int division = tick / 60;
            int remaining = tick % 60;

            roundedTick = division * 60;
            if (remaining >= 30) {
                roundedTick += 60;

                if (roundedTick == 480) {
                    roundedTick = 0;
                    beat++;
                    if (beat == 5) {
                        beat = 1;
                        measure++;
                    }
                }
            }
            roundedBeat = beat;
            roundedMeasure = measure;
        }
    }

    private static class MessageTimes {
        int onMeasureTime = -1;
        int onBeatTime = -1;
        int onTickTime = -1;
        int offMeasureTime = -1;
        int offBeatTime = -1;
        int offTickTime = -1;

        private boolean keepInRecord() {
            if (onMeasureTime == -1 || offMeasureTime == -1) {
                return true;
            }
            return onMeasureTime != offMeasureTime
                    || onBeatTime != offBeatTime
                    || onTickTime != offTickTime;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int messageCount = scanner.nextInt();

        while (messageCount != -1) {
            Message[] messages = new Message[messageCount];
            Map<Integer, Deque<MessageTimes>> messageTimesMap = new HashMap<>();

            for (int m = 0; m < messages.length; m++) {
                int code = scanner.nextInt();
                int note = scanner.nextInt();
                int measure = scanner.nextInt();
                int beat = scanner.nextInt();
                int tick = scanner.nextInt();
                messages[m] = new Message(code, note, measure, beat, tick);

                if (!messageTimesMap.containsKey(note)) {
                    Deque<MessageTimes> messageTimes = new ArrayDeque<>();
                    messageTimesMap.put(note, messageTimes);
                }

                Deque<MessageTimes> messageTimes = messageTimesMap.get(note);
                addMeasuredTime(messageTimes, code, messages[m]);
            }
            printPerformance(messages, messageTimesMap);
            messageCount = scanner.nextInt();
        }
        System.out.println("-1");
    }

    private static void addMeasuredTime(Deque<MessageTimes> messageTimes, int code, Message message) {
        if (messageTimes.isEmpty() || code == 1) {
            MessageTimes messageTime = new MessageTimes();
            if (code == 1) {
                messageTime.onMeasureTime = message.roundedMeasure;
                messageTime.onBeatTime = message.roundedBeat;
                messageTime.onTickTime = message.roundedTick;
            } else {
                messageTime.offMeasureTime = message.roundedMeasure;
                messageTime.offBeatTime = message.roundedBeat;
                messageTime.offTickTime = message.roundedTick;
            }
            messageTimes.push(messageTime);
        } else {
            boolean added = false;
            for (MessageTimes messageTime : messageTimes) {
                if (messageTime.offMeasureTime == -1) {
                    messageTime.offMeasureTime = message.roundedMeasure;
                    messageTime.offBeatTime = message.roundedBeat;
                    messageTime.offTickTime = message.roundedTick;
                    added = true;
                    break;
                }
            }

            if (!added) {
                MessageTimes messageTime = new MessageTimes();
                messageTime.offMeasureTime = message.roundedMeasure;
                messageTime.offBeatTime = message.roundedBeat;
                messageTime.offTickTime = message.roundedTick;
                messageTimes.push(messageTime);
            }
        }
    }

    private static void printPerformance(Message[] messages, Map<Integer, Deque<MessageTimes>> messageTimesMap) {
        System.out.println(countPerformanceMessages(messageTimesMap));

        for (Message message : messages) {
            Deque<MessageTimes> messageTimesDeque = messageTimesMap.get(message.note);
            if (shouldPrint(messageTimesDeque, message)) {
                System.out.printf("%d %d %d %d %d\n", message.code, message.note, message.roundedMeasure,
                        message.roundedBeat, message.roundedTick);
            }
        }
    }

    private static boolean shouldPrint(Deque<MessageTimes> messageTimesDeque, Message message) {
        for (MessageTimes messageTimes : messageTimesDeque) {
            if (message.code == 1) {
                if (messageTimes.onMeasureTime == message.roundedMeasure
                        && messageTimes.onBeatTime == message.roundedBeat
                        && messageTimes.onTickTime == message.roundedTick) {
                    return messageTimes.keepInRecord();
                }
            } else {
                if (messageTimes.offMeasureTime == message.roundedMeasure
                        && messageTimes.offBeatTime == message.roundedBeat
                        && messageTimes.offTickTime == message.roundedTick) {
                    return messageTimes.keepInRecord();
                }
            }
        }
        return true;
    }

    private static int countPerformanceMessages(Map<Integer, Deque<MessageTimes>> messageTimesMap) {
        int count = 0;
        for (int note : messageTimesMap.keySet()) {
            Deque<MessageTimes> messageTimesDeque = messageTimesMap.get(note);

            for (MessageTimes messageTimes : messageTimesDeque) {
                if (messageTimes.keepInRecord()) {
                    if (messageTimes.onMeasureTime != -1) {
                        count++;
                    }
                    if (messageTimes.offMeasureTime != -1) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
