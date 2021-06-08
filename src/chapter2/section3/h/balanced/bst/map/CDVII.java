package chapter2.section3.h.balanced.bst.map;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 08/06/21.
 */
public class CDVII {

    private static class LicensePhoto implements Comparable<LicensePhoto> {
        String licenseNumber;
        String time;
        boolean isEnterEvent;
        int location;

        public LicensePhoto(String licenseNumber, String time, boolean isEnterEvent, int location) {
            this.licenseNumber = licenseNumber;
            this.time = time;
            this.isEnterEvent = isEnterEvent;
            this.location = location;
        }

        @Override
        public int compareTo(LicensePhoto other) {
            if (!licenseNumber.equals(other.licenseNumber)) {
                return licenseNumber.compareTo(other.licenseNumber);
            }
            return time.compareTo(other.time);
        }
    }

    private static class Bill implements Comparable<Bill> {
        String licenseNumber;
        double amount;

        public Bill(String licenseNumber, double amount) {
            this.licenseNumber = licenseNumber;
            this.amount = amount;
        }

        @Override
        public int compareTo(Bill other) {
            return licenseNumber.compareTo(other.licenseNumber);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        FastReader.getLine();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }
            List<LicensePhoto> photosList = new ArrayList<>();

            int[] centsPerKm = new int[24];
            for (int i = 0; i < centsPerKm.length; i++) {
                centsPerKm[i] = FastReader.nextInt();
            }

            String line = FastReader.getLine();
            while (line != null && !line.equals("")) {
                String[] lineData = line.split(" ");
                String licenseNumber = lineData[0];
                String time = lineData[1];
                boolean isEnterEvent = lineData[2].equals("enter");
                int location = Integer.parseInt(lineData[3]);
                photosList.add(new LicensePhoto(licenseNumber, time, isEnterEvent, location));
                line = FastReader.getLine();
            }

            List<Bill> bills = computeBills(photosList, centsPerKm);
            for (Bill bill : bills) {
                outputWriter.printLine(String.format("%s $%.2f", bill.licenseNumber, bill.amount));
            }
        }
        outputWriter.flush();
    }

    private static List<Bill> computeBills(List<LicensePhoto> photosList, int[] centsPerKm) {
        Map<String, Double> licenseNumberToBill = new HashMap<>();
        Collections.sort(photosList);

        for (int index = 0; index < photosList.size(); index++) {
            LicensePhoto photo = photosList.get(index);
            String licenseNumber = photo.licenseNumber;

            if (!isValidPhoto(photosList, index)) {
                continue;
            }
            if (!licenseNumberToBill.containsKey(licenseNumber)) {
                licenseNumberToBill.put(licenseNumber, 2.0);
            }
            double currentBill = licenseNumberToBill.get(licenseNumber);

            String[] timeData = photo.time.split(":");
            int hour = Integer.parseInt(timeData[2]);
            int centsWhenEnteredRoad = centsPerKm[hour];
            int startLocation = photo.location;
            int endLocation = photosList.get(index + 1).location;
            currentBill += computeSegmentPrice(startLocation, endLocation, centsWhenEnteredRoad);
            licenseNumberToBill.put(licenseNumber, currentBill);
            index++;
        }

        List<Bill> bills = new ArrayList<>();
        for (String licenseNumber : licenseNumberToBill.keySet()) {
            double bill = licenseNumberToBill.get(licenseNumber);
            bills.add(new Bill(licenseNumber, bill));
        }
        Collections.sort(bills);
        return bills;
    }

    private static double computeSegmentPrice(int startLocation, int endLocation, int centsWhenEnteredRoad) {
        double amountToPay = 0;
        double kilometers = Math.abs(endLocation - startLocation);
        if (kilometers > 0) {
            amountToPay += (centsWhenEnteredRoad * kilometers) / 100;
        }
        amountToPay++;
        return amountToPay;
    }

    private static boolean isValidPhoto(List<LicensePhoto> photosList, int index) {
        LicensePhoto photo = photosList.get(index);
        if (photo.isEnterEvent && index + 1 < photosList.size()) {
            LicensePhoto nextPhoto = photosList.get(index + 1);
            return photo.licenseNumber.equals(nextPhoto.licenseNumber) && !nextPhoto.isEnterEvent;
        }
        if (!photo.isEnterEvent && index > 0) {
            LicensePhoto previousPhoto = photosList.get(index - 1);
            return photo.licenseNumber.equals(previousPhoto.licenseNumber) && previousPhoto.isEnterEvent;
        }
        return false;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
