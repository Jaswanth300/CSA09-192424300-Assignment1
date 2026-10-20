/**
 * SIMATS Java Programming Assignment 1 - Campus Smart-Parking System
 * TASK 2: Choose the Data Representation
 * Bloom's Level: K4 - Analyse | Marks: 3
 *
 * Student: BALA NAGA JASWANTH (Reg No: 192424300C)
 */
public class DataRepresentationDemo {

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("TASK 2: Choose the Data Representation");
        System.out.println("Data Types, Precision Risks, and Array Modeling");
        System.out.println("======================================================================");

        // 1. Data Type Field Mapping
        System.out.println("\n[1] Field Data Type Justification:");
        System.out.println("  - Slot Number  : int (non-negative discrete index)");
        System.out.println("  - Vehicle Plate: String (alphanumeric alphanumeric plate format)");
        System.out.println("  - Entry Time   : long / LocalDateTime (precise millisecond/second timestamp)");
        System.out.println("  - Duration     : int minutes (sufficient range up to 4000+ years)");
        System.out.println("  - Parking Fee  : double (monetary fractional value)");

        // 2. Casting Risk & Integer Division Demonstration
        System.out.println("\n[2] Casting Risk: Integer Division Truncation vs Safe Ceiling:");
        int durationMinutes = 59;
        double hourlyRate = 30.0;

        // Truncation risk
        int naiveHours = durationMinutes / 60; // 59 / 60 = 0
        double flawedFee = naiveHours * hourlyRate; // 0.0

        // Safe integer arithmetic ceiling: (minutes + 59) / 60
        int safeHoursInt = (durationMinutes + 59) / 60; // (59 + 59) / 60 = 118 / 60 = 1
        double safeFeeInt = safeHoursInt * hourlyRate; // 30.0

        // Floating ceiling: (int) Math.ceil(minutes / 60.0)
        int safeHoursFloat = (int) Math.ceil(durationMinutes / 60.0);
        double safeFeeFloat = safeHoursFloat * hourlyRate;

        System.out.println("Duration: " + durationMinutes + " minutes | Hourly Rate: Rs " + hourlyRate);
        System.out.println("  - Naive Integer Division (duration / 60)         : " + naiveHours + " hrs -> Fee: Rs " + flawedFee + " (ERROR: Under-billed!)");
        System.out.println("  - Safe Integer Arithmetic ((duration + 59) / 60) : " + safeHoursInt + " hrs -> Fee: Rs " + safeFeeInt + " (CORRECT)");
        System.out.println("  - Safe Floating Ceiling (Math.ceil(dur / 60.0))  : " + safeHoursFloat + " hrs -> Fee: Rs " + safeFeeFloat + " (CORRECT)");

        // 3. Array Representations: Jagged Array vs 1D Flat Array
        System.out.println("\n[3] Array Modeling: Jagged vs Flat 1D:");
        // Jagged array representation with non-uniform slots: Floor 0 has 3 slots, Floor 1 has 2 slots
        int[] slotsPerFloor = {3, 2};
        String[][] jaggedSlots = new String[slotsPerFloor.length][];
        for (int f = 0; f < slotsPerFloor.length; f++) {
            jaggedSlots[f] = new String[slotsPerFloor[f]];
        }
        jaggedSlots[0][0] = "TN01AA1000";
        jaggedSlots[1][1] = "TN02BB2000";

        System.out.println("Jagged Array instantiated: Floor 0 has " + jaggedSlots[0].length + " slots, Floor 1 has " + jaggedSlots[1].length + " slots.");
        System.out.println("  - Slot (0,0) occupied by: " + jaggedSlots[0][0]);
        System.out.println("  - Slot (1,1) occupied by: " + jaggedSlots[1][1]);
        System.out.println("Advantage: Zero wasted memory when floors have different slot capacities.");
        System.out.println("======================================================================");
    }
}
