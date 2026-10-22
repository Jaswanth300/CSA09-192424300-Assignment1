/**
 * SIMATS Java Programming Assignment 1 - Task 06
 * Inheritance Hierarchy
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class VehicleHierarchyDemo {

    static class Vehicle {
        String regNo;

        public Vehicle(String regNo) {
            this.regNo = regNo;
            System.out.println("1. Vehicle constructor called for " + regNo);
        }

        public double ratePerHour() {
            return 20.0;
        }
    }
    static class TwoWheeler extends Vehicle {
        public TwoWheeler(String regNo) {
            super(regNo);
            System.out.println("2. TwoWheeler constructor called");
        }
        public double ratePerHour() {
            return 10.0;
        }
    }
    static class Car extends Vehicle {
        public Car(String regNo) {
            super(regNo);
            System.out.println("2. Car constructor called");
        }
        public double ratePerHour() {
            return 30.0;
        }
    }
    static class ElectricCar extends Car {
        int batteryCapacity;

        public ElectricCar(String regNo, int batteryCapacity) {
            super(regNo);
            this.batteryCapacity = batteryCapacity;
            System.out.println("3. ElectricCar constructor called (Battery: " + batteryCapacity + " kWh)");
        }
    }

    public static void main(String[] args) {
        System.out.println("Creating ElectricCar (tracing constructor order):");
        ElectricCar ev = new ElectricCar("TN03EV9999", 40);

        TwoWheeler bike = new TwoWheeler("TN01BK1111");
        Car car = new Car("TN02CR2222");

        System.out.println("Bike rate per hour: Rs " + bike.ratePerHour());
        System.out.println("Car rate per hour: Rs " + car.ratePerHour());
        System.out.println("EV rate per hour (inherited from Car): Rs " + ev.ratePerHour());
    }
}
