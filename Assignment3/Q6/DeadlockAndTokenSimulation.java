package q6;

/**
 * SIMATS Java Programming Assignment 3 - Q6
 * Deadlock and Inter-Thread Communication
 * Part A: Hostel Wallet Lock Ordering | Part B: Mess Token Counter (Producer-Consumer)
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
class TokenCounter {
    private final int[] buffer = new int[5]; // the counter holds at most 5 tokens
    private int count = 0, in = 0, out = 0;

    public synchronized void put(int token) throws InterruptedException {
        // TODO 1: Wait while the counter is full, add the token at position in, then wake waiting threads
        while (count == buffer.length) {
            wait();
        }
        buffer[in] = token;
        in = (in + 1) % buffer.length;
        count++;
        notifyAll();
    }

    public synchronized int take() throws InterruptedException {
        // TODO 2: Wait while the counter is empty, remove the token at position out, wake waiting threads and return the token
        while (count == 0) {
            wait();
        }
        int token = buffer[out];
        out = (out + 1) % buffer.length;
        count--;
        notifyAll();
        return token;
    }
}

// Part A: Hostel Wallet Account demonstrating Deadlock Prevention
class Account {
    final int id;
    int balance;

    Account(int id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    // Deadlock-free transfer using global lock ordering by Account ID
    public static void safeTransfer(Account from, Account to, int amount) {
        Account firstLock = from.id < to.id ? from : to;
        Account secondLock = from.id < to.id ? to : from;

        synchronized (firstLock) {
            synchronized (secondLock) {
                from.balance -= amount;
                to.balance += amount;
            }
        }
    }
}

public class DeadlockAndTokenSimulation {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("==================================================================");
        System.out.println("  DEADLOCK PREVENTION & INTER-THREAD COMMUNICATION (PRODUCER-CONSUMER)");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");

        // OFFICIAL COMPLETE THE CODE RUN (PART B)
        System.out.println("\n--- PART B: MESS TOKEN COUNTER (CAPACITY = 5) ---");
        TokenCounter counter = new TokenCounter();
        int[] collected = new int[2];
        Thread cook = new Thread(() -> {
            try {
                for (int t = 1; t <= 20; t++) counter.put(t);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        Thread[] students = new Thread[2];
        for (int s = 0; s < 2; s++) {
            final int id = s;
            students[s] = new Thread(() -> {
                try {
                    for (int k = 0; k < 10; k++) collected[id] += counter.take();
                } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            });
        }
        cook.start(); for (Thread t : students) t.start();
        cook.join(); for (Thread t : students) t.join();

        System.out.println("Sum of tokens collected: " + (collected[0] + collected[1])
                + " (expected 210)");

        // PART A: DEADLOCK PREVENTION VERIFICATION
        System.out.println("\n--- PART A: HOSTEL WALLET TRANSFER DEADLOCK PREVENTION ---");
        Account accA = new Account(101, 1000);
        Account accB = new Account(102, 1000);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) Account.safeTransfer(accA, accB, 1);
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) Account.safeTransfer(accB, accA, 1);
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.printf("Transfers completed successfully without deadlock! Acc 101: %d, Acc 102: %d%n",
                accA.balance, accB.balance);
    }
}
