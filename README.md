# SIMATS Engineering, Saveetha School of Engineering
## Java Programming (CSA09 / CSA0921)
### Coursework Laboratory Portfolio

**Student Details:**
- **Name:** Bala Naga Jaswanth
- **Registration Number:** 192424300
- **College:** SIMATS Engineering, Saveetha School of Engineering
- **Java Platform:** OpenJDK 21 LTS / Java SE 26
- **GitHub Profile:** [https://github.com/Jaswanth300](https://github.com/Jaswanth300)
- **Primary Repository:** [https://github.com/Jaswanth300/CSA09-192424300-Assignment1](https://github.com/Jaswanth300/CSA09-192424300-Assignment1)

---

### Coursework Assignments Overview

This repository houses the complete, academically rigorous, and empirically validated laboratory submissions for **Java Programming** across Course Outcomes CO1, CO2, CO3, and CO4:

```
CSA09-192424300-Assignments/
├── TASKS/                                     # Assignment 1: Campus Smart-Parking System (Tasks 01–12)
├── diagrams/                                  # Assignment 1: Architecture, UML and class diagrams
├── report/                                    # Assignment 1: Academic Report (PDF & DOCX)
├── Assignment2/                               # Assignment 2: Strings & Collections (Q1–Q7)
│   ├── Q1/ to Q7/                             # Source code implementations
│   └── README.md                              # Compilation & execution instructions
├── Assignment3/                               # Assignment 3: Exceptions & Multithreading (Q1–Q7)
│   ├── Q1/ to Q7/                             # Source code implementations
│   └── README.md                              # Compilation & execution instructions
├── Assignment4/                               # Assignment 4: Event-Driven GUI Design (Tasks 01–06)
│   ├── Task01/ to Task06/                     # Source code implementations
│   └── README.md                              # Compilation & execution instructions
├── screenshots/                               # Authentic Eclipse IDE execution transcripts
│   ├── Assignment2/                           # Eclipse screenshots for Q1–Q7
│   ├── Assignment3/                           # Eclipse screenshots for Q1–Q7
│   └── Assignment4/                           # Eclipse screenshots for Tasks 01–06
├── SIMATS_Java_Assignment_2_Report.pdf        # Final Comprehensive Report (35 Pages)
├── SIMATS_Java_Assignment_2_Report.docx       # Editable Comprehensive Report
├── SIMATS_Java_Assignment_3_Report.pdf        # Final Comprehensive Report (31 Pages)
├── SIMATS_Java_Assignment_3_Report.docx       # Editable Comprehensive Report
├── SIMATS_Java_Assignment_4_Report.pdf        # Final Comprehensive Report (29 Pages)
├── SIMATS_Java_Assignment_4_Report.docx       # Editable Comprehensive Report
└── README.md                                  # Repository overview and guide
```

---

### Assignment Summaries

#### 1. Assignment 1 – CO1: Object-Oriented Campus Smart-Parking System
- **Focus:** Object-oriented modeling of a multi-level campus parking facility under OpenJDK 21 LTS, progressing through problem analysis, control logic, inheritance hierarchies, polymorphic billing, modular encapsulation, and Strategy Pattern design adaptation.
- **Artifacts:** `TASKS/`, `diagrams/`, `report/`

#### 2. Assignment 2 – CO2: Strings and the Java Collection Framework
- **Focus:** String/StringBuilder/StringBuffer benchmarking under load, ArrayList vs LinkedList operating boundaries, ArrayDeque double-ended queues, HashSet hashCode-equals identity contracts, Map trade-offs (HashMap, TreeMap, LinkedHashMap), generic ranking architectures (Comparable/Comparator), PriorityQueue triage ordering, and Day-4 Design Challenge (5-Bucket Multi-Level Queue).
- **Artifacts:** `Assignment2/` directory, `SIMATS_Java_Assignment_2_Report.pdf` (35 pages), `SIMATS_Java_Assignment_2_Report.docx`

#### 3. Assignment 3 – CO3: Exception Handling, Resource Management & Multithreading
- **Focus:** Robust ATM exception hierarchy, user-defined checked/unchecked exceptions and chaining, Try-With-Resources resource leak elimination, Thread lifecycle and scheduling experiments, Cinema seat-booking race conditions and atomic synchronization, Deadlock prevention via lock ordering and producer-consumer token coordination, and a concurrent fault-tolerant semester result processor.
- **Artifacts:** `Assignment3/` directory, `SIMATS_Java_Assignment_3_Report.pdf` (31 pages), `SIMATS_Java_Assignment_3_Report.docx`

#### 4. Assignment 4 – CO4: Event-Driven Graphical User Interface Design
- **Focus:** GUI design review and MVC refactoring, Outpatient appointment desk event models, Library search layout resizing with nested containers, event-handling forensics and repaint persistence, comparative event styles (Frame listener, named inner class, anonymous inner class, lambdas), and a full-featured Campus Event Registration Desktop Application with Swing & SQLite JDBC.
- **Artifacts:** `Assignment4/` directory, `SIMATS_Java_Assignment_4_Report.pdf` (29 pages), `SIMATS_Java_Assignment_4_Report.docx`

---

### Compilation and Execution Instructions

#### Assignment 2 (CO2):
```bash
cd Assignment2
javac -d bin Q1/TicketBenchmark.java Q2/PlaylistBenchmark.java Q3/Attendance.java Q4/PharmacyInventory.java Q5/LeaderboardDemo.java Q6/Triage.java Q7/DesignChallenge_BucketQueue.java
java -cp bin q1.TicketBenchmark
```

#### Assignment 3 (CO3):
```bash
cd Assignment3
javac -d bin Q1/ATMSimulation.java Q2/StorageDemo.java Q3/SalesReportGenerator.java Q4/ScannerStationSimulation.java Q5/SeatBooking.java Q6/DeadlockAndTokenSimulation.java Q7/SemesterResultProcessor.java
java -cp bin q1.ATMSimulation
```

#### Assignment 4 (CO4):
```bash
cd Assignment4
javac -d bin Task01/RefactoredFeeWindow.java Task02/OutpatientDesk.java Task03/LibrarySearchScreen.java Task04/DrawPad.java Task05/Converter.java Task06/CampusEventRegistrationApp.java
java -cp bin task01.RefactoredFeeWindow
```
