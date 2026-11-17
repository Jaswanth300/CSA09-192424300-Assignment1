import java.util.Objects;

/**
 * SIMATS Java Programming Assignment 1 - Task 07
 * Method Overriding & Object Contracts
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class VehicleOverrideDemo {

    static class Vehicle {
        private final String regNo;

        public Vehicle(String regNo) {
            this.regNo = regNo;
        }

        public final String getRegNo() {
            return regNo;
        }

        public double ratePerHour() {
            return 20.0;
        }

        public double calculateFee(int minutes) {
            int hours = (minutes + 59) / 60;
            return hours * ratePerHour();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || !(o instanceof Vehicle)) return false;
            Vehicle vehicle = (Vehicle) o;
            return Objects.equals(regNo, vehicle.regNo);
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

    static class TwoWheeler extends Vehicle {
        public TwoWheeler(String regNo) {
            super(regNo);
        }

        @Override
        public double ratePerHour() {
            return 10.0;
        }
    }

    static class Car extends Vehicle {
        public Car(String regNo) {
            super(regNo);
        }

        @Override
        public double ratePerHour() {
            return 30.0;
        }
    }

    public static void main(String[] args) {
        Vehicle bike = new TwoWheeler("TN01A1");
        Vehicle car1 = new Car("TN02B2");
        Vehicle car2 = new Car("TN02B2");
        Vehicle car3 = new Car("TN03C3");

        // Method overriding
        System.out.println(bike + " fee for 130 mins: Rs " + bike.calculateFee(130));
        System.out.println(car1 + " fee for 130 mins: Rs " + car1.calculateFee(130));

        // equals() and hashCode() contract
        System.out.println("car1.equals(car2) [same regNo]: " + car1.equals(car2));
        System.out.println("car1.equals(car3) [different regNo]: " + car1.equals(car3));
        System.out.println("car1.hashCode() == car2.hashCode(): " + (car1.hashCode() == car2.hashCode()));
    }
}
