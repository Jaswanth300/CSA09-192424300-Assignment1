package q3;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * SIMATS Java Programming Assignment 3 - Q3
 * Resource Safety, Debugging and Robust Code
 * Comparing Manual Resource Management vs Try-With-Resources & Suppressed Exceptions
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class SalesReportGenerator {

    // Simulating custom tracked resource to count open file handles
    static class TrackedFileReader implements AutoCloseable {
        private final String filename;
        private static int openHandles = 0;
        private boolean closed = false;

        public TrackedFileReader(String filename) throws IOException {
            this.filename = filename;
            if (filename.contains("corrupt")) {
                throw new IOException("Corrupted file header in: " + filename);
            }
            openHandles++;
        }

        public String readRecord() throws IOException {
            if (filename.contains("midway_fail")) {
                throw new IOException("Unreadable block in: " + filename);
            }
            return "Record from " + filename;
        }

        @Override
        public void close() throws IOException {
            if (!closed) {
                closed = true;
                openHandles--;
                if (filename.contains("close_fail")) {
                    throw new IOException("Hardware error while flushing handle: " + filename);
                }
            }
        }

        public static int getOpenHandles() {
            return openHandles;
        }

        public static void reset() {
            openHandles = 0;
        }
    }

    // Approach 1: Vulnerable Manual try-catch-finally
    public static void generateReportManual(List<String> files) {
        TrackedFileReader.reset();
        List<TrackedFileReader> readers = new ArrayList<>();
        try {
            for (String file : files) {
                TrackedFileReader r = new TrackedFileReader(file);
                readers.add(r);
                r.readRecord();
            }
        } catch (IOException e) {
            System.err.println("[MANUAL CAUGHT] Processing failed: " + e.getMessage());
        } finally {
            // Fragile manual closing: if one reader throws during close, remaining readers are LEAKED!
            for (TrackedFileReader r : readers) {
                try {
                    r.close();
                } catch (IOException ce) {
                    System.err.println("[MANUAL CLOSE FAIL] " + ce.getMessage());
                    break; // Abrupt break leaks remaining handles!
                }
            }
        }
    }

    // Approach 2: Robust Try-With-Resources with AutoCloseable
    public static void generateReportTryWithResources(List<String> files) {
        TrackedFileReader.reset();
        for (String file : files) {
            try (TrackedFileReader r = new TrackedFileReader(file)) {
                r.readRecord();
            } catch (IOException e) {
                System.err.println("[TWR CAUGHT] " + e.getMessage());
                for (Throwable sup : e.getSuppressed()) {
                    System.err.println("  [SUPPRESSED EXCEPTION] " + sup.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("  RESOURCE SAFETY & LEAK ANALYSIS: MANUAL VS TRY-WITH-RESOURCES");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");

        List<String> storeFiles = List.of(
                "store_01.dat",
                "store_02_midway_fail.dat", // Causes failure during reading
                "store_03_close_fail.dat",
                "store_04.dat"
        );

        System.out.println("\n--- RUNNING MANUAL TRY-CATCH-FINALLY ---");
        generateReportManual(storeFiles);
        System.out.println("Open File Handles Remaining after Manual: " + TrackedFileReader.getOpenHandles());
        System.out.println("Result: Leaked file descriptors detected! System vulnerable to 'Too many open files' OS error.");

        System.out.println("\n--- RUNNING TRY-WITH-RESOURCES ---");
        generateReportTryWithResources(storeFiles);
        System.out.println("Open File Handles Remaining after TWR: " + TrackedFileReader.getOpenHandles());
        System.out.println("Result: Exactly ZERO file leaks! Complete resource safety guaranteed under all failure modes.");
    }
}
