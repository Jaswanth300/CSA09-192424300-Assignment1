package parking.service;

import parking.model.ParkingSpot;
import parking.model.Vehicle;

/**
 * Service layer enforcing business rules and validation.
 */
public class ParkingService {
    private final ParkingSpot[][] grid;

    public ParkingService(int[] slotsPerFloor) {
        if (slotsPerFloor == null || slotsPerFloor.length == 0) {
            throw new IllegalArgumentException("Facility must have at least one floor");
        }
        grid = new ParkingSpot[slotsPerFloor.length][];
        for (int f = 0; f < slotsPerFloor.length; f++) {
            grid[f] = new ParkingSpot[slotsPerFloor[f]];
            for (int s = 0; s < slotsPerFloor[f]; s++) {
                grid[f][s] = new ParkingSpot(f, s);
            }
        }
    }

    public ParkingSpot allocateSpot(Vehicle vehicle) {
        for (ParkingSpot[] floor : grid) {
            for (ParkingSpot spot : floor) {
                if (spot.isFree()) {
                    spot.assignVehicle(vehicle);
                    return spot;
                }
            }
        }
        return null;
    }

    public double calculateFee(Vehicle vehicle, int durationMinutes) {
        if (durationMinutes < 0) {
            throw new IllegalArgumentException("Duration cannot be negative: " + durationMinutes);
        }
        int billableHours = (durationMinutes + 59) / 60;
        return billableHours * vehicle.ratePerHour();
    }
}
