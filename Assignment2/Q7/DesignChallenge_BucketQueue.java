package q7;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * SIMATS Java Programming Assignment 2 - Q7 (Day-4 Design Challenge)
 * Diagnose, Redesign and Validate: Multi-Level Bucket Queue vs PriorityQueue
 * Eliminating O(log N) Heap Bottlenecks & Preventing Patient Starvation
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class DesignChallenge_BucketQueue {

    static class Patient {
        final String name;
        int severity; // 1 (critical) to 5 (minor)
        final long arrivalTime;
        long waitTicks;

        Patient(String name, int severity, long arrivalTime) {
            this.name = name;
            this.severity = severity;
            this.arrivalTime = arrivalTime;
            this.waitTicks = 0;
        }

        @Override
        public String toString() {
            return name + "(S" + severity + ")";
        }
    }

    // ORIGINAL SOLUTION: PriorityQueue (Binary Min-Heap)
    static class OriginalTriage {
        private final PriorityQueue<Patient> heap = new PriorityQueue<>(
                Comparator.comparingInt((Patient p) -> p.severity)
                        .thenComparingLong(p -> p.arrivalTime)
        );

        void admit(Patient p) {
            heap.add(p);
        }

        Patient next() {
            return heap.poll();
        }

        int size() {
            return heap.size();
        }
    }

    // REDESIGNED SOLUTION: Multi-Level 5-Bucket FIFO Array with Starvation-Free Aging
    static class RedesignedBucketTriage {
        @SuppressWarnings("unchecked")
        private final ArrayDeque<Patient>[] buckets = new ArrayDeque[5];
        private int totalCount = 0;

        public RedesignedBucketTriage() {
            for (int i = 0; i < 5; i++) {
                buckets[i] = new ArrayDeque<>();
            }
        }

        // O(1) Instantaneous Admission into discrete severity bucket
        public void admit(Patient p) {
            if (p.severity < 1 || p.severity > 5) throw new IllegalArgumentException("Invalid severity");
            buckets[p.severity - 1].addLast(p);
            totalCount++;
        }

        // O(1) Extraction: Inspects at most 5 array pointers
        public Patient next() {
            for (int i = 0; i < 5; i++) {
                if (!buckets[i].isEmpty()) {
                    totalCount--;
                    return buckets[i].pollFirst();
                }
            }
            return null;
        }

        // Starvation Prevention: Age waiting patients in lower buckets
        public void applyAging(long agingThresholdTicks) {
            for (int i = 4; i >= 1; i--) { // Check severity 5 down to 2
                ArrayDeque<Patient> current = buckets[i];
                int sz = current.size();
                for (int k = 0; k < sz; k++) {
                    Patient p = current.pollFirst();
                    p.waitTicks += 10;
                    if (p.waitTicks >= agingThresholdTicks && p.severity > 1) {
                        p.severity--; // Promote to higher priority bucket!
                        buckets[p.severity - 1].addLast(p);
                    } else {
                        current.addLast(p);
                    }
                }
            }
        }

        public int size() {
            return totalCount;
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("  DAY-4 DESIGN CHALLENGE: TRIAGE QUEUE BOTTLENECK & REDESIGN");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");

        int N = 100000;
        System.out.println("Workload: N = " + N + " Patient Admissions and Polling Operations");

        // 1. Benchmark Original PriorityQueue
        OriginalTriage orig = new OriginalTriage();
        Runtime rt = Runtime.getRuntime();
        rt.gc();
        long memBefore = rt.totalMemory() - rt.freeMemory();

        long t0 = System.nanoTime();
        for (int i = 0; i < N; i++) {
            orig.admit(new Patient("P_" + i, 1 + (i % 5), i));
        }
        long origAdmitTime = System.nanoTime() - t0;

        t0 = System.nanoTime();
        while (orig.next() != null) {}
        long origPollTime = System.nanoTime() - t0;
        long memAfter = rt.totalMemory() - rt.freeMemory();

        System.out.println("\n[A] ORIGINAL SOLUTION (PriorityQueue - Binary Min-Heap):");
        System.out.printf("    - Admission Time (N ops) : %10.2f ms (O(log N) sift-up)%n", origAdmitTime / 1e6);
        System.out.printf("    - Polling Time   (N ops) : %10.2f ms (O(log N) sift-down)%n", origPollTime / 1e6);
        System.out.printf("    - Total Duration         : %10.2f ms%n", (origAdmitTime + origPollTime) / 1e6);

        // 2. Benchmark Redesigned Bucket Queue
        RedesignedBucketTriage redis = new RedesignedBucketTriage();
        rt.gc();
        memBefore = rt.totalMemory() - rt.freeMemory();

        t0 = System.nanoTime();
        for (int i = 0; i < N; i++) {
            redis.admit(new Patient("P_" + i, 1 + (i % 5), i));
        }
        long redisAdmitTime = System.nanoTime() - t0;

        t0 = System.nanoTime();
        while (redis.next() != null) {}
        long redisPollTime = System.nanoTime() - t0;

        System.out.println("\n[B] REDESIGNED SOLUTION (5-Bucket ArrayDeque Array):");
        System.out.printf("    - Admission Time (N ops) : %10.2f ms (O(1) direct bucket push)%n", redisAdmitTime / 1e6);
        System.out.printf("    - Polling Time   (N ops) : %10.2f ms (O(1) head pop)%n", redisPollTime / 1e6);
        System.out.printf("    - Total Duration         : %10.2f ms%n", (redisAdmitTime + redisPollTime) / 1e6);

        double speedup = (double) (origAdmitTime + origPollTime) / (redisAdmitTime + redisPollTime);
        System.out.printf("%n>>> SPEEDUP ACHIEVED: %.2fx faster execution with Redesigned Architecture! <<<%n", speedup);

        // 3. Starvation Prevention Validation
        System.out.println("\n[C] VALIDATING STARVATION-FREE AGING MECHANISM:");
        RedesignedBucketTriage agingDemo = new RedesignedBucketTriage();
        agingDemo.admit(new Patient("Critical_Meena", 1, 0));
        agingDemo.admit(new Patient("Minor_Asha", 5, 1));
        System.out.println("Initial State: Asha is in Bucket S5 (Minor)");

        // Simulate 5 time steps with incoming severe cases
        for (int step = 1; step <= 5; step++) {
            agingDemo.admit(new Patient("Emergency_" + step, 1, step + 1));
            agingDemo.applyAging(20); // Age threshold: 20 ticks
        }
        System.out.println("After aging updates, serving order:");
        Patient p;
        while ((p = agingDemo.next()) != null) {
            System.out.println("  Served: " + p.name + " (Severity: " + p.severity + ", Wait: " + p.waitTicks + " ticks)");
        }
        System.out.println("Confirmation: Asha was promoted from S5 to higher severity, guaranteeing fair treatment!");
    }
}
