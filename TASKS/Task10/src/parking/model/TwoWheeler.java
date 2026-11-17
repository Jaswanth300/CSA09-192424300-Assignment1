package parking.model;

/**
 * TwoWheeler model with subclass ratePerHour.
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class TwoWheeler extends Vehicle {

    public TwoWheeler(String regNo) {
        super(regNo);
    }

    @Override
    public double ratePerHour() {
        return 10.0;
    }
}
