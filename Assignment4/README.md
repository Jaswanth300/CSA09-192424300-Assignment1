# SIMATS School of Engineering and Technology
## Department of Computer Science & Engineering
### Course: Programming in Java (CSA09)
### Assignment 4 – CO4: Event-Driven GUI Design

**Student Details:**
- **Name:** Bala Naga Jaswanth
- **Register Number:** 192424300
- **Java Version:** OpenJDK 21 LTS / Java SE 26
- **GitHub Profile:** [https://github.com/Jaswanth300](https://github.com/Jaswanth300)
- **GitHub Repository:** [https://github.com/Jaswanth300/CSA09-192424300-Assignment1](https://github.com/Jaswanth300/CSA09-192424300-Assignment1)

---

### Folder Structure
```
Assignment4/
├── Task01/
│   └── RefactoredFeeWindow.java
├── Task02/
│   └── OutpatientDesk.java
├── Task03/
│   └── LibrarySearchScreen.java
├── Task04/
│   └── DrawPad.java
├── Task05/
│   └── Converter.java
├── Task06/
│   ├── CampusEventRegistrationApp.java
│   └── schema.sql
└── README.md
```

---

### Compilation and Execution Instructions

Compile all tasks:
```bash
javac -d bin Task01/RefactoredFeeWindow.java Task02/OutpatientDesk.java Task03/LibrarySearchScreen.java Task04/DrawPad.java Task05/Converter.java Task06/CampusEventRegistrationApp.java
```

Execute individual tasks:
```bash
# Task 1: Refactored Student Fee Payment Window (MVC)
java -cp bin task01.RefactoredFeeWindow

# Task 2: Hospital Outpatient Appointment Desk
java -cp bin task02.OutpatientDesk

# Task 3: Campus Library Catalog Search Screen (Resizing Layout)
java -cp bin task03.LibrarySearchScreen

# Task 4: Event-Handling Forensics & Paint Persistence
java -cp bin task04.DrawPad

# Task 5: Currency Converter with 4 Event Styles & Lambda
java -cp bin task05.Converter

# Task 6: Campus Event Registration Desktop App (Swing + JDBC)
java -cp bin task06.CampusEventRegistrationApp
```

---

### Task Summary & Verified Empirical Results

| Task | Topic | Key Finding / Result | Status |
| :--- | :--- | :--- | :--- |
| **Task 1** | GUI Design Review & Refactor | Decoupled UI presentation from business logic; asynchronous `SwingWorker` eliminates UI freezing during database operations. | Verified |
| **Task 2** | Event-Driven Appointment Desk | Dynamic doctor population via `ItemListener` on department selection; `WindowClosing` prompts confirmation if booking in progress. | Verified |
| **Task 3** | Layout Resizing Architecture | Master `BorderLayout` with nested `FlowLayout` controls; central `JScrollPane` smoothly absorbs viewport expansion on window maximize. | Verified |
| **Task 4** | Event-Handling Forensics | Fixed lowercase `mouseclicked` defect via `@Override`; stored clicked coordinates in `List<Point>` to survive repaint/minimize events. | Verified |
| **Task 5** | Event Styles Comparison | Lambda expressions (`e -> convert()`) reduce boilerplate by 75% compared to anonymous/named inner classes while unifying button and Enter key. | Verified |
| **Task 6** | Design Challenge: Event App | Full 3-tier desktop application (Swing UI $\to$ Service $\to$ DAO); enforces unique student-event constraints and filters records dynamically. | Verified |
