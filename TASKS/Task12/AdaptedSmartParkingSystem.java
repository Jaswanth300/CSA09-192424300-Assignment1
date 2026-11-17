/**
 * SIMATS Java Programming Assignment 1 - Task 12
 * Student: Bala Naga Jaswanth | Reg No: 192424300 | Course: CSA0921
 * Day-4 Design Challenge: Decoupled Dynamic Pricing (Strategy Pattern)
 * Time-of-day Delivery Van pricing, 24-hr Visitor Pass, E-Bike flat fee, and 5-floor facility.
 */

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

interface Pass {
    boolean isValidAt(LocalDateTime queryTime);
    double getPrice();
}

class OneDayPass implements Pass {
    private final double price;
    private final LocalDateTime issueTime;
    private final LocalDateTime expiryTime;

    OneDayPass(LocalDateTime issueTime) {
        this(issueTime, 100.0);
    }

    OneDayPass(LocalDateTime issueTime, double price) {
        this.issueTime = issueTime;
        this.expiryTime = issueTime.plusHours(24);
        this.price = price;
    }

    @Override
    public boolean isValidAt(LocalDateTime queryTime) {
        return !queryTime.isBefore(issueTime) && !queryTime.isAfter(expiryTime);
    }

    @Override
    public double getPrice() {
        return price;
    }

    public LocalDateTime getIssueTime() {
        return issueTime;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }
}

interface PricingStrategy {
    double calculateFee(LocalDateTime entryTime, LocalDateTime exitTime);

    default double calculateFee(int hours) {
        LocalDateTime base = LocalDateTime.of(2026, 10, 22, 6, 0);
        return calculateFee(base, base.plusHours(hours));
    }
}

class HourlyPricing implements PricingStrategy {
    private final double ratePerHour;

    HourlyPricing(double ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    @Override
    public double calculateFee(LocalDateTime entryTime, LocalDateTime exitTime) {
        long minutes = Math.max(0, Duration.between(entryTime, exitTime).toMinutes());
        int billableHours = (int) Math.ceil(minutes / 60.0);
        return billableHours * ratePerHour;
    }

    public double calculateFee(int hours) {
        return hours * ratePerHour;
    }
}

class FlatPricing implements PricingStrategy {
    private final double flatFee;

    FlatPricing(double flatFee) {
        this.flatFee = flatFee;
    }

    @Override
    public double calculateFee(LocalDateTime entryTime, LocalDateTime exitTime) {
        return flatFee;
    }

    public double calculateFee(int hours) {
        return flatFee;
    }
}

/**
 * DeliveryPricing implements time-of-day dynamic surge pricing.
 * Peak window: 9:00 AM (09:00) to 5:00 PM (17:00).
 * Rate during peak: Rs 60.0/hr (double the standard car rate of Rs 30.0/hr).
 * Rate outside peak (off-peak): Rs 30.0/hr (standard car rate).
 *
 * Rounding Policy:
 * The parking session is partitioned into peak minutes and off-peak minutes.
 * Each partition is independently rounded up using the university ceiling rule:
 * every started hour is charged in full ((minutes + 59) / 60), then multiplied
 * by its respective hourly rate.
 */
class DeliveryPricing implements PricingStrategy {
    private static final LocalTime PEAK_START = LocalTime.of(9, 0);
    private static final LocalTime PEAK_END = LocalTime.of(17, 0);
    private static final double PEAK_RATE = 60.0;     // Double car rate
    private static final double OFF_PEAK_RATE = 30.0; // Standard car rate

    @Override
    public double calculateFee(LocalDateTime entryTime, LocalDateTime exitTime) {
        if (exitTime.isBefore(entryTime)) {
            throw new IllegalArgumentException("Exit time cannot be before entry time");
        }
        long totalMinutes = Duration.between(entryTime, exitTime).toMinutes();
        if (totalMinutes == 0) {
            return 0.0;
        }

        long peakMinutes = 0;
        long offPeakMinutes = 0;

        LocalDateTime current = entryTime;
        while (current.isBefore(exitTime)) {
            LocalTime time = current.toLocalTime();
            if (!time.isBefore(PEAK_START) && time.isBefore(PEAK_END)) {
                peakMinutes++;
            } else {
                offPeakMinutes++;
            }
            current = current.plusMinutes(1);
        }

        int peakHours = (int) Math.ceil(peakMinutes / 60.0);
        int offPeakHours = (int) Math.ceil(offPeakMinutes / 60.0);

        return (peakHours * PEAK_RATE) + (offPeakHours * OFF_PEAK_RATE);
    }
}

class ParkingVehicle {
    String regNo;
    PricingStrategy pricing;

    ParkingVehicle(String regNo, PricingStrategy pricing) {
        this.regNo = regNo;
        this.pricing = pricing;
    }

    double fee(LocalDateTime entryTime, LocalDateTime exitTime) {
        return pricing.calculateFee(entryTime, exitTime);
    }

    double fee(int hours) {
        return pricing.calculateFee(hours);
    }
}

class AdaptedParkingFacility {
    String[][] floors = new String[5][];

    AdaptedParkingFacility() {
        int[] capacity = {30, 25, 20, 15, 10};
        for (int i = 0; i < floors.length; i++) {
            floors[i] = new String[capacity[i]];
        }
    }

    boolean park(String regNo) {
        for (String[] floor : floors) {
            for (int i = 0; i < floor.length; i++) {
                if (floor[i] == null) {
                    floor[i] = regNo;
                    return true;
                }
            }
        }
        return false;
    }

    boolean remove(String regNo) {
        for (String[] floor : floors) {
            for (int i = 0; i < floor.length; i++) {
                if (regNo.equals(floor[i])) {
                    floor[i] = null;
                    return true;
                }
            }
        }
        return false;
    }

    int totalSlots() {
        int total = 0;
        for (String[] floor : floors) {
            total += floor.length;
        }
        return total;
    }
}

public class AdaptedSmartParkingSystem {
    public static void main(String[] args) {
        AdaptedParkingFacility parking = new AdaptedParkingFacility();

        ParkingVehicle car = new ParkingVehicle("TN02B2", new HourlyPricing(30.0));
        ParkingVehicle eBike = new ParkingVehicle("TN06F6", new FlatPricing(15.0));
        ParkingVehicle van = new ParkingVehicle("TN07G7", new DeliveryPricing());

        System.out.println("Total slots: " + parking.totalSlots());
        System.out.println("Car parked: " + parking.park(car.regNo));
        System.out.println("E-bike parked: " + parking.park(eBike.regNo));
        System.out.println("Van parked: " + parking.park(van.regNo));

        LocalDate testDate = LocalDate.of(2026, 10, 22);

        // Standard Car hourly fee (2 hours @ Rs 30/hr)
        LocalDateTime carEntry = LocalDateTime.of(testDate, LocalTime.of(10, 0));
        LocalDateTime carExit = LocalDateTime.of(testDate, LocalTime.of(12, 0));
        System.out.println("Car fee (2 hours): Rs " + car.fee(carEntry, carExit));

        // E-bike flat fee (regardless of duration)
        LocalDateTime bikeEntry = LocalDateTime.of(testDate, LocalTime.of(8, 0));
        LocalDateTime bikeExit = LocalDateTime.of(testDate, LocalTime.of(14, 0));
        System.out.println("E-bike fee (flat rate): Rs " + eBike.fee(bikeEntry, bikeExit));

        // Delivery Van - Peak Only (10:00 to 12:00, 2h in peak window @ Rs 60/hr)
        LocalDateTime vanPeakEntry = LocalDateTime.of(testDate, LocalTime.of(10, 0));
        LocalDateTime vanPeakExit = LocalDateTime.of(testDate, LocalTime.of(12, 0));
        System.out.println("Van peak fee (10:00 to 12:00, 2h peak @ Rs 60): Rs " + van.fee(vanPeakEntry, vanPeakExit));

        // Delivery Van - Off-Peak Only (06:00 to 08:00, 2h outside peak window @ Rs 30/hr)
        LocalDateTime vanOffPeakEntry = LocalDateTime.of(testDate, LocalTime.of(6, 0));
        LocalDateTime vanOffPeakExit = LocalDateTime.of(testDate, LocalTime.of(8, 0));
        System.out.println("Van off-peak fee (06:00 to 08:00, 2h off-peak @ Rs 30): Rs " + van.fee(vanOffPeakEntry, vanOffPeakExit));

        // Delivery Van - Crossing Peak Boundary (08:00 to 10:00, 1h off-peak @ Rs 30 + 1h peak @ Rs 60)
        LocalDateTime vanCrossEntry = LocalDateTime.of(testDate, LocalTime.of(8, 0));
        LocalDateTime vanCrossExit = LocalDateTime.of(testDate, LocalTime.of(10, 0));
        System.out.println("Van boundary fee (08:00 to 10:00, 1h off-peak + 1h peak): Rs " + van.fee(vanCrossEntry, vanCrossExit));

        // One-Day Pass issued at Day 1 09:00 AM (Valid 24 hours until Day 2 09:00 AM)
        LocalDateTime passIssue = LocalDateTime.of(testDate, LocalTime.of(9, 0));
        OneDayPass pass = new OneDayPass(passIssue, 100.0);
        System.out.println("Pass price: Rs " + pass.getPrice());

        // Pass Test 1: Valid pass exit at Day 1 14:00 (5 hours after issue) -> fee waived
        LocalDateTime validExit = LocalDateTime.of(testDate, LocalTime.of(14, 0));
        boolean isValidPass = pass.isValidAt(validExit);
        double feeValidPass = isValidPass ? 0.0 : car.fee(carEntry, validExit);
        System.out.println("Pass valid at Day 1 14:00 (5h after issue): " + isValidPass);
        System.out.println("Pass waives exit fee: Rs " + feeValidPass);

        // Pass Test 2: Expired pass exit at Day 2 11:00 (26 hours after issue) -> charges standard fee
        LocalDateTime expiredExit = passIssue.plusHours(26);
        boolean isExpiredPass = pass.isValidAt(expiredExit);
        LocalDateTime secondEntry = LocalDateTime.of(testDate.plusDays(1), LocalTime.of(9, 0));
        double feeExpiredPass = isExpiredPass ? 0.0 : car.fee(secondEntry, expiredExit);
        System.out.println("Pass valid at Day 2 11:00 (26h after issue): " + isExpiredPass);
        System.out.println("Fee with expired pass (2h stay @ Rs 30): Rs " + feeExpiredPass);

        // Facility release operations
        System.out.println("Car removed: " + parking.remove(car.regNo));
        System.out.println("Invalid vehicle removed: " + parking.remove("UNKNOWN"));
    }
}
