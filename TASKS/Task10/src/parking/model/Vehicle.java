package parking.model;

/**
 * Base Vehicle model demonstrating encapsulation and validated state.
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public abstract class Vehicle {
    private String regNo;

    public Vehicle(String regNo) {
        setRegNo(regNo);
    }

    public String getRegNo() {
        return regNo;
    }

    public final void setRegNo(String regNo) {
        if (regNo == null || regNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Registration number cannot be blank");
        }
        this.regNo = regNo.trim();
    }

    public abstract double ratePerHour();
}
