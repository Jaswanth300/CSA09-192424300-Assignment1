package q3;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * SIMATS Java Programming Assignment 2 - Q3
 * Engineer the hashCode-equals Contract
 * Student Attendance De-duplication Investigation
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
class Student {
    private String regNo;
    private final String name;

    Student(String regNo, String name) {
        this.regNo = regNo;
        this.name = name;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getRegNo() {
        return regNo;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(regNo, student.regNo);
    }

    // TODO 1: Override hashCode() so that it is consistent with equals()
    @Override
    public int hashCode() {
        return Objects.hash(regNo);
    }

    @Override
    public String toString() {
        return regNo + " " + name;
    }
}

// Faulty Student class demonstrating the defect when hashCode() is NOT overridden
class FaultyStudent {
    private final String regNo;
    private final String name;

    FaultyStudent(String regNo, String name) {
        this.regNo = regNo;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FaultyStudent)) return false;
        return Objects.equals(regNo, ((FaultyStudent) o).regNo);
    }

    // hashCode() is intentionally NOT overridden -> inherits System identityHashCode
    @Override
    public String toString() {
        return regNo + " " + name;
    }
}

public class Attendance {
    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("  EVENT ATTENDANCE DE-DUPLICATION: HASHCODE-EQUALS EXPERIMENTS");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");

        // EXPERIMENT 1: HashSet appears to work correctly (Identical object reference)
        System.out.println("\n--- TEST 1: Apparent Success (Re-scanning exact same Object reference) ---");
        Set<FaultyStudent> set1 = new HashSet<>();
        FaultyStudent s1 = new FaultyStudent("192311001", "Arun");
        FaultyStudent s2 = new FaultyStudent("192311002", "Divya");
        set1.add(s1);
        set1.add(s2);
        set1.add(s1); // Same reference added again
        System.out.println("Predicted Size : 2");
        System.out.println("Observed Size  : " + set1.size() + " -> Elements: " + set1);
        System.out.println("Explanation    : Works by accident because identityHashCode matches for the same reference.");

        // EXPERIMENT 2: Exposing duplicate entries (Distinct instances with identical regNo)
        System.out.println("\n--- TEST 2: Duplicate Leak (Distinct instances, identical regNo, no hashCode) ---");
        Set<FaultyStudent> set2 = new HashSet<>();
        set2.add(new FaultyStudent("192311001", "Arun")); // Gate 1
        set2.add(new FaultyStudent("192311002", "Divya")); // Gate 2
        set2.add(new FaultyStudent("192311001", "Arun")); // Gate 3 (Duplicate scan)
        System.out.println("Predicted Size : 3 (Duplicates allowed)");
        System.out.println("Observed Size  : " + set2.size() + " -> Elements: " + set2);
        System.out.println("Explanation    : Different heap allocations yield distinct identity hashCodes, landing in different buckets. equals() is never invoked!");

        // EXPERIMENT 3: Mutating a field after insertion (Hash collision & lost object)
        System.out.println("\n--- TEST 3: Mutating key field after insertion into HashSet ---");
        Set<Student> set3 = new HashSet<>();
        Student sMutable = new Student("192311005", "Kiran");
        set3.add(sMutable);
        System.out.println("Inserted: " + sMutable + " | Contains before mutation: " + set3.contains(sMutable));
        sMutable.setRegNo("192311099"); // Mutate field used in hashCode()
        System.out.println("Mutated regNo to 192311099 | Contains after mutation: " + set3.contains(sMutable));
        System.out.println("Set Size: " + set3.size() + " | But contains() returns FALSE! Object is trapped/lost in old bucket.");

        // COMPLETE THE CODE OFFICIAL TEST
        System.out.println("\n--- COMPLETE THE CODE EXECUTION (With TODO 1 Implemented) ---");
        Set<Student> present = new HashSet<>();
        present.add(new Student("192311001", "Arun"));    // scanned at Gate 1
        present.add(new Student("192311002", "Divya"));   // scanned at Gate 2
        present.add(new Student("192311001", "Arun"));    // scanned again at Gate 3
        System.out.println("Unique students: " + present.size());
        System.out.println("Expected output: Unique students: 2");

        // ALTERNATIVE CONCEPT: TreeSet with Comparator
        System.out.println("\n--- ALTERNATIVE: TreeSet<Student> with Comparator on regNo ---");
        Set<Student> treeSet = new TreeSet<>((a, b) -> a.getRegNo().compareTo(b.getRegNo()));
        treeSet.add(new Student("192311001", "Arun"));
        treeSet.add(new Student("192311002", "Divya"));
        treeSet.add(new Student("192311001", "Arun"));
        System.out.println("TreeSet unique students: " + treeSet.size() + " -> " + treeSet);
    }
}
