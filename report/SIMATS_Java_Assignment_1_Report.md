# SIMATS Online Programming Lab — Assignment Report
## Programming in Java (CSA0921)
### Assignment Title: Design, Implement and Validate an Object-Oriented Model for a Campus Smart-Parking System
**Total Marks: 50 Marks**

---

### Student & Submission Metadata
- **Student Name**: Bala Naga Jaswanth
- **Registration Number**: 192424300
- **Course / Subject**: Java Programming
- **Course Code**: `CSA0921`
- **Submission Repository**: `CSA09-192424300-Assignment1`
- **GitHub Repository Link**: `https://github.com/Jaswanth300/CSA09-192424300-Assignment1`
- **Target Java Version**: OpenJDK 21 LTS

---

## Executive Summary

This report documents the architectural design, implementation, and empirical validation of a **Campus Smart-Parking System** completed for SIMATS Java Programming Assignment 1. The assignment systematically develops and evaluates an object-oriented solution progressing through problem analysis, data representation, control logic, encapsulation, inheritance, polymorphism, abstraction, and dynamic system adaptation under changing real-world constraints.

---

## Mark Distribution Matrix

| Task # | Task Description | Bloom's Level | Marks | Status |
| :---: | :--- | :---: | :---: | :---: |
| **Task 01** | Understand the Problem & Identify the Objects | K4 – Analyse | 4 | Complete & Verified |
| **Task 02** | Choose the Data Representation & Casting Analysis | K4 – Analyse | 3 | Complete & Verified |
| **Task 03** | Design Control Logic & Complete ParkingFacility Code | K4 – Analyse | 4 | Complete & Verified |
| **Task 04** | Design Classes, Objects & Constructors | K4 – Analyse | 4 | Complete & Verified |
| **Task 05** | Apply static Members & Object Life Cycle | K4 – Analyse | 3 | Complete & Verified |
| **Task 06** | Design the Inheritance Hierarchy & Constructor Trace | K4 – Analyse | 4 | Complete & Verified |
| **Task 07** | Override & Specialise Behaviour | K4 – Analyse | 4 | Complete & Verified |
| **Task 08** | Apply Polymorphism & Complete FeeDemo Code | K4 – Analyse | 4 | Complete & Verified |
| **Task 09** | Introduce Abstraction (Abstract Classes & Interfaces) | K4 – Analyse | 4 | Complete & Verified |
| **Task 10** | Enforce Encapsulation through Modular Packages | K4 – Analyse | 3 | Complete & Verified |
| **Task 11** | End-to-End System Integration, Testing & Defence | K5 – Evaluate | 5 | Complete & Verified |
| **Task 12** | Day-4 Design Challenge (E-Bikes, Vans, 24-hr Pass, 5 Floors) | K5 – Evaluate | 8 | Complete & Verified |
| **TOTAL** | **Consolidated Assignment Total** | | **50** | **Completed & Verified** |

---

## Detailed Task Documentation

### TASK 1 – Understand the Problem and Identify the Objects
**Marks**: 4 Marks | **Bloom’s Level**: K4 – Analyse

#### 1. System Inputs & Outputs
- **Inputs**: Vehicle arrival details (Registration number, vehicle type: Two-Wheeler, Car, EV; pass holder status: Student/Staff monthly pass vs. Visitor), charging request (kWh), exit timestamp.
- **Outputs**: Allocated slot location (Floor, Slot number), unique parking ticket ID, departure receipt itemizing parking duration, hourly rate, EV power fee, pass discount, and total payable amount.

#### 2. Candidate Classes & Methods
- **`Vehicle`**: Noun representing any vehicle entering the campus. Verbs: `getRegNo()`, `ratePerHour()`, `calculateParkingFee()`.
- **`ParkingSpot`**: Noun representing a physical slot. Verbs: `isAvailable()`, `park()`, `vacate()`.
- **`ParkingTicket`**: Noun representing contract issued on entry. Verbs: `getTicketId()`, `getEntryTime()`.
- **`ParkingFacility`**: Noun representing the multilevel physical garage. Verbs: `checkIn()`, `checkOut()`, `allocate()`, `release()`.

#### 3. Business Rules
1. **Single Occupancy**: A slot cannot hold more than one vehicle simultaneously.
2. **Ticket Immutability**: Ticket numbers are strictly unique and never reused.
3. **Pass Exemption**: Students and staff with valid monthly passes park free of charge for standard parking.
4. **Guaranteed Allocation**: A vehicle is only admitted if a suitable vacant slot exists.

#### 4. Assumptions
- Floors are numbered $0$ to $F-1$; slots per floor are numbered $0$ to $S-1$.
- Entry and exit are controlled by automated barriers.

#### 5. Alternative Concept & Complexity Analysis
- **Approach A (Object-Oriented Design - `Vehicle[] vehicles`)**:
  - Encapsulates state (`regNo`, `type`, `entryTime`) in a coherent entity.
  - Linear search by plate: $O(N)$ time.
  - Adding a new attribute (e.g. `ownerContact`): Requires adding a single field to `Vehicle` ($O(1)$ structural change).
  - Space: Contiguous array of pointers + single object header per vehicle.
- **Approach B (Parallel Arrays - `String[] regNo, String[] type, long[] entryTime`)**:
  - Linear search by plate: $O(N)$ time.
  - Adding a new attribute: Requires allocating and maintaining a completely new parallel array across all functions.
  - Integrity: Extreme desynchronization risk when sorting, swapping, or deleting records.

#### 6. Test Evidence & Results
- Source: [`TASKS/Task01/Task01_ModelAnalysis.java`](../TASKS/Task01/Task01_ModelAnalysis.java)
- Output Log: [`TASKS/Task01/evidence/terminal_output.txt`](../TASKS/Task01/evidence/terminal_output.txt)

| Test ID | Scenario | Input | Expected Output | Actual Output | Status |
| :--- | :--- | :--- | :--- | :--- | :---: |
| TC_01_01 | Nominal Parking | Car TN01AB1111 at (0,0) | Allocates successfully | true, Spot occupied | Pass |
| TC_01_02 | Double Booking | Attempt park Bike at occupied (0,0) | Rejected (Denied) | PASS (Denied) | Pass |
| TC_01_03 | Vacating Spot | Vacate (0,0) | Spot becomes free | TN01AB1111 vacated | Pass |

---

### TASK 2 – Choose the Data Representation
**Marks**: 3 Marks | **Bloom’s Level**: K4 – Analyse

#### 1. Data Types & Precision Loss Analysis
- `slotNumber`, `floorIndex`: `int` (compact, non-negative discrete indexes).
- `vehicleNumber`: `String` (alphanumeric license format).
- `entryTime`: `long` (epoch milliseconds) or `LocalDateTime` (date-time arithmetic).
- `duration`: `int` minutes (sufficient for durations up to millions of minutes).
- `parkingFee`: `double` (currency amount requiring fractional representation).

#### 2. Casting Risk & Truncation Bug Demonstration
When calculating fees from minutes, naive integer division `minutes / 60` drops the remainder:
- For 59 minutes: $59 / 60 = 0$ hours $\rightarrow$ Fee = $0 \times 30 = \text{Rs } 0.0$ (Severe revenue leakage).
- **Resolution**: Pure integer arithmetic ceiling:
  $$\text{billableHours} = \frac{\text{minutes} + 59}{60}$$
  For 59 minutes: $(59 + 59) / 60 = 118 / 60 = 1$ hour $\rightarrow$ Fee = $\text{Rs } 30.0$.
  Or floating ceiling: `(int) Math.ceil(minutes / 60.0)`.

#### 3. Array Representation: Jagged vs Flat 1D Array
- **Jagged Array (`String[][]` / `ParkingSpot[][]`)**: Fits real garages where lower floors hold more vehicles than upper floors. Wasted memory = 0 cells.
- **Flat 1D Array (`index = floor * slotsPerFloor + slot`)**: Requires uniform rectangular dimensions. If floors have different capacities, flat indexing breaks or requires wasting slots allocated to the maximum floor size.

#### 4. Test Evidence & Results
- Source: [`TASKS/Task02/DataRepresentationDemo.java`](../TASKS/Task02/DataRepresentationDemo.java)
- Output Log: [`TASKS/Task02/evidence/terminal_output.txt`](../TASKS/Task02/evidence/terminal_output.txt)

| Test ID | Scenario | Input | Expected Output | Actual Output | Status |
| :--- | :--- | :--- | :--- | :--- | :---: |
| TC_02_01 | Naive Division | 59 mins @ Rs 30/hr | Rs 0.0 (Bug) | 0 hrs -> Rs 0.0 | Pass |
| TC_02_02 | Safe Integer Math | (59 + 59) / 60 | 1 hr -> Rs 30.0 | 1 hr -> Rs 30.0 | Pass |
| TC_02_03 | Jagged Array | Floor 0 (3 slots), Floor 1 (2 slots) | Valid access, 0 padding | Slot (0,0) & (1,1) allocated | Pass |

---

### TASK 3 – Design the Control Logic & Complete the Code
**Marks**: 4 Marks | **Bloom’s Level**: K4 – Analyse

#### 1. Algorithmic Pseudocode (SIMATS Engineering Standard)
```
ALGORITHM AllocateSlot(vehicleNo, slots[][])
INPUT: vehicleNo as String, slots as 2D array of String
OUTPUT: Array of integer [floor, slot] or null

BEGIN
    FOR floor <- 0 TO Length(slots) - 1 DO
        FOR slot <- 0 TO Length(slots[floor]) - 1 DO
            IF slots[floor][slot] IS null THEN
                slots[floor][slot] <- vehicleNo
                RETURN [floor, slot]
            END IF
        END FOR
    END FOR
    RETURN null
END
```

```
ALGORITHM ReleaseSlot(floor, slot, slots[][])
INPUT: floor as Integer, slot as Integer, slots as 2D array of String
OUTPUT: Boolean (true if released, false otherwise)

BEGIN
    IF floor < 0 OR floor >= Length(slots) THEN
        RETURN false
    END IF
    IF slot < 0 OR slot >= Length(slots[floor]) THEN
        RETURN false
    END IF
    IF slots[floor][slot] IS null THEN
        RETURN false
    END IF
    slots[floor][slot] <- null
    RETURN true
END
```

#### 2. Implementation & Exact Output Verification
- Source: [`TASKS/Task03/ParkingFacility.java`](../TASKS/Task03/ParkingFacility.java)
- Output Log: [`TASKS/Task03/evidence/terminal_output.txt`](../TASKS/Task03/evidence/terminal_output.txt)

**Expected Output vs Actual Output**:
```
[0, 0]
[0, 1]
[1, 0]
null
true false false
[0, 1]
```
*Exact character-for-character match verified.*

#### 3. Alternative Concept Analysis
- **Linear 2D Scan**: Time $O(F \times S)$, Extra Space $O(1)$.
- **Free-Slot Stack per Floor**: Time $O(1)$ for `allocate()` and `release()`, Extra Space $O(F \times S)$ pointers.

---

### TASK 4 – Design Classes, Objects and Constructors
**Marks**: 4 Marks | **Bloom’s Level**: K4 – Analyse

#### 1. Constructor Design & Chaining
- Implemented default constructor, full parameterized constructor, overloaded convenience constructor using `this(...)` chaining, and copy constructor.
- **Copy Constructor Application**: Re-issuing lost or damaged parking tickets. It duplicates the vehicle number, floor, slot, and entry timestamp onto a new ticket ID without resetting the entry time.

#### 2. Memory Model
- **Stack**: Stores thread call frames, primitive locals, and reference pointers (`tkt1`, `tktDuplicate`).
- **Heap**: Stores actual object payloads (`ParkingTicket` instances, strings, backing arrays).

#### 3. Test Evidence & Results
- Source: [`TASKS/Task04/ConstructorDemo.java`](../TASKS/Task04/ConstructorDemo.java)
- Output Log: [`TASKS/Task04/evidence/terminal_output.txt`](../TASKS/Task04/evidence/terminal_output.txt)

| Test ID | Scenario | Constructor Used | Expected State | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :---: |
| TC_04_01 | Default Ticket | `ParkingTicket()` | ID: TKT-DEFAULT, Plate: UNKNOWN | Verified | Pass |
| TC_04_02 | Chained Overload | `ParkingTicket(id, plate, f, s)` | Current timestamp auto-populated | Verified | Pass |
| TC_04_03 | Copy Constructor | `ParkingTicket(orig, newId)` | Same vehicle/entry time, distinct object | Same data, true ref distinct | Pass |

---

### TASK 5 – Apply static Members and the Object Life Cycle
**Marks**: 3 Marks | **Bloom’s Level**: K4 – Analyse

#### 1. Static Members Justification
- `ticketCounter` (`static int`): Belongs to the facility class as a whole. Ensures every ticket issued receives a strictly increasing, unique sequence number. An instance variable would reset to 1 on every new ticket object.
- `activeOccupancy` (`static int`): Tracks total vehicles currently inside across all floors.

#### 2. Object Lifecycle & Garbage Collection Trace
- Ticket object created upon vehicle entry $\rightarrow$ stored in active tracking data structure.
- Upon departure and fee payment, reference is removed (`t1 = null`).
- Unreachable object becomes immediately eligible for Garbage Collection (GC).

#### 3. Test Evidence & Results
- Source: [`TASKS/Task05/TicketLifecycleDemo.java`](../TASKS/Task05/TicketLifecycleDemo.java)
- Output Log: [`TASKS/Task05/evidence/terminal_output.txt`](../TASKS/Task05/evidence/terminal_output.txt)

| Test ID | Scenario | Action | Expected Output | Actual Output | Status |
| :--- | :--- | :--- | :--- | :--- | :---: |
| TC_05_01 | Monotonic Counter | Issue 3 tickets | TKT-0001, TKT-0002, TKT-0003 | Correct sequence | Pass |
| TC_05_02 | Live Occupancy | Park 3, exit 1 | Count: 3 -> 2 | Live count: 2 | Pass |
| TC_05_03 | Pass-by-Value | Set local param to null | Caller reference untouched | Caller ref intact | Pass |

---

### TASK 6 – Design the Inheritance Hierarchy
**Marks**: 4 Marks | **Bloom’s Level**: K4 – Analyse

#### 1. Class Hierarchy Design
- Base Superclass: `Vehicle` (holds `regNo`, base `ratePerHour()`).
- Hierarchical Subclasses: `TwoWheeler` (Rs 10/hr), `Car` (Rs 30/hr).
- Multilevel Subclass: `ElectricCar extends Car` (Rs 30/hr + battery charging).

#### 2. Constructor Execution Order Trace
When instantiating `new ElectricCar("TN03EV9999", 40.0)`:
1. `java.lang.Object` constructor executes.
2. `Vehicle` constructor executes via `super(regNo)`.
3. `Car` constructor executes via `super(regNo)`.
4. `ElectricCar` constructor body completes.

#### 3. Test Evidence & Results
- Source: [`TASKS/Task06/VehicleHierarchyDemo.java`](../TASKS/Task06/VehicleHierarchyDemo.java)
- Output Log: [`TASKS/Task06/evidence/terminal_output.txt`](../TASKS/Task06/evidence/terminal_output.txt)

| Test ID | Vehicle Type | Hierarchy Type | Rate Verified | Constructor Trace | Status |
| :--- | :--- | :--- | :--- | :--- | :---: |
| TC_06_01 | ElectricCar | Multilevel (Vehicle->Car->ElectricCar) | Rs 30.0/hr | Object->Vehicle->Car->ElectricCar | Pass |
| TC_06_02 | TwoWheeler | Hierarchical (Vehicle->TwoWheeler) | Rs 10.0/hr | Object->Vehicle->TwoWheeler | Pass |

---

### TASK 7 – Override and Specialise Behaviour
**Marks**: 4 Marks | **Bloom’s Level**: K4 – Analyse

#### 1. Method Overriding & Object Contracts
- Overrode `ratePerHour()` in each subclass.
- Overrode `equals()` and `hashCode()` in `Vehicle` using `regNo`. Two vehicle objects with the same registration number are treated as equal in hash sets and maps, preventing duplicate entries.
- Applied `final` keyword to `regNo` to guarantee immutability.

#### 2. Test Evidence & Results
- Source: [`TASKS/Task07/VehicleOverrideDemo.java`](../TASKS/Task07/VehicleOverrideDemo.java)
- Output Log: [`TASKS/Task07/evidence/terminal_output.txt`](../TASKS/Task07/evidence/terminal_output.txt)

| Test ID | Test Case | Method | Expected Output | Actual Output | Status |
| :--- | :--- | :--- | :--- | :--- | :---: |
| TC_07_01 | Fee Overriding | `calculateFee(130)` | Bike: Rs 30, Car: Rs 90 | Rs 30.0, Rs 90.0 | Pass |
| TC_07_02 | Hash Equality | `equals()` & `hashCode()` | Same plate -> equals=true | true, hashCodes match | Pass |
| TC_07_03 | Set Deduplication | `HashSet.add()` duplicate plate | Rejected (false) | SUCCESS (Deduplicated) | Pass |

---

### TASK 8 – Apply Polymorphism & Complete the Code
**Marks**: 4 Marks | **Bloom’s Level**: K4 – Analyse

#### 1. Implementation & Output Verification
- Source: [`TASKS/Task08/FeeDemo.java`](../TASKS/Task08/FeeDemo.java)
- Output Log: [`TASKS/Task08/evidence/terminal_output.txt`](../TASKS/Task08/evidence/terminal_output.txt)

**Expected Output vs Actual Output**:
```
TwoWheeler TN01A1 -> Rs 30.0
Car TN02B2 -> Rs 90.0
ElectricCar TN03C3 -> Rs 210.0
true
```
*Exact character-for-character match verified.*

#### 2. Equality Semantics & Separate Registration Uniqueness Enforcement
The last line `new Car("TN02B2").equals(new ElectricCar("TN02B2"))` prints `true`.
- **Reason**: The overridden `equals()` method in `Vehicle` checks `o instanceof Vehicle` and compares `regNo.equals(...)`. Because `ElectricCar` is a subclass of `Car` (which extends `Vehicle`), it passes the `instanceof Vehicle` test. Since both instances share the license plate `"TN02B2"`, they evaluate as equal.
- **Architectural Consequence**: Equality based strictly on registration number allows instances of different subclasses to equate as identical in Java collections.
- **Enforcement Principle**: In reality, a physical car cannot simultaneously be a standard car and an electric car. Therefore, **registration-number uniqueness must be enforced separately at the facility entry/allocation boundary** (e.g., checking that no active vehicle already has that plate before issuing a ticket) rather than relying on class identity alone.

---

### TASK 9 – Introduce Abstraction
**Marks**: 4 Marks | **Bloom’s Level**: K4 – Analyse

#### 1. Abstract Class vs Interface Rationale
- **`Vehicle` (Abstract Class)**: Appropriate because all vehicles share common state (`regNo`), require constructor validation, and implement common template algorithms (`calculateFee`).
- **`Chargeable` (Interface)**: Appropriate because battery charging is a pluggable capability that only certain vehicles possess. It does not dictate inheritance hierarchy and supports multiple inheritance of type.
- **Default & Static Methods**: `getEmergencySurcharge()` implemented as a default method; `isValidChargingDuration()` implemented as a static utility method on the interface.

#### 2. Test Evidence & Results
- Source: [`TASKS/Task09/AbstractionDemo.java`](../TASKS/Task09/AbstractionDemo.java)
- Output Log: [`TASKS/Task09/evidence/terminal_output.txt`](../TASKS/Task09/evidence/terminal_output.txt)

| Test ID | Capability | Implementation | Value | Status |
| :--- | :--- | :--- | :--- | :---: |
| TC_09_01 | Base Parking | Abstract `Vehicle.calculateFee(120)` | Rs 60.0 | Pass |
| TC_09_02 | EV Power Grid | Interface `Chargeable.chargingFee(15)` | Rs 180.0 | Pass |
| TC_09_03 | Default Interface Method | `getEmergencySurcharge()` | Rs 25.0 | Pass |
| TC_09_04 | Multiple Interfaces | `implements Chargeable, Payable` | Rs 252.00 (Tax included) | Pass |

---

### TASK 10 – Enforce Encapsulation through Packages and Access Modifiers
**Marks**: 3 Marks | **Bloom’s Level**: K4 – Analyse

#### 1. Package Structure & Visibility
- `parking.model`: `Vehicle`, `Car`, `TwoWheeler`, `ParkingSpot`.
- `parking.service`: `ParkingService` (allocates spots, calculates fees).
- `parking.app`: `MainApp` (executable entry point).
- Access modifiers: Fields are `private`; business methods are `public`; internal helper mutators are `protected` or package-private.

#### 2. Autoboxing & Wrapper Class Trap
- Demonstrated `Integer` comparison with `==`:
  - `cachedA == cachedB` (values 100): evaluates to `true` due to JVM `IntegerCache` range $[-128, 127]$.
  - `nonCachedA == nonCachedB` (values 200): evaluates to `false` because distinct objects are allocated on the Heap!
  - `nonCachedA.equals(nonCachedB)`: correctly evaluates to `true` using value comparison.

#### 3. Test Evidence & Results
- Source: [`TASKS/Task10/src/parking/app/MainApp.java`](../TASKS/Task10/src/parking/app/MainApp.java)
- Output Log: [`TASKS/Task10/evidence/terminal_output.txt`](../TASKS/Task10/evidence/terminal_output.txt)

| Test ID | Scenario | Input | Expected Output | Status |
| :--- | :--- | :--- | :--- | :---: |
| TC_10_01 | Defensive Duration | Negative duration (-15 mins) | Rejected: `IllegalArgumentException` | Pass |
| TC_10_02 | Defensive Plate | Blank plate ("   ") | Rejected: `IllegalArgumentException` | Pass |
| TC_10_03 | Wrapper Cache Trap | 200 == 200 vs .equals() | == is false, .equals() is true | Pass |

---

### TASK 11 – Implement, Test and Defend the Design
**Marks**: 5 Marks | **Bloom’s Level**: K5 – Evaluate

#### 1. Comprehensive System Verification
Implemented an integrated parking facility covering normal operations, edge boundaries, and invalid cases.
- **Zero-Minute Parking**: Evaluates to **Rs 0.0**, matching Task 08's formula $\lfloor (0 + 59)/60 \rfloor = 0$ hours (grace turnaround).
- **Slab Boundary**: 60 minutes = 1 billable hour (Rs 30.0); 61 minutes = 2 billable hours (Rs 60.0).

#### 2. Hand-Calculated Revenue Scenario Comparison
- **Vehicle 1**: Car (Visitor), 120 mins $\rightarrow 2 \times 30 = \text{Rs } 60.0$.
- **Vehicle 2**: TwoWheeler (Visitor), 90 mins $\rightarrow 2 \times 10 = \text{Rs } 20.0$.
- **Vehicle 3**: ElectricCar (Visitor), 60 mins parking + 10 kWh charging $\rightarrow 1 \times 30 + 10 \times 12 = \text{Rs } 150.0$.
- **Vehicle 4**: Car (Student Monthly Pass), 180 mins $\rightarrow \text{Rs } 0.0$ (Free).
- **Vehicle 5**: Car (Visitor), 0 mins $\rightarrow \text{Rs } 0.0$.
- **Hand-Calculated Total**: $60.0 + 20.0 + 150.0 + 0.0 + 0.0 = \textbf{Rs 230.0}$.
- **Actual Program Output**: **Rs 230.0** (Exact Match).

#### 3. Test Evidence & Results
- Source: [`TASKS/Task11/SmartParkingSystem.java`](../TASKS/Task11/SmartParkingSystem.java)
- Output Log: [`TASKS/Task11/evidence/terminal_output.txt`](../TASKS/Task11/evidence/terminal_output.txt)

---

### TASK 12 – Day-4 Design Challenge: Re-evaluate and Adapt the Design
**Marks**: 8 Marks | **Bloom’s Level**: K5 – Evaluate

#### Changed Requirements & Documented Engineering Assumptions
1. **E-Bikes Category**: Charged a flat fee regardless of duration.
   - *Documented Engineering Assumption*: Flat fee set to **Rs 15.0**.
2. **Delivery Vans Category**: Charged double the car rate during 9 AM–5 PM.
   - *Documented Engineering Assumption*: Rate is  \times 30 = \textbf{Rs 60.0/hr}$ during 9 AM–5 PM; off-peak rate outside 9 AM–5 PM is standard car rate of **Rs 30.0/hr**.
   - *Rounding Policy*: The session is partitioned into peak minutes (09:00–17:00) and off-peak minutes (outside 09:00–17:00). Each segment is independently rounded up using the ceiling rule (minutes + 59) / 60, then multiplied by its rate.
3. **One-Day Pass Feature**: Visitors can purchase a 24-hour pass for unlimited entry/exit.
   - *Documented Engineering Assumption*: Pass price set to flat **Rs 100.0**, activated with an issue timestamp and valid for exactly 24 hours. A valid pass waives the parking exit fee (Rs 0.0); an expired pass charges the standard stay duration.
4. **Extended Facility**: 5 floors with non-uniform slots per floor.
   - *Documented Engineering Assumption*: Floor capacities: Floor 0: 30, Floor 1: 25, Floor 2: 20, Floor 3: 15, Floor 4: 10 slots (Total: 100 slots).

#### Subpart A: Impact Analysis (2 Marks)
- The introduction of non-uniform slot counts across 5 floors invalidates rectangular arrays. A jagged array Vehicle[][] is mandatory to avoid allocating memory for non-existent slots on higher floors.
- Dynamic time-dependent rates (Delivery Vans) and duration-independent fees (E-Bikes) break the simple static 
atePerHour() contract from earlier tasks.

#### Subpart B: Evaluation of Original Design (2 Marks)
- The original design embedded rate calculations directly inside vehicle classes. Adding surge pricing or time-of-day windows would require rewriting vehicle classes, violating the Open-Closed Principle (OCP).
- **Redesign Decision**: Decouple pricing into a PricingStrategy interface (Strategy Pattern).

#### Subpart C: Adaptation or Redesign Implementation (2 Marks)
- Implemented PricingStrategy hierarchy: HourlyPricing, FlatPricing, and DeliveryPricing (handling 9 AM–5 PM peak vs off-peak window overlap).
- Implemented Pass interface and OneDayPass class (managing 24-hour validity window from issue time).
- Configured 5-floor jagged facility (int[]{30, 25, 20, 15, 10}).

#### Subpart D: Evidence-Based Defence (2 Marks)
- **Zero Existing Class Modifications**: By refactoring to the Strategy pattern, new vehicle categories and dynamic pricing rules were introduced purely by extension.
- **Empirical Test Verification**:
  1. **E-Bike**: Flat fee Rs 15.0 regardless of stay duration.
  2. **Delivery Van (Peak-Only)**: 10:00–12:00 (2h @ Rs 60/hr) = Rs 120.0.
  3. **Delivery Van (Off-Peak-Only)**: 06:00–08:00 (2h @ Rs 30/hr) = Rs 60.0.
  4. **Delivery Van (Boundary Crossing)**: 08:00–10:00 (1h off-peak @ Rs 30 + 1h peak @ Rs 60) = Rs 90.0.
  5. **One-Day Pass (Valid)**: Exit at 5h after issue = Rs 0.0 (fee waived).
  6. **One-Day Pass (Expired)**: Exit at 26h after issue = Rs 60.0 (charged 2h standard stay @ Rs 30/hr).
  7. **5-Floor Jagged Allocation**: Total 100 slots allocated and managed without index errors.

| Test ID | Scenario & Inputs | Applied Strategy | Expected Output | Actual Output | Status |
| :--- | :--- | :--- | :--- | :--- | :---: |
| TC_12_01 | E-Bike Flat Fee | FlatPricing(15.0) | Fixed Rs 15.0 | Rs 15.0 | Pass |
| TC_12_02 | Van Peak Only (10:00–12:00) | DeliveryPricing (Peak 60/hr) | 2 hrs * Rs 60 = Rs 120.0 | Rs 120.0 | Pass |
| TC_12_03 | Van Off-Peak (06:00–08:00) | DeliveryPricing (Off-Peak 30/hr) | 2 hrs * Rs 30 = Rs 60.0 | Rs 60.0 | Pass |
| TC_12_04 | Van Boundary (08:00–10:00) | DeliveryPricing (1h off + 1h pk) | 1*30 + 1*60 = Rs 90.0 | Rs 90.0 | Pass |
| TC_12_05 | One-Day Pass (5h, Valid) | OneDayPass (Price Rs 100.0) | Valid: true, Exit waived: Rs 0.0 | Valid: true, Exit: Rs 0.0 | Pass |
| TC_12_06 | One-Day Pass (26h, Expired) | OneDayPass (Expired) | Valid: false, Charge 2h: Rs 60.0 | Valid: false, Fee: Rs 60.0 | Pass |
| TC_12_07 | 5-Floor Jagged Capacity | AdaptedParkingFacility | Total slots: 100 | Total slots: 100 | Pass |


