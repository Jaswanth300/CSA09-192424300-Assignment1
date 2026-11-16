package q1;

import java.util.StringJoiner;

/**
 * SIMATS Java Programming Assignment 2 - Q1
 * Building Text Under Load: String, StringBuilder or StringBuffer
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class TicketBenchmark {

    // Approach 1: Immutable String concatenation using + operator
    public static String buildWithPlus(String[] fields) {
        String ticket = "";
        for (String field : fields) {
            ticket += field + " | ";
        }
        return ticket;
    }

    // Approach 2: Unsynchronized StringBuilder
    public static String buildWithBuilder(String[] fields) {
        StringBuilder sb = new StringBuilder();
        for (String field : fields) {
            sb.append(field).append(" | ");
        }
        return sb.toString();
    }

    // Approach 3: Thread-safe synchronized StringBuffer
    public static String buildWithBuffer(String[] fields) {
        StringBuffer sb = new StringBuffer();
        for (String field : fields) {
            sb.append(field).append(" | ");
        }
        return sb.toString();
    }

    // Alternative approaches for comparative analysis
    public static String buildWithJoin(String[] fields) {
        return String.join(" | ", fields);
    }

    public static String buildWithJoiner(String[] fields) {
        StringJoiner sj = new StringJoiner(" | ");
        for (String field : fields) {
            sj.add(field);
        }
        return sj.toString();
    }

    private static String[] generateFields(int count) {
        String[] fields = new String[count];
        for (int i = 0; i < count; i++) {
            fields[i] = "Field_" + i + ":Val_" + (i * 107 % 997);
        }
        return fields;
    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("  RAILWAY E-TICKET TEXT GENERATION UNDER LOAD: BENCHMARK SUITE");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");

        // Verification on small ticket
        String[] sampleFields = {"PNR:2847291039", "Train:12626-KeralaExp", "Coach:B3", "Berth:42-MB", 
                                 "Passenger:Bala Naga Jaswanth", "Quota:General", "Fare:INR 1840.00", 
                                 "Date:2026-11-20", "From:MAS", "To:SBC"};
        System.out.println("\n--- VERIFICATION TEST (10 Fields) ---");
        System.out.println("String (+)      : " + buildWithPlus(sampleFields));
        System.out.println("StringBuilder   : " + buildWithBuilder(sampleFields));
        System.out.println("StringBuffer    : " + buildWithBuffer(sampleFields));
        System.out.println("String.join()   : " + buildWithJoin(sampleFields));

        // Warm-up JVM
        for (int i = 0; i < 500; i++) {
            buildWithBuilder(sampleFields);
            buildWithPlus(sampleFields);
        }

        // Workload 1: 1 ticket with 50 fields (Single interactive generation)
        runWorkload("Workload 1: Single Ticket (50 Fields)", 1, 50);

        // Workload 2: 10,000 tickets with 50 fields each (Batch production load)
        runWorkload("Workload 2: 10,000 Tickets (50 Fields Each)", 10000, 50);

        // Workload 3: 50,000 tickets with 20 fields each (High-volume API load)
        runWorkload("Workload 3: 50,000 Tickets (20 Fields Each)", 50000, 20);
    }

    private static void runWorkload(String title, int ticketCount, int fieldsPerTicket) {
        System.out.println("\n>>> " + title + " <<<");
        String[] fields = generateFields(fieldsPerTicket);

        // 1. String (+)
        Runtime rt = Runtime.getRuntime();
        rt.gc();
        long memBefore = rt.totalMemory() - rt.freeMemory();
        long start = System.nanoTime();
        for (int i = 0; i < ticketCount; i++) {
            buildWithPlus(fields);
        }
        long timePlus = System.nanoTime() - start;
        long memAfter = rt.totalMemory() - rt.freeMemory();
        long memPlus = Math.max(0, memAfter - memBefore);

        // 2. StringBuilder
        rt.gc();
        memBefore = rt.totalMemory() - rt.freeMemory();
        start = System.nanoTime();
        for (int i = 0; i < ticketCount; i++) {
            buildWithBuilder(fields);
        }
        long timeBuilder = System.nanoTime() - start;
        memAfter = rt.totalMemory() - rt.freeMemory();
        long memBuilder = Math.max(0, memAfter - memBefore);

        // 3. StringBuffer
        rt.gc();
        memBefore = rt.totalMemory() - rt.freeMemory();
        start = System.nanoTime();
        for (int i = 0; i < ticketCount; i++) {
            buildWithBuffer(fields);
        }
        long timeBuffer = System.nanoTime() - start;
        memAfter = rt.totalMemory() - rt.freeMemory();
        long memBuffer = Math.max(0, memAfter - memBefore);

        System.out.printf("%-18s | Execution Time: %10.2f ms | Est. Heap Used: %8.2f KB%n",
                "String (+)", timePlus / 1_000_000.0, memPlus / 1024.0);
        System.out.printf("%-18s | Execution Time: %10.2f ms | Est. Heap Used: %8.2f KB%n",
                "StringBuilder", timeBuilder / 1_000_000.0, memBuilder / 1024.0);
        System.out.printf("%-18s | Execution Time: %10.2f ms | Est. Heap Used: %8.2f KB%n",
                "StringBuffer", timeBuffer / 1_000_000.0, memBuffer / 1024.0);

        double speedup = (double) timePlus / timeBuilder;
        System.out.printf("StringBuilder Speedup over String (+): %.2fx%n", speedup);
    }
}
