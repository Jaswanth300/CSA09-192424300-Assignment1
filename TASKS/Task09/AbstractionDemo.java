/**
 * SIMATS Java Programming Assignment 1 - Task 09
 * Abstraction (Abstract Classes & Interfaces)
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class AbstractionDemo {

    interface Chargeable {
        double chargingFee(int kWh);

        default double getServiceTax() {
            return 5.0; // Flat service charge
        }
    }

    interface Payable {
        double computeTotal(double baseFee);
    }

    abstract static class Vehicle {
        private String regNo;

        public Vehicle(String regNo) {
            this.regNo = regNo;
        }

        public String getRegNo() {
            return regNo;
        }

        public abstract double ratePerHour();

        public double calculateFee(int minutes) {
            int hours = (minutes + 59) / 60;
            return hours * ratePerHour();
        }
    }

    static class ElectricCar extends Vehicle implements Chargeable, Payable {
        public ElectricCar(String regNo) {
            super(regNo);
        }

        @Override
        public double ratePerHour() {
            return 30.0;
        }

        @Override
        public double chargingFee(int kWh) {
            return kWh * 12.0;
        }

        @Override
        public double computeTotal(double baseFee) {
            return baseFee + getServiceTax();
        }
    }

    public static void main(String[] args) {
        ElectricCar ev = new ElectricCar("TN03EV5555");

        double parkingFee = ev.calculateFee(120); // 2 hours = Rs 60.0
        double chargeFee = ev.chargingFee(10);    // 10 kWh = Rs 120.0
        double total = ev.computeTotal(parkingFee + chargeFee);

        System.out.println("Vehicle: " + ev.getRegNo());
        System.out.println("Parking Fee (120 mins): Rs " + parkingFee);
        System.out.println("Charging Fee (10 kWh): Rs " + chargeFee);
        System.out.println("Service Tax (default method): Rs " + ev.getServiceTax());
        System.out.println("Total Amount Payable: Rs " + total);
    }
}
