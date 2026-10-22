package parking.service;

import parking.model.ParkingSpot;
import parking.model.Vehicle;

/**
 * Service orchestrating parking operations and business validations.
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class ParkingService {
    private ParkingSpot[][] facility;

    public ParkingService(int floors, int slotsPerFloor) {
        facility = new ParkingSpot[floors][slotsPerFloor];
        for (int f = 0; f < floors; f++) {
            for (int s = 0; s < slotsPerFloor; s++) {
                facility[f][s] = new ParkingSpot(f, s);
            }
        }
    }

    public ParkingSpot allocateSpot(Vehicle v) {
        for (int f = 0; f < facility.length; f++) {
            for (int s = 0; s < facility[f].length; s++) {
                if (facility[f][s].isFree()) {
                    facility[f][s].park(v);
                    return facility[f][s];
                }
            }
        }
        return null;
    }

    public double calculateFee(Vehicle v, int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }
        int hours = (minutes + 59) / 60;
        return hours * v.ratePerHour();
    }
}
