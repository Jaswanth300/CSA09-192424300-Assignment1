package q4;

import java.util.*;

/**
 * SIMATS Java Programming Assignment 2 - Q4
 * Choosing a Map for a Pharmacy Inventory
 * Comparing HashMap, TreeMap and LinkedHashMap under Real Load
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class PharmacyInventory {

    static class Medicine {
        final String code;
        final String name;
        final int stock;
        final double price;

        Medicine(String code, String name, int stock, double price) {
            this.code = code;
            this.name = name;
            this.stock = stock;
            this.price = price;
        }

        @Override
        public String toString() {
            return String.format("[%s: %s, Stock: %d, INR %.2f]", code, name, stock, price);
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("  HOSPITAL PHARMACY STOCK LOOKUP & REPORTING BENCHMARK");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");

        int[] datasetSizes = {1000, 10000, 50000};
        int lookupCount = 100000;

        for (int n : datasetSizes) {
            System.out.println("\n-------------------------------------------------------------");
            System.out.printf("  EVALUATION DATASET SIZE: N = %d MEDICINES%n", n);
            System.out.println("-------------------------------------------------------------");

            // Generate synthetic medicines in pseudo-random order
            List<Medicine> inputList = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                // Scrambled code to test sorting
                int scrambledId = (i * 7919) % (n * 3);
                String code = String.format("MED-%06d", scrambledId);
                inputList.add(new Medicine(code, "Drug_" + i, 50 + (i % 200), 15.50 + (i % 500)));
            }

            // Populate Maps
            Map<String, Medicine> hashMap = new HashMap<>();
            Map<String, Medicine> treeMap = new TreeMap<>();
            Map<String, Medicine> linkedHashMap = new LinkedHashMap<>();

            for (Medicine m : inputList) {
                hashMap.put(m.code, m);
                treeMap.put(m.code, m);
                linkedHashMap.put(m.code, m);
            }

            // Benchmark 1: 100,000 Lookups
            String[] queryCodes = new String[lookupCount];
            Random rnd = new Random(42);
            for (int i = 0; i < lookupCount; i++) {
                queryCodes[i] = inputList.get(rnd.nextInt(inputList.size())).code;
            }

            // HashMap Lookups
            long t0 = System.nanoTime();
            long hitsHash = 0;
            for (String code : queryCodes) {
                if (hashMap.get(code) != null) hitsHash++;
            }
            long timeHashGet = System.nanoTime() - t0;

            // TreeMap Lookups
            t0 = System.nanoTime();
            long hitsTree = 0;
            for (String code : queryCodes) {
                if (treeMap.get(code) != null) hitsTree++;
            }
            long timeTreeGet = System.nanoTime() - t0;

            // LinkedHashMap Lookups
            t0 = System.nanoTime();
            long hitsLinked = 0;
            for (String code : queryCodes) {
                if (linkedHashMap.get(code) != null) hitsLinked++;
            }
            long timeLinkedGet = System.nanoTime() - t0;

            System.out.printf("100,000 Lookups Time | HashMap: %8.2f ms | TreeMap: %8.2f ms | LinkedHashMap: %8.2f ms%n",
                    timeHashGet / 1e6, timeTreeGet / 1e6, timeLinkedGet / 1e6);

            // Benchmark 2: Report 1 (Code Order - Alphabetical)
            // TreeMap produces it naturally
            t0 = System.nanoTime();
            int countTreeRep1 = 0;
            for (Map.Entry<String, Medicine> e : treeMap.entrySet()) {
                countTreeRep1++;
            }
            long timeTreeRep1 = System.nanoTime() - t0;

            // HashMap must extract and sort keys explicitly: O(N log N)
            t0 = System.nanoTime();
            List<Medicine> hashSorted = new ArrayList<>(hashMap.values());
            hashSorted.sort(Comparator.comparing(m -> m.code));
            long timeHashRep1 = System.nanoTime() - t0;

            // LinkedHashMap must also sort keys explicitly: O(N log N)
            t0 = System.nanoTime();
            List<Medicine> linkedSorted = new ArrayList<>(linkedHashMap.values());
            linkedSorted.sort(Comparator.comparing(m -> m.code));
            long timeLinkedRep1 = System.nanoTime() - t0;

            System.out.printf("Report 1 (Code Order) | TreeMap: %8.3f ms (Natural) | HashMap: %8.3f ms (Extra Sort) | LinkedHashMap: %8.3f ms%n",
                    timeTreeRep1 / 1e6, timeHashRep1 / 1e6, timeLinkedRep1 / 1e6);

            // Benchmark 3: Report 2 (Addition Order - Chronological)
            // LinkedHashMap produces it naturally
            t0 = System.nanoTime();
            int countLinkedRep2 = 0;
            for (Map.Entry<String, Medicine> e : linkedHashMap.entrySet()) {
                countLinkedRep2++;
            }
            long timeLinkedRep2 = System.nanoTime() - t0;

            // HashMap CANNOT reproduce addition order naturally! (Requires external timestamp/list)
            // TreeMap CANNOT reproduce addition order (keys are always sorted by natural order)
            System.out.printf("Report 2 (Add Order)  | LinkedHashMap: %8.3f ms (Natural) | HashMap: Impossible without extra state | TreeMap: Impossible%n",
                    timeLinkedRep2 / 1e6);
        }

        // Summary Architectural Recommendation
        System.out.println("\n==================================================================");
        System.out.println("  DESIGN DECISION & SCALABILITY VERDICT");
        System.out.println("==================================================================");
        System.out.println("1. Recommended Deployment: LinkedHashMap<String, Medicine>");
        System.out.println("   - Provides O(1) average lookup performance identical to HashMap.");
        System.out.println("   - Preserves insertion order inherently for Report 2 with ZERO extra memory.");
        System.out.println("   - For evening Report 1 (Code Order), sorting N elements once a day is negligible.");
        System.out.println("2. Why Hashtable is Rejected in Modern Architecture:");
        System.out.println("   - Method-level synchronization creates massive thread contention.");
        System.out.println("   - Does not implement modern Map enhancements (computeIfAbsent, Streams, etc.).");
    }
}
