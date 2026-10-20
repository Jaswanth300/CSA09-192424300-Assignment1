package parking.model;

import java.util.Objects;

/**
 * Encapsulated Vehicle base class.
 */
public abstract class Vehicle {
    // private field: Strict data hiding
    private final String regNo;

    protected Vehicle(String regNo) {
        if (regNo == null || regNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Registration number cannot be null or empty");
        }
        this.regNo = regNo.trim().toUpperCase();
    }

    public final String getRegNo() {
        return regNo;
    }

    public abstract double ratePerHour();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle other)) return false;
        return Objects.equals(this.regNo, other.regNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regNo);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" + regNo + "]";
    }
}
