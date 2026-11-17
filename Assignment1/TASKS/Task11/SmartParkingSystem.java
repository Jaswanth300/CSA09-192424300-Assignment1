
import java.util.ArrayList;
import java.util.Objects;

interface Chargeable {
    double chargingFee(double units);
}

abstract class Vehicle {
    String regNo;
    boolean monthlyPass;

    Vehicle(String regNo, boolean monthlyPass) {
        this.regNo = regNo;
        this.monthlyPass = monthlyPass;
    }

    abstract double ratePerHour();

    double parkingFee(int minutes) {
        if (monthlyPass || minutes <= 0) return 0;
        return Math.ceil(minutes / 60.0) * ratePerHour();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vehicle)) return false;
        return regNo.equals(((Vehicle) obj).regNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regNo);
    }
}

class TwoWheeler extends Vehicle {
    TwoWheeler(String regNo, boolean pass) {
        super(regNo, pass);
    }

    double ratePerHour() {
        return 10;
    }
}

class Car extends Vehicle {
    Car(String regNo, boolean pass) {
        super(regNo, pass);
    }

    double ratePerHour() {
        return 30;
    }
}

class ElectricCar extends Car implements Chargeable {
    ElectricCar(String regNo, boolean pass) {
        super(regNo, pass);
    }

    public double chargingFee(double units) {
        return units * 12;
    }
}

class ParkingTicket {
    Vehicle vehicle;
    int start;
    int floor;
    int slot;

    ParkingTicket(Vehicle v, int start, int floor, int slot) {
        vehicle = v;
        this.start = start;
        this.floor = floor;
        this.slot = slot;
    }
}

class ParkingFacility {
    boolean[][] occupied;
    ArrayList<ParkingTicket> tickets = new ArrayList<>();
    double revenue;

    ParkingFacility(int floors, int slots) {
        occupied = new boolean[floors][slots];
    }

    ParkingTicket checkIn(Vehicle v, int time) {
        for (ParkingTicket t : tickets)
            if (t.vehicle.equals(v)) return null;

        for (int f = 0; f < occupied.length; f++) {
            for (int s = 0; s < occupied[f].length; s++) {
                if (!occupied[f][s]) {
                    occupied[f][s] = true;
                    ParkingTicket t = new ParkingTicket(v, time, f, s);
                    tickets.add(t);
                    return t;
                }
            }
        }
        return null;
    }

    double checkOut(ParkingTicket t, int time, double units) {
        if (t == null || !tickets.contains(t) || time < t.start)
            return -1;

        double fee = t.vehicle.parkingFee(time - t.start);

        if (t.vehicle instanceof Chargeable)
            fee += ((Chargeable) t.vehicle).chargingFee(units);

        occupied[t.floor][t.slot] = false;
        tickets.remove(t);
        revenue += fee;
        return fee;
    }
}

public class SmartParkingSystem {
    public static void main(String[] args) {
        ParkingFacility p = new ParkingFacility(1, 3);

        Vehicle car = new Car("TN02B2", false);
        Vehicle bike = new TwoWheeler("TN01A1", false);
        ElectricCar ev = new ElectricCar("TN03C3", false);
        Vehicle passCar = new Car("TN04D4", true);

        ParkingTicket t1 = p.checkIn(car, 0);
        ParkingTicket t2 = p.checkIn(bike, 0);
        ParkingTicket t3 = p.checkIn(ev, 0);

        System.out.println("Full parking: " + (p.checkIn(passCar, 0) == null));
        System.out.println("Duplicate vehicle: " + (p.checkIn(bike, 0) == null));

        System.out.println("Car fee: Rs " + p.checkOut(t1, 120, 0));
        System.out.println("Bike fee: Rs " + p.checkOut(t2, 90, 0));
        System.out.println("EV fee: Rs " + p.checkOut(t3, 60, 10));

        ParkingTicket passTicket = p.checkIn(passCar, 0);
        System.out.println("Monthly pass fee: Rs " + p.checkOut(passTicket, 60, 0));

        ParkingTicket zero = p.checkIn(new Car("TN05E5", false), 0);
        System.out.println("Zero-minute fee: Rs " + p.checkOut(zero, 0, 0));
        System.out.println("Invalid ticket: " + p.checkOut(zero, -1, 0));

        double expected = 230.0;
        System.out.println("Expected revenue: Rs " + expected);
        System.out.println("Actual revenue: Rs " + p.revenue);
        System.out.println("Revenue match: " + (p.revenue == expected));
    }
}
