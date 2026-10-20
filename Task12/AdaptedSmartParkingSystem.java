import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * SIMATS Java Programming Assignment 1 - Campus Smart-Parking System
 * DAY-4 DESIGN CHALLENGE
 * TASK 12: Re-evaluate and Adapt the Design
 * Bloom's Level: K5 - Evaluate | Marks: 8
 *
 * Student: BALA NAGA JASWANTH (Reg No: 192424300C)
 *
 * Documented Engineering Assumptions:
 * 1. E-Bike flat fee set to Rs 15.0 (independent of parking duration).
 * 2. Delivery Van peak rate (9 AM - 5 PM) is double car rate (2 x 30 = Rs 60.0/hr); off-peak rate is Rs 30.0/hr.
 * 3. Visitor 24-Hour One-Day Pass price set to Rs 100.0.
 * 4. 5-Floor non-uniform capacity configured as: Floor 0 (30), Floor 1 (25), Floor 2 (20), Floor 3 (15), Floor 4 (10).
 */
public class AdaptedSmartParkingSystem {

    // ==========================================
    // Adapted Abstractions & Pricing Strategy
    // ==========================================

    public interface Pass {
        boolean isValid();
        double getPassPrice();
    }

    public static class OneDayPass implements Pass {
        private final long purchaseTimestamp;
        private static final double PASS_PRICE = 100.0; // Documented Engineering Assumption

        public OneDayPass() {
            this.purchaseTimestamp = System.currentTimeMillis();
        }

        @Override
        public boolean isValid() {
            // Valid for 24 hours (86,400,000 milliseconds)
            return (System.currentTimeMillis() - purchaseTimestamp) <= 86_400_000L;
        }

        @Override
        public double getPassPrice() { return PASS_PRICE; }
    }

    public interface PricingStrategy {
        double calculateFee(int durationMinutes, LocalTime entryTime, LocalTime exitTime);
    }

    // Standard hourly billing strategy
    public static class HourlyRateStrategy implements PricingStrategy {
        private final double ratePerHour;

        public HourlyRateStrategy(double ratePerHour) {
            this.ratePerHour = ratePerHour;
        }

        @Override
        public double calculateFee(int durationMinutes, LocalTime entryTime, LocalTime exitTime) {
            int hours = (durationMinutes + 59) / 60;
            return hours * ratePerHour;
        }
    }

    // E-Bike Flat Fee Strategy (Documented Engineering Assumption: Rs 15.0)
    public static class FlatFeeStrategy implements PricingStrategy {
        private final double flatFee;

        public FlatFeeStrategy(double flatFee) {
            this.flatFee = flatFee;
        }

        @Override
        public double calculateFee(int durationMinutes, LocalTime entryTime, LocalTime exitTime) {
            return flatFee;
        }
    }

    // Delivery Van Time-Windowed Surge Strategy (9 AM - 5 PM double car rate)
    public static class DeliveryVanPricingStrategy implements PricingStrategy {
        private static final LocalTime PEAK_START = LocalTime.of(9, 0);
        private static final LocalTime PEAK_END = LocalTime.of(17, 0);
        private final double peakRatePerHour;
        private final double offPeakRatePerHour;

        public DeliveryVanPricingStrategy(double carRate) {
            this.peakRatePerHour = carRate * 2.0;    // Rs 60.0/hr (Mandated: Double car rate)
            this.offPeakRatePerHour = carRate;       // Rs 30.0/hr (Documented Engineering Assumption)
        }

        @Override
        public double calculateFee(int durationMinutes, LocalTime entryTime, LocalTime exitTime) {
            int hours = (durationMinutes + 59) / 60;
            // Determine if the stay falls within business hours 9 AM - 5 PM
            boolean isPeak = !(exitTime.isBefore(PEAK_START) || entryTime.isAfter(PEAK_END));
            double activeRate = isPeak ? peakRatePerHour : offPeakRatePerHour;
            return hours * activeRate;
        }
    }

    // ==========================================
    // Vehicle Domain Hierarchy
    // ==========================================

    public abstract static class Vehicle {
        private final String regNo;
        private final PricingStrategy pricingStrategy;
        private Pass activePass;

        protected Vehicle(String regNo, PricingStrategy strategy) {
            this.regNo = Objects.requireNonNull(regNo).toUpperCase();
            this.pricingStrategy = strategy;
            this.activePass = null;
        }

        public String getRegNo() { return regNo; }
        public PricingStrategy getPricingStrategy() { return pricingStrategy; }
        public Pass getActivePass() { return activePass; }
        public void assignPass(Pass pass) { this.activePass = pass; }

        public double calculateFee(int durationMinutes, LocalTime inTime, LocalTime outTime) {
            if (activePass != null && activePass.isValid()) {
                return 0.0; // Covered by one-day or monthly pass
            }
            return pricingStrategy.calculateFee(durationMinutes, inTime, outTime);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + " [" + regNo + "]";
        }
    }

    public static class Car extends Vehicle {
        public Car(String regNo) {
            super(regNo, new HourlyRateStrategy(30.0));
        }
    }

    public static class TwoWheeler extends Vehicle {
        public TwoWheeler(String regNo) {
            super(regNo, new HourlyRateStrategy(10.0));
        }
    }

    // NEW CATEGORY 1: EBike (Mandated: Flat fee)
    public static class EBike extends Vehicle {
        public EBike(String regNo) {
            super(regNo, new FlatFeeStrategy(15.0)); // Documented Engineering Assumption: Rs 15.0
        }
    }

    // NEW CATEGORY 2: DeliveryVan (Mandated: Double car rate 9 AM - 5 PM)
    public static class DeliveryVan extends Vehicle {
        public DeliveryVan(String regNo) {
            super(regNo, new DeliveryVanPricingStrategy(30.0));
        }
    }

    // ==========================================
    // Extended 5-Floor Parking Facility
    // ==========================================

    public static class ExtendedParkingFacility {
        // Jagged array representation for non-uniform slot capacities across 5 floors
        private final Vehicle[][] slots;
        private final Map<String, int[]> vehicleLocations = new HashMap<>();

        public ExtendedParkingFacility(int[] slotsPerFloor) {
            slots = new Vehicle[slotsPerFloor.length][];
            for (int f = 0; f < slotsPerFloor.length; f++) {
                slots[f] = new Vehicle[slotsPerFloor[f]];
            }
        }

        public int[] allocate(Vehicle vehicle) {
            for (int f = 0; f < slots.length; f++) {
                for (int s = 0; s < slots[f].length; s++) {
                    if (slots[f][s] == null) {
                        slots[f][s] = vehicle;
                        int[] loc = new int[]{f, s};
                        vehicleLocations.put(vehicle.getRegNo(), loc);
                        return loc;
                    }
                }
            }
            return null; // Full
        }

        public boolean release(int floor, int slot) {
            if (floor < 0 || floor >= slots.length || slot < 0 || slot >= slots[floor].length) {
                return false;
            }
            if (slots[floor][slot] == null) return false;
            Vehicle v = slots[floor][slot];
            slots[floor][slot] = null;
            vehicleLocations.remove(v.getRegNo());
            return true;
        }

        public int getFloorCapacity(int floor) {
            return slots[floor].length;
        }

        public int getTotalCapacity() {
            int total = 0;
            for (Vehicle[] floor : slots) total += floor.length;
            return total;
        }
    }

    // ==========================================
    // Main Verification Test Suite
    // ==========================================

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("TASK 12: Day-4 Design Challenge - Re-evaluate and Adapt the Design");
        System.out.println("E-Bikes, Delivery Vans, 24-Hr Passes, and 5-Floor Jagged Facility");
        System.out.println("======================================================================");

        // ----------------------------------------------------
        // PART A: Impact Analysis Demonstration (5 Floors Jagged Array)
        // ----------------------------------------------------
        System.out.println("\n[PART A: Impact Analysis - Non-Uniform 5-Floor Jagged Array]");
        // Documented Engineering Assumption: Floor 0: 30, Floor 1: 25, Floor 2: 20, Floor 3: 15, Floor 4: 10
        int[] fiveFloorConfig = {30, 25, 20, 15, 10};
        ExtendedParkingFacility facility = new ExtendedParkingFacility(fiveFloorConfig);

        System.out.println("Configured 5-Floor Facility with Non-Uniform Capacities:");
        for (int i = 0; i < fiveFloorConfig.length; i++) {
            System.out.println("  Floor " + i + " Capacity: " + facility.getFloorCapacity(i) + " slots");
        }
        System.out.println("Total Facility Capacity: " + facility.getTotalCapacity() + " slots");

        // ----------------------------------------------------
        // PART B & C: Adaptation & Implementation Verification
        // ----------------------------------------------------
        System.out.println("\n[PART B & C: Implementation of New Categories & Pricing Rules]");

        // Test 1: E-Bike Flat Fee Verification
        EBike ebike1 = new EBike("TN04EB0001");
        double feeShort = ebike1.calculateFee(30, LocalTime.of(10, 0), LocalTime.of(10, 30));
        double feeLong = ebike1.calculateFee(360, LocalTime.of(10, 0), LocalTime.of(16, 0));
        System.out.println("\n1. E-Bike Flat Fee Test (Mandated: Flat Fee | Assumption: Rs 15.0):");
        System.out.println("  Stay 1 (30 mins)  : Rs " + feeShort);
        System.out.println("  Stay 2 (360 mins) : Rs " + feeLong);
        System.out.println("  Flat Fee Verification (Identical regardless of time): " + (feeShort == feeLong && feeShort == 15.0 ? "PASS" : "FAIL"));

        // Test 2: Delivery Van Business Hours Peak vs Off-Peak Verification
        DeliveryVan van = new DeliveryVan("TN05DV1111");
        // Peak stay: 10:00 AM to 12:00 PM (2 hours inside 9 AM - 5 PM) -> 2 * 60 = Rs 120
        double feePeak = van.calculateFee(120, LocalTime.of(10, 0), LocalTime.of(12, 0));
        // Off-peak stay: 6:00 PM to 8:00 PM (2 hours outside 9 AM - 5 PM) -> 2 * 30 = Rs 60
        double feeOffPeak = van.calculateFee(120, LocalTime.of(18, 0), LocalTime.of(20, 0));
        System.out.println("\n2. Delivery Van Dynamic Pricing Test (Mandated: Double car rate 9 AM-5 PM):");
        System.out.println("  Peak Window (10:00 AM - 12:00 PM, 2 hrs)  : Rs " + feePeak + " (Expected: Rs 120.0 -> " + (feePeak == 120.0 ? "PASS" : "FAIL") + ")");
        System.out.println("  Off-Peak (6:00 PM - 8:00 PM, 2 hrs)       : Rs " + feeOffPeak + " (Expected: Rs 60.0 -> " + (feeOffPeak == 60.0 ? "PASS" : "FAIL") + ")");

        // Test 3: One-Day Visitor Pass Verification
        System.out.println("\n3. Visitor 24-Hour One-Day Pass Test (Mandated: One-day pass | Assumption: Rs 100.0):");
        Car visitorCar = new Car("TN06VP2222");
        OneDayPass pass = new OneDayPass();
        System.out.println("  Pass Purchased: Price = Rs " + pass.getPassPrice() + " | Valid = " + pass.isValid());
        visitorCar.assignPass(pass);

        double exitFee1 = visitorCar.calculateFee(180, LocalTime.of(9, 0), LocalTime.of(12, 0));
        double exitFee2 = visitorCar.calculateFee(240, LocalTime.of(14, 0), LocalTime.of(18, 0));
        System.out.println("  First Exit with Valid Pass (180 mins)  : Rs " + exitFee1 + " (Covered by Pass -> " + (exitFee1 == 0.0 ? "PASS" : "FAIL") + ")");
        System.out.println("  Second Exit with Valid Pass (240 mins) : Rs " + exitFee2 + " (Covered by Pass -> " + (exitFee2 == 0.0 ? "PASS" : "FAIL") + ")");

        // Test 4: Allocation in 5-Floor Jagged Facility
        System.out.println("\n4. Jagged Slot Allocation Verification:");
        int[] locEBike = facility.allocate(ebike1);
        int[] locVan = facility.allocate(van);
        int[] locCar = facility.allocate(visitorCar);
        System.out.println("  Allocated EBike       : Floor " + locEBike[0] + ", Slot " + locEBike[1]);
        System.out.println("  Allocated DeliveryVan : Floor " + locVan[0] + ", Slot " + locVan[1]);
        System.out.println("  Allocated VisitorCar  : Floor " + locCar[0] + ", Slot " + locCar[1]);

        // ----------------------------------------------------
        // PART D: Evidence-Based Defence Summary
        // ----------------------------------------------------
        System.out.println("\n[PART D: Evidence-Based Design Defence]");
        System.out.println("1. Existing Classes Modified : ZERO. The core Strategy pattern allowed adding EBike,");
        System.out.println("   DeliveryVan, and OneDayPass via EXTENSION rather than modification (Strict OCP).");
        System.out.println("2. Ease of Future Additions  : Future vehicle types (e.g. Autonomous Shuttles, Heavy Trucks)");
        System.out.println("   simply require writing one subclass and passing a PricingStrategy.");
        System.out.println("3. Memory Efficiency        : Jagged arrays eliminate wasted memory cells for floors with fewer slots.");
        System.out.println("======================================================================");
    }
}
