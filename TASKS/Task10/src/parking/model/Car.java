package parking.model;

/**
 * Car model with subclass ratePerHour.
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class Car extends Vehicle {

    public Car(String regNo) {
        super(regNo);
    }

    @Override
    public double ratePerHour() {
        return 30.0;
    }
}
