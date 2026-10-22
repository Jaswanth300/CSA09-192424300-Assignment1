/**
 * SIMATS Java Programming Assignment 1 - Task 02
 * Data Representation & Type Conversion
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class DataRepresentationDemo {

    public static void main(String[] args) {
        int slotNumber = 1;
        String vehicleNo = "TN01AB1111";
        int durationMinutes = 59;
        double hourlyRate = 30.0;

        // Integer division truncation vs safe ceiling
        int naiveHours = durationMinutes / 60; // 59 / 60 = 0 (truncation)
        double naiveFee = naiveHours * hourlyRate;

        // Safe integer ceiling: (minutes + 59) / 60
        int safeHours = (durationMinutes + 59) / 60;
        double safeFee = safeHours * hourlyRate;

        System.out.println("Duration: " + durationMinutes + " minutes, Hourly Rate: Rs " + hourlyRate);
        System.out.println("Naive calculation (minutes / 60): " + naiveHours + " hrs -> Rs " + naiveFee);
        System.out.println("Safe ceiling ((minutes + 59) / 60): " + safeHours + " hrs -> Rs " + safeFee);

        // Jagged array representation
        String[][] parkingLot = new String[2][];
        parkingLot[0] = new String[2];
        parkingLot[1] = new String[3];

        parkingLot[0][0] = vehicleNo;
        parkingLot[1][2] = "TN02CD2222";

        System.out.println("Floor 0 slots: " + parkingLot[0].length + ", Floor 1 slots: " + parkingLot[1].length);
        System.out.println("Slot (0,0) holds: " + parkingLot[0][0]);
        System.out.println("Slot (1,2) holds: " + parkingLot[1][2]);
    }
}
