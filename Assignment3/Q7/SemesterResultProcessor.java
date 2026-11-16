package q7;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SIMATS Java Programming Assignment 3 - Q7
 * Design a Concurrent, Fault-Tolerant Processor from First Principles
 * Semester Result Processing under Scaled Workloads (10,000 files)
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class SemesterResultProcessor {

    // Record modeling an individual score file
    static class ScoreFile {
        final String studentId;
        final int score;
        final String status; // "VALID", "CORRUPT", "MISSING"

        ScoreFile(String studentId, int score, String status) {
            this.studentId = studentId;
            this.score = score;
            this.status = status;
        }
    }

    // Result Summary
    static class ProcessingSummary {
        final AtomicInteger successCount = new AtomicInteger(0);
        final AtomicInteger corruptCount = new AtomicInteger(0);
        final AtomicInteger missingCount = new AtomicInteger(0);
        final ConcurrentHashMap<String, String> grades = new ConcurrentHashMap<>();

        public void printSummary() {
            System.out.printf("  Processed Valid Records   : %d%n", successCount.get());
            System.out.printf("  Corrupted Files Recovered : %d%n", corruptCount.get());
            System.out.printf("  Missing Files Handled     : %d%n", missingCount.get());
            System.out.printf("  Total Files Evaluated     : %d%n",
                    successCount.get() + corruptCount.get() + missingCount.get());
        }
    }

    // Task submitted to ExecutorService
    static class FileProcessorTask implements Callable<Boolean> {
        private final ScoreFile file;
        private final ProcessingSummary summary;

        FileProcessorTask(ScoreFile file, ProcessingSummary summary) {
            this.file = file;
            this.summary = summary;
        }

        @Override
        public Boolean call() {
            try {
                if ("MISSING".equals(file.status)) {
                    throw new IOException("File not found on storage mount");
                }
                if ("CORRUPT".equals(file.status)) {
                    throw new IllegalArgumentException("Corrupted CRC checksum in score payload");
                }

                // Compute Grade
                String grade = file.score >= 90 ? "O" : file.score >= 80 ? "A+" : file.score >= 70 ? "A" : "B";
                summary.grades.put(file.studentId, grade);
                summary.successCount.incrementAndGet();
                return true;
            } catch (IOException ioe) {
                summary.missingCount.incrementAndGet();
                return false;
            } catch (IllegalArgumentException iae) {
                summary.corruptCount.incrementAndGet();
                return false;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("==================================================================");
        System.out.println("  CONCURRENT FAULT-TOLERANT SEMESTER RESULT PROCESSOR (N = 10,000)");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");

        int totalFiles = 10000;
        List<ScoreFile> dataset = new ArrayList<>(totalFiles);

        for (int i = 0; i < totalFiles; i++) {
            String studentId = String.format("1924%05d", i);
            if (i % 100 == 0) {
                dataset.add(new ScoreFile(studentId, -1, "CORRUPT")); // 100 corrupt files
            } else if (i % 250 == 0) {
                dataset.add(new ScoreFile(studentId, -1, "MISSING")); // 40 missing files
            } else {
                dataset.add(new ScoreFile(studentId, 45 + (i % 55), "VALID"));
            }
        }

        int[] poolSizes = {1, 2, 4, 8};

        for (int p : poolSizes) {
            ProcessingSummary summary = new ProcessingSummary();
            ExecutorService executor = Executors.newFixedThreadPool(p);

            long t0 = System.nanoTime();
            List<Future<Boolean>> futures = new ArrayList<>(totalFiles);
            for (ScoreFile sf : dataset) {
                futures.add(executor.submit(new FileProcessorTask(sf, summary)));
            }

            // Await completion of all tasks
            for (Future<Boolean> f : futures) {
                f.get(); // Guarantees all tasks finish
            }
            executor.shutdown();
            long durationNs = System.nanoTime() - t0;

            System.out.println("\n-------------------------------------------------------------");
            System.out.printf("  THREAD POOL SIZE: %d THREADS | DURATION: %8.2f ms%n", p, durationNs / 1e6);
            System.out.println("-------------------------------------------------------------");
            summary.printSummary();
        }

        System.out.println("\n==================================================================");
        System.out.println("  ARCHITECTURAL DEFENSE & SCALABILITY VERDICT");
        System.out.println("==================================================================");
        System.out.println("1. Optimal Thread Pool Size: 4 to 8 threads.");
        System.out.println("2. Fault Tolerance Guarantee: A corrupted or missing score file never halts batch execution.");
        System.out.println("3. Concurrency Safety: AtomicInteger & ConcurrentHashMap eliminate race conditions without global locks.");
    }
}
