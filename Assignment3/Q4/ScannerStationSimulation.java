package q4;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * SIMATS Java Programming Assignment 3 - Q4
 * Thread Creation and the Thread Life Cycle: Parallel Answer-Script Scanning
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class ScannerStationSimulation {

    // Approach 1: Implementing Runnable
    static class ScannerTask implements Runnable {
        private final String stationName;
        private final int batchCount;
        private final AtomicInteger grandTotal;

        public ScannerTask(String stationName, int batchCount, AtomicInteger grandTotal) {
            this.stationName = stationName;
            this.batchCount = batchCount;
            this.grandTotal = grandTotal;
        }

        @Override
        public void run() {
            System.out.printf("[%s] Started scanning %d scripts on Thread: %s%n",
                    stationName, batchCount, Thread.currentThread().getName());
            try {
                // Simulate physical scanning latency
                Thread.sleep(50 + (long)(Math.random() * 50));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            grandTotal.addAndGet(batchCount);
            System.out.printf("[%s] Finished scanning. State: %s%n",
                    stationName, Thread.currentThread().getState());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("==================================================================");
        System.out.println("  PARALLEL SCANNER STATIONS: THREAD LIFE CYCLE & SCHEDULING");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");

        AtomicInteger totalScanned = new AtomicInteger(0);
        int numStations = 4;
        Thread[] threads = new Thread[numStations];

        System.out.println("\n--- CREATING & STARTING SCANNER WORKER THREADS ---");
        for (int i = 0; i < numStations; i++) {
            String name = "Station-" + (i + 1);
            threads[i] = new Thread(new ScannerTask(name, 250, totalScanned), name);
            System.out.printf("Created %s | State before start(): %s%n", name, threads[i].getState());
        }

        for (Thread t : threads) {
            t.start();
        }

        // Check isAlive() while running
        Thread.sleep(10);
        System.out.println("\n--- RUNTIME STATE CHECK ---");
        for (Thread t : threads) {
            System.out.printf("Thread %s | isAlive: %b | State: %s%n", t.getName(), t.isAlive(), t.getState());
        }

        // Wait for all stations to complete using join()
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("\n--- POST-JOIN VERIFICATION ---");
        System.out.println("All stations joined. Total answer scripts scanned: " + totalScanned.get() + " (Expected: 1000)");

        // DEMONSTRATING RUN() VS START() PITFALL
        System.out.println("\n--- DEMONSTRATING RUN() VS START() PITFALL ---");
        Thread badThread = new Thread(() -> {
            System.out.println("Executing in Thread: " + Thread.currentThread().getName());
        }, "DedicatedWorkerThread");

        System.out.println("Invoking badThread.run() directly:");
        badThread.run(); // Synchronous call on main thread!
        System.out.println("Notice: Thread name was 'main', not 'DedicatedWorkerThread'! No new OS thread was spawned.");

        System.out.println("\nInvoking badThread.start():");
        badThread.start();
        badThread.join();
    }
}
