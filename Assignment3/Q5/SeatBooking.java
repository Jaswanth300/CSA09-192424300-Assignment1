package q5;

/**
 * SIMATS Java Programming Assignment 3 - Q5
 * Race Conditions in a Cinema Seat-Booking System
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class SeatBooking {
    private final boolean[] booked;
    private int bookingsMade = 0;

    public SeatBooking(int seats) {
        booked = new boolean[seats];
    }

    // TODO 1: Make the check-then-book operation atomic so that a seat can never be sold twice
    public synchronized boolean book(int seat) {
        if (!booked[seat]) {
            // Artificial delay to expose race condition when unsynchronized
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {}
            booked[seat] = true;
            bookingsMade++;
            return true;
        }
        return false;
    }

    // Unsynchronized book method for comparative analysis
    public boolean bookUnsynchronized(int seat) {
        if (!booked[seat]) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {}
            booked[seat] = true;
            bookingsMade++;
            return true;
        }
        return false;
    }

    public int getBookingsMade() {
        return bookingsMade;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("==================================================================");
        System.out.println("  CINEMA SEAT-BOOKING SYSTEM: RACE CONDITIONS & SYNCHRONIZATION");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");

        // 1. OFFICIAL RUN WITH TODO 1 IMPLEMENTED (SYNCHRONIZED)
        System.out.println("\n--- OFFICIAL RUN WITH TODO 1 (SYNCHRONIZED METHOD) ---");
        SeatBooking screen = new SeatBooking(10);
        Thread[] users = new Thread[100];
        for (int i = 0; i < users.length; i++) {
            final int seat = i % 10; // 10 users compete for every seat
            users[i] = new Thread(() -> screen.book(seat));
            users[i].start();
        }
        for (Thread t : users) t.join();
        System.out.println("Seats sold: " + screen.getBookingsMade());
        System.out.println("Expected output: Seats sold: 10");

        // 2. RUNNING UNSYNCHRONIZED VERSION OVER 5 RUNS TO DEMONSTRATE DOUBLE-BOOKINGS
        System.out.println("\n--- UNSYNCHRONIZED RUNS (DEMONSTRATING DOUBLE-BOOKING DEFECT) ---");
        for (int run = 1; run <= 5; run++) {
            SeatBooking unsharedScreen = new SeatBooking(10);
            Thread[] contestants = new Thread[100];
            for (int i = 0; i < contestants.length; i++) {
                final int seat = i % 10;
                contestants[i] = new Thread(() -> unsharedScreen.bookUnsynchronized(seat));
                contestants[i].start();
            }
            for (Thread t : contestants) t.join();
            System.out.printf("Unsynchronized Run #%d -> Seats Sold: %d (OVERSOLD BY: +%d seats!)%n",
                    run, unsharedScreen.getBookingsMade(), unsharedScreen.getBookingsMade() - 10);
        }
    }
}
