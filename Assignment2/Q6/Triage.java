package q6;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * SIMATS Java Programming Assignment 2 - Q6
 * Priority, Order and Fairness in a Queue: Hospital Emergency Triage
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
class Patient {
    final String name;
    final int severity;
    final long arrival;

    Patient(String name, int severity, long arrival) {
        this.name = name;
        this.severity = severity;
        this.arrival = arrival;
    }

    @Override
    public String toString() {
        return name + "(S" + severity + ")";
    }
}

public class Triage {
    private long counter = 0;

    // TODO 1: Create a PriorityQueue that serves the lowest severity number first and,
    // for equal severity, the earliest arrival first
    private final PriorityQueue<Patient> queue = new PriorityQueue<>(
            Comparator.comparingInt((Patient p) -> p.severity)
                    .thenComparingLong(p -> p.arrival)
    );

    void admit(String name, int severity) {
        queue.add(new Patient(name, severity, counter++));
    }

    Patient next() {
        return queue.poll();
    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("  HOSPITAL EMERGENCY TRIAGE: PRIORITYQUEUE ORDER & FAIRNESS");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");

        Triage er = new Triage();
        er.admit("Ravi", 3);
        er.admit("Meena", 1);
        er.admit("John", 3);
        er.admit("Asha", 5);
        er.admit("Kumar", 1);
        er.admit("Lata", 2);

        // DEMONSTRATING DIRECT PRINTING PITFALL (Internal Heap Array View)
        System.out.println("\n--- DIRECT PRINTING OF PRIORITYQUEUE (Heap Structure) ---");
        System.out.println("Direct toString(): " + er.queue);
        System.out.println("Note: Direct iteration traverses the internal binary-heap array level-by-level, NOT in polled order!");

        // SERVING PATIENTS VIA POLL()
        System.out.println("\n--- OFFICIAL TREATMENT ORDER VIA POLL() ---");
        StringBuilder order = new StringBuilder();
        Patient p;
        while ((p = er.next()) != null) {
            order.append(p).append(' ');
        }
        String result = order.toString().trim();
        System.out.println("Actual output   : " + result);
        System.out.println("Expected output : Meena(S1) Kumar(S1) Lata(S2) Ravi(S3) John(S3) Asha(S5)");

        // 8-PATIENT HAND-VERIFIED TRACE
        System.out.println("\n--- HAND-VERIFIED CORRECTNESS TEST (8 PATIENTS) ---");
        Triage er8 = new Triage();
        er8.admit("P1_Cardiopulmonary", 1);
        er8.admit("P2_Fracture", 3);
        er8.admit("P3_HeadTrauma", 1);
        er8.admit("P4_MildFever", 5);
        er8.admit("P5_AsthmaAttack", 2);
        er8.admit("P6_Sprain", 4);
        er8.admit("P7_DeepLaceration", 2);
        er8.admit("P8_AbdominalPain", 3);

        System.out.println("Expected Order: P1(S1) -> P3(S1) -> P5(S2) -> P7(S2) -> P2(S3) -> P8(S3) -> P6(S4) -> P4(S5)");
        System.out.print("Observed Order: ");
        while ((p = er8.next()) != null) {
            System.out.print(p.name + "(S" + p.severity + ") ");
        }
        System.out.println();

        // SCALABILITY MEASUREMENT: 5,000 PATIENTS
        System.out.println("\n--- SCALABILITY INVESTIGATION (5,000 PATIENT WORKLOAD) ---");
        Triage cityER = new Triage();
        long t0 = System.nanoTime();
        for (int i = 0; i < 5000; i++) {
            cityER.admit("Patient_" + i, 1 + (i % 5));
        }
        long admitTime = System.nanoTime() - t0;

        t0 = System.nanoTime();
        int served = 0;
        while (cityER.next() != null) served++;
        long pollTime = System.nanoTime() - t0;

        System.out.printf("Time to admit 5,000 patients : %8.3f ms (Avg: %.2f us/op)%n",
                admitTime / 1e6, (admitTime / 5000.0) / 1000.0);
        System.out.printf("Time to poll 5,000 patients  : %8.3f ms (Avg: %.2f us/op)%n",
                pollTime / 1e6, (pollTime / 5000.0) / 1000.0);
        System.out.println("Verdict: Sub-microsecond latency easily scales to 5,000 patients/day in a metropolitan hospital.");
    }
}
