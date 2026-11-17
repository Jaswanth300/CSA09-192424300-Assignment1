# SIMATS Engineering, Saveetha School of Engineering
## Java Programming (CSA09)
### Assignment 3 – CO3: Exception Handling and Multithreading

**Student Details:**
- **Student A (Lead):** Bala Naga Jaswanth (Reg: 192424300)
- **Student B (Collaborator):** Peer Contributor / Pair Partner
- **College:** SIMATS Engineering, Saveetha School of Engineering
- **Java Version:** OpenJDK 21 LTS / Java SE 26
- **GitHub Profile:** [https://github.com/Jaswanth300](https://github.com/Jaswanth300)
- **Repository:** [https://github.com/Jaswanth300/JAVA-Assignment](https://github.com/Jaswanth300/JAVA-Assignment)

---

### Folder Structure
```
Assignment3/
├── Q1/
│   └── ATMSimulation.java
├── Q2/
│   └── StorageDemo.java
├── Q3/
│   └── SalesReportGenerator.java
├── Q4/
│   └── ScannerStationSimulation.java
├── Q5/
│   └── SeatBooking.java
├── Q6/
│   └── DeadlockAndTokenSimulation.java
├── Q7/
│   └── SemesterResultProcessor.java
└── README.md
```

---

### Compilation and Execution Instructions

Compile all tasks:
```bash
javac -d bin Q1/ATMSimulation.java Q2/StorageDemo.java Q3/SalesReportGenerator.java Q4/ScannerStationSimulation.java Q5/SeatBooking.java Q6/DeadlockAndTokenSimulation.java Q7/SemesterResultProcessor.java
```

Execute individual tasks:
```bash
# Q1: Bank ATM Simulation (Exception Hierarchy & Multi-catch)
java -cp bin q1.ATMSimulation

# Q2: Online Exam Portal (User-Defined Exceptions & Chaining)
java -cp bin q2.StorageDemo

# Q3: Daily Sales Report (Resource Safety & Try-With-Resources)
java -cp bin q3.SalesReportGenerator

# Q4: Parallel Answer-Script Scanning (Thread Creation & Lifecycle)
java -cp bin q4.ScannerStationSimulation

# Q5: Cinema Seat Booking (Race Conditions & Atomic Synchronization)
java -cp bin q5.SeatBooking

# Q6: Deadlock Prevention & Mess Token Producer-Consumer
java -cp bin q6.DeadlockAndTokenSimulation

# Q7: Semester Result Processor (ExecutorService & Fault Tolerance)
java -cp bin q7.SemesterResultProcessor
```

---

### Task Summary & Verified Empirical Results

| Task | Topic | Key Finding / Result | Status |
| :--- | :--- | :--- | :--- |
| **Q1** | Bank ATM Simulation | Hierarchy-aware catch blocks prevent bug masking; `finally` guarantees hardware lock release and audit logging regardless of failure path. | Verified |
| **Q2** | Exam Portal Chaining | Wrapping low-level `IOException` into high-level `SubmissionStorageException` preserves underlying root cause via `getCause()`. | Verified |
| **Q3** | Try-With-Resources Safety | Manual resource closing leaks descriptors upon midway exceptions; `try-with-resources` guarantees automatic closing and tracks suppressed exceptions. | Verified |
| **Q4** | Thread Creation & Lifecycle | `start()` spawns native OS threads whereas `run()` executes synchronously on caller thread. `join()` guarantees deterministic final tallying. | Verified |
| **Q5** | Cinema Seat Booking | Unsynchronized check-then-book causes severe overselling (selling 12–25 seats for 10 slots); atomic `synchronized` method guarantees exactly 10 sold. | Verified |
| **Q6** | Deadlock & Inter-Thread Comm | Global lock ordering by Account ID eliminates circular wait; `TokenCounter` producer-consumer with `wait()`/`notifyAll()` guarantees exact token sum of 210. | Verified |
| **Q7** | Concurrent Fault-Tolerant Processor | `ExecutorService` (4 threads) finishes 10,000 files in 25.40 ms; corrupt/missing files are logged without halting the batch run. | Verified |
