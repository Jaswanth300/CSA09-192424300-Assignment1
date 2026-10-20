# SIMATS Java Programming Assignment 1: Campus Smart-Parking System

## Student Credentials
- **Student Name**: BALA NAGA JASWANTH
- **Register Number**: 192424300C
- **Course Outcome**: CO1: Java fundamentals and object-oriented programming
- **Mandatory Repository Name**: `CSA09-192424300C-Assignment1`
- **GitHub Repository Link**: `https://github.com/Jaswanth/CSA09-192424300C-Assignment1`
- **Target Java Version**: OpenJDK 21 LTS (`javac 21.0.12.1`)
- **Shell Environment**: Windows PowerShell 5.1

---

## Project Overview

This repository contains the complete, verified implementation and documentation for the **Campus Smart-Parking System** (SIMATS Java Programming Assignment 1, Total: 50 Marks). Every task has been designed and implemented in strict accordance with the official assignment specification (`SIMATS_Java_Assignment_1.pdf`), compiled with zero warnings under OpenJDK 21, and backed by genuine execution output logs.

---

## Directory Structure

```
Assessment-1/
├── .gitignore                                 # Git ignore file for binaries and logs
├── README.md                                  # Repository overview, compile & run instructions
├── SIMATS_Java_Assignment_1.pdf               # Original assignment specification (Source of Truth)
├── diagrams/
│   └── class_diagrams.md                      # UML Class, Sequence & Strategy diagrams
├── evidence/
│   ├── Task01/ ... Task12/
│   │   ├── terminal_output.txt                # Genuine captured console output transcripts
│   │   └── screenshot_TaskXX.png              # Windows Snipping Tool execution screenshots
├── report/
│   └── SIMATS_Java_Assignment_1_Report.md     # Comprehensive 50-mark academic report
├── Task01/
│   └── Task01_ModelAnalysis.java              # Problem analysis & candidate object models
├── Task02/
│   └── DataRepresentationDemo.java            # Data types, casting risks, and jagged arrays
├── Task03/
│   └── ParkingFacility.java                   # Slot allocation & release (Complete the Code)
├── Task04/
│   └── ConstructorDemo.java                   # Constructor chaining & memory model
├── Task05/
│   └── TicketLifecycleDemo.java               # Static members & GC object lifecycle
├── Task06/
│   └── VehicleHierarchyDemo.java              # Inheritance hierarchy & constructor trace
├── Task07/
│   └── VehicleOverrideDemo.java               # Method overriding & Object method contracts
├── Task08/
│   └── FeeDemo.java                           # Polymorphism & dynamic dispatch (Complete the Code)
├── Task09/
│   └── AbstractionDemo.java                   # Abstract classes & multi-interface capability
├── Task10/
│   └── src/parking/                           # Encapsulation via packages (model, service, app)
├── Task11/
│   └── SmartParkingSystem.java                # Complete integrated system & verification suite
└── Task12/
    └── AdaptedSmartParkingSystem.java         # Day-4 Design Challenge (E-Bikes, Vans, Passes, 5 Floors)
```

---

## How to Compile and Run Each Task

Ensure you have OpenJDK 21 installed and added to your system PATH. Execute the following commands in PowerShell from the project root (`D:\Data\Development\College\Java\Assessment-1`):

### Task 01: Problem Analysis & Object Modeling
```powershell
Set-Location -Path "Task01"
javac -encoding UTF-8 -Xlint:all Task01_ModelAnalysis.java
java Task01_ModelAnalysis
```

### Task 02: Data Representation & Casting Risks
```powershell
Set-Location -Path "Task02"
javac -encoding UTF-8 -Xlint:all DataRepresentationDemo.java
java DataRepresentationDemo
```

### Task 03: Control Logic (ParkingFacility Complete the Code)
```powershell
Set-Location -Path "Task03"
javac -encoding UTF-8 -Xlint:all ParkingFacility.java
java ParkingFacility
```

### Task 04: Classes, Objects & Constructors
```powershell
Set-Location -Path "Task04"
javac -encoding UTF-8 -Xlint:all ConstructorDemo.java
java ConstructorDemo
```

### Task 05: Static Members & Object Lifecycle
```powershell
Set-Location -Path "Task05"
javac -encoding UTF-8 -Xlint:all TicketLifecycleDemo.java
java TicketLifecycleDemo
```

### Task 06: Inheritance Hierarchy
```powershell
Set-Location -Path "Task06"
javac -encoding UTF-8 -Xlint:all VehicleHierarchyDemo.java
java VehicleHierarchyDemo
```

### Task 07: Method Overriding & Object Equality
```powershell
Set-Location -Path "Task07"
javac -encoding UTF-8 -Xlint:all VehicleOverrideDemo.java
java VehicleOverrideDemo
```

### Task 08: Polymorphism & Dynamic Dispatch (FeeDemo Complete the Code)
```powershell
Set-Location -Path "Task08"
javac -encoding UTF-8 -Xlint:all FeeDemo.java
java FeeDemo
```

### Task 09: Abstraction & Multiple Interfaces
```powershell
Set-Location -Path "Task09"
javac -encoding UTF-8 -Xlint:all AbstractionDemo.java
java AbstractionDemo
```

### Task 10: Encapsulation & Modular Packages
```powershell
Set-Location -Path "Task10"
if (Test-Path bin) { Remove-Item -Recurse -Force bin }
New-Item -ItemType Directory -Path bin -Force | Out-Null
javac -encoding UTF-8 -d bin (Get-ChildItem -Path src -Recurse -Filter "*.java" | ForEach-Object { $_.FullName })
java -cp bin parking.app.MainApp
```

### Task 11: Integrated System Verification Suite
```powershell
Set-Location -Path "Task11"
javac -encoding UTF-8 -Xlint:all SmartParkingSystem.java
java SmartParkingSystem
```

### Task 12: Day-4 Design Challenge (Adapted System)
```powershell
Set-Location -Path "Task12"
javac -encoding UTF-8 -Xlint:all AdaptedSmartParkingSystem.java
java AdaptedSmartParkingSystem
```

---

## Task Results Summary

| Task | Marks | Objective & Key Result | Verification Status |
| :--- | :---: | :--- | :---: |
| **Task 01** | 4 | Identified candidate classes (`Vehicle`, `ParkingSpot`, `ParkingTicket`, `ParkingFacility`). Validated single occupancy and double-booking rejection. Compared object models with parallel arrays. | **Passed (Exit Code 0)** |
| **Task 02** | 3 | Demonstrated integer truncation bug (`59 / 60 = 0` hrs $\rightarrow$ Rs 0.0) vs safe ceiling `(59 + 59) / 60 = 1` hr ($\rightarrow$ Rs 30.0). Validated jagged arrays for variable floor sizes. | **Passed (Exit Code 0)** |
| **Task 03** | 4 | Completed `ParkingFacility.java` `allocate()` and `release()` methods. Character-for-character matched expected output: `[0, 0]`, `[0, 1]`, `[1, 0]`, `null`, `true false false`, `[0, 1]`. | **Passed (Exit Code 0)** |
| **Task 04** | 4 | Designed constructors with `this(...)` chaining. Implemented copy constructor for re-issuing duplicate tickets without altering original entry timestamp. | **Passed (Exit Code 0)** |
| **Task 05** | 3 | Implemented static monotonically increasing ticket ID generator (`TKT-0001`...) and active facility occupancy tracking. Demonstrated GC eligibility upon checkout. | **Passed (Exit Code 0)** |
| **Task 06** | 4 | Built hierarchical and multilevel inheritance tree (`Vehicle` $\rightarrow$ `Car` $\rightarrow$ `ElectricCar`). Traced constructor order: `Object` $\rightarrow$ `Vehicle` $\rightarrow$ `Car` $\rightarrow$ `ElectricCar`. | **Passed (Exit Code 0)** |
| **Task 07** | 4 | Overrode `ratePerHour()`, `toString()`, `equals()`, and `hashCode()`. Validated license-plate equality and `HashSet` duplicate prevention. | **Passed (Exit Code 0)** |
| **Task 08** | 4 | Completed `FeeDemo.java` with ceiling integer arithmetic and dynamic method dispatch. Matched expected output: `TwoWheeler -> Rs 30.0`, `Car -> Rs 90.0`, `ElectricCar -> Rs 210.0`, `true`. | **Passed (Exit Code 0)** |
| **Task 09** | 4 | Introduced `Chargeable` and `Payable` interfaces alongside abstract `Vehicle`. Implemented default interface surcharge method and static duration validators. | **Passed (Exit Code 0)** |
| **Task 10** | 3 | Structured modular packages (`parking.model`, `parking.service`, `parking.app`). Validated defensive mutators against negative inputs and demonstrated Java's autoboxing Integer cache trap. | **Passed (Exit Code 0)** |
| **Task 11** | 5 | Built end-to-end integrated system. Tested nominal, boundary (last slot, zero-minute parking = Rs 0.0, slab boundaries 60m vs 61m), and invalid cases. Hand-calculated benchmark revenue of Rs 230.0 matched program output 100%. | **Passed (Exit Code 0)** |
| **Task 12** | 8 | Day-4 Design Challenge: Decoupled pricing using Strategy Pattern (`PricingStrategy`). Verified E-Bike flat fee (Rs 15.0), Delivery Van 9 AM–5 PM surge rate (Rs 120 vs Rs 60 off-peak), 24-hr One-Day Pass (Rs 100), and 5-floor non-uniform slot capacity. | **Passed (Exit Code 0)** |

---

## Git Commit History Summary

This repository follows atomic conventional commits tracking progressive implementation:

```
fd7ba04 chore: initialize repository and workspace directory structure
e98fff4 feat(task01): implement problem analysis and domain object models
1a98483 feat(task02): implement data representation and casting risk demo
473dd81 feat(task03): complete ParkingFacility allocation and release logic
11d5d5d feat(task04): implement classes, constructors and chaining
05be3ac feat(task05): apply static members and lifecycle tracking
eea801b feat(task06): design inheritance hierarchy and constructor trace
8ea0049 feat(task07): implement method overriding and Object methods
8669130 feat(task08): complete FeeDemo with dynamic method dispatch
ae80013 feat(task09): implement abstraction with Vehicle and Chargeable
b2f8356 feat(task10): enforce encapsulation with modular packages
3f54c61 feat(task11): implement complete Smart-Parking System integration
c055845 feat(task12): implement Day-4 adapted design challenge
58551c1 docs(diagrams): add UML class diagrams and architecture charts
1599b38 docs(report): compile comprehensive 50-mark academic report
```
