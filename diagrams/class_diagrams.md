# Campus Smart-Parking System - UML Diagrams & Architectural Models

## 1. Core Domain Class Diagram (Tasks 01–11)

```mermaid
classDiagram
    direction TB

    class Chargeable {
        <<interface>>
        +chargingFee(int kWh) double
    }

    class Vehicle {
        <<abstract>>
        -String regNo
        -boolean hasMonthlyPass
        +getRegNo() String
        +hasMonthlyPass() boolean
        +ratePerHour()* double
        +calculateParkingFee(int minutes) double
        +equals(Object o) boolean
        +hashCode() int
        +toString() String
    }

    class TwoWheeler {
        +TwoWheeler(String regNo, boolean pass)
        +ratePerHour() double
    }

    class Car {
        +Car(String regNo, boolean pass)
        +ratePerHour() double
    }

    class ElectricCar {
        +ElectricCar(String regNo, boolean pass)
        +chargingFee(int kWh) double
    }

    class ParkingSpot {
        -int floor
        -int slot
        -Vehicle currentVehicle
        +getFloor() int
        +getSlot() int
        +isAvailable() boolean
        +park(Vehicle v) boolean
        +vacate() Vehicle
    }

    class ParkingTicket {
        -int counter$
        -String ticketId
        -Vehicle vehicle
        -int floor
        -int slot
        -long entryTimeMillis
        +getTicketId() String
        +getVehicle() Vehicle
        +getFloor() int
        +getSlot() int
    }

    class ParkingFacility {
        -ParkingSpot[][] slots
        -Map~String, ParkingTicket~ activeTickets
        -double totalDailyRevenue
        +checkIn(Vehicle vehicle) ParkingTicket
        +checkOut(String ticketId, int minutes, int kWh) double
        +getTotalDailyRevenue() double
    }

    Vehicle <|-- TwoWheeler
    Vehicle <|-- Car
    Car <|-- ElectricCar
    Chargeable <|.. ElectricCar
    ParkingSpot o-- Vehicle
    ParkingTicket o-- Vehicle
    ParkingFacility *-- ParkingSpot
    ParkingFacility o-- ParkingTicket
```

---

## 2. Adapted Day-4 Architecture Diagram (Task 12)

```mermaid
classDiagram
    direction TB

    class Pass {
        <<interface>>
        +isValid() boolean
        +getPassPrice() double
    }

    class OneDayPass {
        -long purchaseTimestamp
        -double PASS_PRICE$
        +isValid() boolean
        +getPassPrice() double
    }

    class PricingStrategy {
        <<interface>>
        +calculateFee(int minutes, LocalTime inTime, LocalTime outTime) double
    }

    class HourlyRateStrategy {
        -double ratePerHour
        +calculateFee(int minutes, LocalTime inTime, LocalTime outTime) double
    }

    class FlatFeeStrategy {
        -double flatFee
        +calculateFee(int minutes, LocalTime inTime, LocalTime outTime) double
    }

    class DeliveryVanPricingStrategy {
        -LocalTime PEAK_START$
        -LocalTime PEAK_END$
        -double peakRatePerHour
        -double offPeakRatePerHour
        +calculateFee(int minutes, LocalTime inTime, LocalTime outTime) double
    }

    class Vehicle {
        <<abstract>>
        -String regNo
        -PricingStrategy pricingStrategy
        -Pass activePass
        +calculateFee(int minutes, LocalTime inTime, LocalTime outTime) double
        +assignPass(Pass pass) void
    }

    class EBike {
        +EBike(String regNo)
    }

    class DeliveryVan {
        +DeliveryVan(String regNo)
    }

    class ExtendedParkingFacility {
        -Vehicle[][] slots
        -Map~String, int[]~ vehicleLocations
        +allocate(Vehicle vehicle) int[]
        +release(int floor, int slot) boolean
        +getFloorCapacity(int floor) int
        +getTotalCapacity() int
    }

    Pass <|.. OneDayPass
    PricingStrategy <|.. HourlyRateStrategy
    PricingStrategy <|.. FlatFeeStrategy
    PricingStrategy <|.. DeliveryVanPricingStrategy
    Vehicle o-- PricingStrategy
    Vehicle o-- Pass
    Vehicle <|-- EBike
    Vehicle <|-- DeliveryVan
    ExtendedParkingFacility o-- Vehicle
```

---

## 3. Vehicle Check-in & Allocation Sequence Diagram

```mermaid
sequenceDiagram
    autonumber
    actor Driver
    participant Gate as ParkingGate / App
    participant Facility as ParkingFacility
    participant Slot as ParkingSpot
    participant Ticket as ParkingTicket

    Driver->>Gate: Present Vehicle (RegNo, Type, PassStatus)
    Gate->>Facility: checkIn(Vehicle)
    Facility->>Facility: Verify activeTickets does not contain RegNo
    Facility->>Slot: isAvailable()
    Slot-->>Facility: true (First free slot)
    Facility->>Slot: park(Vehicle)
    Facility->>Ticket: new ParkingTicket(Vehicle, floor, slot)
    Ticket-->>Facility: Ticket instance (TKT-XXXX)
    Facility->>Facility: activeTickets.put(RegNo, Ticket)
    Facility-->>Gate: Return ParkingTicket
    Gate-->>Driver: Issue Ticket & Open Barrier
```
