
import java.util.Arrays;

interface Pass {
    boolean isValid(int hours);
}

class OneDayPass implements Pass {
    double price = 100;

    public boolean isValid(int hours) {
        return hours >= 0 && hours <= 24;
    }
}

interface PricingStrategy {
    double calculateFee(int hours);
}

class HourlyPricing implements PricingStrategy {
    double rate;

    HourlyPricing(double rate) {
        this.rate = rate;
    }

    public double calculateFee(int hours) {
        return Math.ceil(hours) * rate;
    }
}

class FlatPricing implements PricingStrategy {
    double fee;

    FlatPricing(double fee) {
        this.fee = fee;
    }

    public double calculateFee(int hours) {
        return fee;
    }
}

class DeliveryPricing implements PricingStrategy {
    public double calculateFee(int hours) {
        return Math.ceil(hours) * 60;
    }

    double offPeakFee(int hours) {
        return Math.ceil(hours) * 30;
    }
}

class ParkingVehicle {
    String regNo;
    PricingStrategy pricing;

    ParkingVehicle(String regNo, PricingStrategy pricing) {
        this.regNo = regNo;
        this.pricing = pricing;
    }

    double fee(int hours) {
        return pricing.calculateFee(hours);
    }
}

class AdaptedParkingFacility {
    String[][] floors = new String[5][];

    AdaptedParkingFacility() {
        int[] capacity = {30, 25, 20, 15, 10};

        for (int i = 0; i < floors.length; i++)
            floors[i] = new String[capacity[i]];
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
        for (String[] floor : floors) total += floor.length;
        return total;
    }
}

public class AdaptedSmartParkingSystem {
    public static void main(String[] args) {
        AdaptedParkingFacility parking = new AdaptedParkingFacility();

        ParkingVehicle car =
            new ParkingVehicle("TN02B2", new HourlyPricing(30));

        ParkingVehicle eBike =
            new ParkingVehicle("TN06F6", new FlatPricing(15));

        DeliveryPricing vanPricing = new DeliveryPricing();
        ParkingVehicle van =
            new ParkingVehicle("TN07G7", vanPricing);

        OneDayPass pass = new OneDayPass();

        System.out.println("Total slots: " + parking.totalSlots());
        System.out.println("Car parked: " + parking.park(car.regNo));
        System.out.println("E-bike parked: " + parking.park(eBike.regNo));
        System.out.println("Van parked: " + parking.park(van.regNo));

        System.out.println("Car fee (2 hours): Rs " + car.fee(2));
        System.out.println("E-bike fee: Rs " + eBike.fee(2));
        System.out.println("Van peak fee (2 hours): Rs " +
                           van.fee(2));
        System.out.println("Van off-peak fee (2 hours): Rs " +
                           vanPricing.offPeakFee(2));

        System.out.println("Pass price: Rs " + pass.price);
        System.out.println("Pass valid for 24 hours: " +
                           pass.isValid(24));
        System.out.println("Pass waives exit fee: Rs " +
                           (pass.isValid(24) ? 0 : car.fee(2)));

        System.out.println("Car removed: " + parking.remove(car.regNo));
        System.out.println("Invalid vehicle removed: " +
                           parking.remove("UNKNOWN"));
    }
}
