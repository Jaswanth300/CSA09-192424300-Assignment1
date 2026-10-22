# Campus Smart-Parking System (Java Programming - CSA0921)

This repository contains the object-oriented implementation, empirical test evidence, architecture diagrams, and academic report for the Campus Smart-Parking System (SIMATS Java Programming Assignment 1, Total: 50 Marks). The project models a multi-level campus parking facility under OpenJDK 21 LTS, progressing through problem analysis, control logic, inheritance hierarchies, polymorphic billing, modular encapsulation, and Strategy Pattern design adaptation.

## Academic & Repository Details
- **Student Name**: Bala Naga Jaswanth
- **Registration Number**: 192424300
- **Course / Subject**: Java Programming
- **Course Code**: CSA0921
- **Target Java Version**: OpenJDK 21 LTS
- **GitHub Repository**: https://github.com/Jaswanth300/CSA09-192424300-Assignment1

---

## Repository Structure

```
Assessment-1/
├── README.md                                  # Project overview and execution guide
├── .gitignore                                 # Ignore rules for class binaries and temporary files
├── diagrams/                                  # Publication-quality architecture and UML diagrams
│   ├── class_diagrams.md                      # Diagram documentation & specifications
│   ├── generate_figma_diagrams.py             # Script to regenerate vector SVGs and 300 DPI PNGs
│   ├── task06_inheritance.(svg|png)           # Task 06 Vehicle inheritance hierarchy
│   ├── task10_packages.(svg|png)              # Task 10 Package encapsulation architecture
│   ├── task11_core_system.(svg|png)           # Task 11 Core system class diagram
│   ├── sequence_checkin.(svg|png)             # Task 11 Vehicle check-in sequence diagram
│   └── task12_adapted_system.(svg|png)        # Task 12 Strategy Pattern architecture
├── report/                                    # Authoritative final assignment report
│   ├── SIMATS_Java_Assignment_1_Report.docx   # Complete academic report (DOCX, < 10 MB)
│   ├── SIMATS_Java_Assignment_1_Report.pdf    # Final submission report (PDF, 2.25 MB)
│   ├── SIMATS_Java_Assignment_1_Report.md     # Full report source text and test tables
│   └── generate_docx_report.py                # Script to generate DOCX and export PDF
└── TASKS/                                     # Implementation of Tasks 01 through 12
    ├── Task01/ ... Task12/
    │   ├── .project, .classpath, .settings/   # Native Eclipse IDE project configuration
    │   ├── *.java (or src/ for Task10)        # Verified Java source files
    │   └── evidence/
    │       ├── screenshot_TaskXX.png          # Genuine Eclipse IDE execution screenshot
    │       └── terminal_output.txt            # Captured console execution transcript
```

---

## Task Overviews

| Task | Marks | Core Demonstration & Key Result |
| :--- | :---: | :--- |
| **Task 01** | 4 | Domain entity extraction (`Vehicle`, `ParkingSpot`, `ParkingTicket`, `ParkingFacility`). Single occupancy validation and double-booking rejection. Comparison of object model vs parallel arrays. |
| **Task 02** | 3 | Data representation and integer division truncation bug (`59 / 60 = 0` hrs $\rightarrow$ Rs 0.0) vs safe integer ceiling `(59 + 59) / 60 = 1` hr ($\rightarrow$ Rs 30.0). Jagged arrays for non-uniform floor capacities. |
| **Task 03** | 4 | Control logic and slot allocation in `ParkingFacility.java`. Sequential 2D array search, freeing slots, and re-allocation matching benchmark: `[0, 0]`, `[0, 1]`, `[1, 0]`, `null`, `true false false`, `[0, 1]`. |
| **Task 04** | 4 | Constructor design with `this(...)` chaining. Copy constructor for issuing duplicate tickets while preserving original entry timestamps. Stack vs heap memory lifecycle. |
| **Task 05** | 3 | Static sequence generator (`TKT-0001`...) for globally unique ticket IDs, active facility occupancy tracking, and JVM Garbage Collection eligibility upon exit. |
| **Task 06** | 4 | Hybrid inheritance hierarchy (`Vehicle` $\rightarrow$ `TwoWheeler`, `Vehicle` $\rightarrow$ `Car` $\rightarrow$ `ElectricCar`). Constructor execution order tracing: `Object` $\rightarrow$ `Vehicle` $\rightarrow$ `Car` $\rightarrow$ `ElectricCar`. |
| **Task 07** | 4 | Overriding `ratePerHour()`, `toString()`, `equals()`, and `hashCode()`. Domain identity based on registration plate numbers to ensure consistent `HashSet` duplicate prevention. |
| **Task 08** | 4 | Polymorphic dynamic method dispatch in `FeeDemo.java`. Ceiling arithmetic and runtime type dispatch: `TwoWheeler -> Rs 30.0`, `Car -> Rs 90.0`, `ElectricCar -> Rs 210.0`, `true`. |
| **Task 09** | 4 | Abstract class `Vehicle` for state inheritance and orthogonal capability interfaces `Chargeable` and `Payable`. Default interface methods for service surcharges. |
| **Task 10** | 3 | Package encapsulation across `parking.model`, `parking.service`, and `parking.app`. Defensive mutator input validation and analysis of Java's `IntegerCache` autoboxing trap. |
| **Task 11** | 5 | Integrated parking system verification suite. Tests nominal entries, floor exhaustion, duplicate checks, zero-duration grace, and exact match to hand-calculated revenue benchmark (**Rs 230.0**). |
| **Task 12** | 8 | Day-4 Design Challenge: Decoupled pricing using the Strategy Pattern (`PricingStrategy`). Handles E-Bike flat fees (Rs 15.0), Delivery Van peak (9 AM–5 PM, Rs 120 vs Rs 60 off-peak), 24-hr One-Day Pass (Rs 100), and 5-floor non-uniform layout (100 total slots). |

---

## How to Compile and Run Each Task

All tasks compile cleanly under OpenJDK 21 LTS with zero warnings. Run the following commands from the repository root (`D:\Data\Development\College\Java\Assessment-1`):

### Task 01: Problem Analysis & Object Modeling
```powershell
javac -encoding UTF-8 -Xlint:all TASKS/Task01/Task01_ModelAnalysis.java
java -cp TASKS/Task01 Task01_ModelAnalysis
```

### Task 02: Data Representation & Casting Analysis
```powershell
javac -encoding UTF-8 -Xlint:all TASKS/Task02/DataRepresentationDemo.java
java -cp TASKS/Task02 DataRepresentationDemo
```

### Task 03: Control Logic (ParkingFacility)
```powershell
javac -encoding UTF-8 -Xlint:all TASKS/Task03/ParkingFacility.java
java -cp TASKS/Task03 ParkingFacility
```

### Task 04: Classes, Objects & Constructor Chaining
```powershell
javac -encoding UTF-8 -Xlint:all TASKS/Task04/ConstructorDemo.java
java -cp TASKS/Task04 ConstructorDemo
```

### Task 05: Static Members & Object Lifecycle
```powershell
javac -encoding UTF-8 -Xlint:all TASKS/Task05/TicketLifecycleDemo.java
java -cp TASKS/Task05 TicketLifecycleDemo
```

### Task 06: Inheritance Hierarchy & Constructor Trace
```powershell
javac -encoding UTF-8 -Xlint:all TASKS/Task06/VehicleHierarchyDemo.java
java -cp TASKS/Task06 VehicleHierarchyDemo
```

### Task 07: Method Overriding & Object Contracts
```powershell
javac -encoding UTF-8 -Xlint:all TASKS/Task07/VehicleOverrideDemo.java
java -cp TASKS/Task07 VehicleOverrideDemo
```

### Task 08: Polymorphism & Dynamic Dispatch (FeeDemo)
```powershell
javac -encoding UTF-8 -Xlint:all TASKS/Task08/FeeDemo.java
java -cp TASKS/Task08 FeeDemo
```

### Task 09: Abstraction & Multiple Interfaces
```powershell
javac -encoding UTF-8 -Xlint:all TASKS/Task09/AbstractionDemo.java
java -cp TASKS/Task09 AbstractionDemo
```

### Task 10: Package Encapsulation & Access Modifiers
```powershell
javac -encoding UTF-8 -d TASKS/Task10/bin (Get-ChildItem -Path TASKS/Task10/src -Recurse -Filter "*.java" | ForEach-Object { $_.FullName })
java -cp TASKS/Task10/bin parking.app.MainApp
```

### Task 11: End-to-End System Integration Suite
```powershell
javac -encoding UTF-8 -Xlint:all TASKS/Task11/SmartParkingSystem.java
java -cp TASKS/Task11 SmartParkingSystem
```

### Task 12: Day-4 Design Challenge (Adapted System)
```powershell
javac -encoding UTF-8 -Xlint:all TASKS/Task12/AdaptedSmartParkingSystem.java
java -cp TASKS/Task12 AdaptedSmartParkingSystem
```

---

## Deliverables & Evidence Location

- **Authoritative PDF Report**: [`report/SIMATS_Java_Assignment_1_Report.pdf`](report/SIMATS_Java_Assignment_1_Report.pdf) (2.25 MB, formatted to academic standard, verified below the 10 MB portal threshold).
- **Authoritative Editable Report**: [`report/SIMATS_Java_Assignment_1_Report.docx`](report/SIMATS_Java_Assignment_1_Report.docx) (4.19 MB).
- **Report Markdown Source**: [`report/SIMATS_Java_Assignment_1_Report.md`](report/SIMATS_Java_Assignment_1_Report.md).
- **Figma UML & Architecture Diagrams**: Located in [`diagrams/`](diagrams/) as dual SVG vectors and 300 DPI PNGs.
- **Genuine Execution Screenshots**: Located in `TASKS/TaskXX/evidence/screenshot_TaskXX.png` for all 12 tasks.
- **Console Output Transcripts**: Located in `TASKS/TaskXX/evidence/terminal_output.txt` for all 12 tasks.
