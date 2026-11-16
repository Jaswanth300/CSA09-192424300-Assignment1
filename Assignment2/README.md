# SIMATS School of Engineering and Technology
## Department of Computer Science & Engineering
### Course: Java Programming (CSA0921 / CSA09)
### Assignment 2 – CO2: Strings and the Java Collection Framework

**Student Details:**
- **Name:** Bala Naga Jaswanth
- **Register Number:** 192424300
- **Java Version:** OpenJDK 21 LTS / Java SE 26
- **GitHub Profile:** [https://github.com/Jaswanth300](https://github.com/Jaswanth300)
- **GitHub Repository:** [https://github.com/Jaswanth300/CSA09-192424300-Assignment1](https://github.com/Jaswanth300/CSA09-192424300-Assignment1)

---

### Folder Structure
```
Assignment2/
├── Q1/
│   └── TicketBenchmark.java
├── Q2/
│   └── PlaylistBenchmark.java
├── Q3/
│   └── Attendance.java
├── Q4/
│   └── PharmacyInventory.java
├── Q5/
│   └── LeaderboardDemo.java
├── Q6/
│   └── Triage.java
├── Q7/
│   └── DesignChallenge_BucketQueue.java
└── README.md
```

---

### Compilation and Execution Instructions

Compile all tasks:
```bash
javac -d bin Q1/TicketBenchmark.java Q2/PlaylistBenchmark.java Q3/Attendance.java Q4/PharmacyInventory.java Q5/LeaderboardDemo.java Q6/Triage.java Q7/DesignChallenge_BucketQueue.java
```

Execute individual tasks:
```bash
# Q1: Railway e-Ticket Generation Benchmark
java -cp bin q1.TicketBenchmark

# Q2: Music Playlist Editor & Up Next Queue
java -cp bin q2.PlaylistBenchmark

# Q3: Event Attendance De-duplication (hashCode-equals Contract)
java -cp bin q3.Attendance

# Q4: Pharmacy Stock Lookup & Reporting (HashMap vs TreeMap vs LinkedHashMap)
java -cp bin q4.PharmacyInventory

# Q5: Generic Leaderboard Ranking Component
java -cp bin q5.LeaderboardDemo

# Q6: Hospital Emergency Triage (PriorityQueue & Fairness)
java -cp bin q6.Triage

# Q7: Day-4 Design Challenge (Multi-Level Bucket Queue vs PriorityQueue)
java -cp bin q7.DesignChallenge_BucketQueue
```

---

### Task Summary & Verified Empirical Results

| Task | Topic | Key Finding / Result | Status |
| :--- | :--- | :--- | :--- |
| **Q1** | String vs StringBuilder vs StringBuffer | `StringBuilder` is 2.83x faster than `+` string concatenation due to $O(N)$ amortized buffer expansion and avoiding intermediate immutable String allocations. | Verified |
| **Q2** | ArrayList vs LinkedList vs ArrayDeque | `ArrayList` offers $O(1)$ random lookups (0.34 ms vs 523.6 ms in LinkedList). `ArrayDeque` delivers cache-friendly $O(1)$ performance for double-ended queues. | Verified |
| **Q3** | hashCode–equals Contract | Overriding `equals()` without `hashCode()` leads to duplicate entries in `HashSet`. Implementing both ensures strictly unique attendees (Size = 2). | Verified |
| **Q4** | Map Selection for Pharmacy Inventory | `LinkedHashMap` offers $O(1)$ lookup time and naturally preserves addition order for chronological reports without extra overhead. | Verified |
| **Q5** | Generic Ranking Component | Decoupled `Comparator` chain (points descending $\to$ goalDiff descending $\to$ name alphabetical) enforces strict transitivity; `Bisons` ranks before `Panthers`. | Verified |
| **Q6** | Hospital Emergency Triage | `PriorityQueue` correctly prioritizes critical severities (S1 to S5) while preserving arrival order via counter; direct heap traversal is not sorted. | Verified |
| **Q7** | Day-4 Design Challenge | Replaced $O(\log N)$ heap with 5-Bucket `ArrayDeque` array, achieving 4.91x speedup (22.69 ms vs 111.31 ms) and eliminating patient starvation via aging. | Verified |
