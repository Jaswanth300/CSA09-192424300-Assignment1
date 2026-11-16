package q2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * SIMATS Java Programming Assignment 2 - Q2
 * When Is ArrayList an Appropriate Engineering Choice?
 * Music Playlist Editor & "Up Next" Queue Benchmark
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class PlaylistBenchmark {

    static class Song {
        final String title;
        final String artist;
        final int durationSec;

        Song(String title, String artist, int durationSec) {
            this.title = title;
            this.artist = artist;
            this.durationSec = durationSec;
        }

        @Override
        public String toString() {
            return title + " - " + artist + " (" + durationSec + "s)";
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("  MUSIC PLAYLIST BENCHMARK: ARRAYLIST VS LINKEDLIST VS ARRAYDEQUE");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");

        int[] sizes = {5000, 20000, 50000};

        for (int n : sizes) {
            System.out.println("\n-------------------------------------------------------------");
            System.out.printf("  BENCHMARK DATASET SIZE: N = %d SONGS%n", n);
            System.out.println("-------------------------------------------------------------");

            // 1. Add at End (Append)
            long tStart = System.nanoTime();
            List<Song> arrayList = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                arrayList.add(new Song("Song_" + i, "Artist_" + (i % 50), 210));
            }
            long timeArrayAddEnd = System.nanoTime() - tStart;

            tStart = System.nanoTime();
            List<Song> linkedList = new LinkedList<>();
            for (int i = 0; i < n; i++) {
                linkedList.add(new Song("Song_" + i, "Artist_" + (i % 50), 210));
            }
            long timeLinkedAddEnd = System.nanoTime() - tStart;

            System.out.printf("1. Add at End (N ops)      | ArrayList: %8.3f ms | LinkedList: %8.3f ms%n",
                    timeArrayAddEnd / 1e6, timeLinkedAddEnd / 1e6);

            // 2. Access k-th element (1,000 random accesses)
            int sampleLookups = 2000;
            tStart = System.nanoTime();
            long checkSum1 = 0;
            for (int i = 0; i < sampleLookups; i++) {
                int idx = (i * 1013) % n;
                checkSum1 += arrayList.get(idx).durationSec;
            }
            long timeArrayGet = System.nanoTime() - tStart;

            tStart = System.nanoTime();
            long checkSum2 = 0;
            for (int i = 0; i < sampleLookups; i++) {
                int idx = (i * 1013) % n;
                checkSum2 += linkedList.get(idx).durationSec;
            }
            long timeLinkedGet = System.nanoTime() - tStart;

            System.out.printf("2. Get k-th (%d ops)    | ArrayList: %8.3f ms | LinkedList: %8.3f ms%n",
                    sampleLookups, timeArrayGet / 1e6, timeLinkedGet / 1e6);

            // 3. Sequential Iteration (Full pass)
            tStart = System.nanoTime();
            long durSum1 = 0;
            for (Song s : arrayList) {
                durSum1 += s.durationSec;
            }
            long timeArrayIter = System.nanoTime() - tStart;

            tStart = System.nanoTime();
            long durSum2 = 0;
            for (Song s : linkedList) {
                durSum2 += s.durationSec;
            }
            long timeLinkedIter = System.nanoTime() - tStart;

            System.out.printf("3. Iterate Whole List       | ArrayList: %8.3f ms | LinkedList: %8.3f ms%n",
                    timeArrayIter / 1e6, timeLinkedIter / 1e6);

            // 4. Insert at Front (1,000 insertions at index 0)
            int frontOps = 1000;
            List<Song> arrFront = new ArrayList<>(arrayList);
            tStart = System.nanoTime();
            for (int i = 0; i < frontOps; i++) {
                arrFront.add(0, new Song("Front_" + i, "Promo", 180));
            }
            long timeArrayFront = System.nanoTime() - tStart;

            List<Song> linkFront = new LinkedList<>(linkedList);
            tStart = System.nanoTime();
            for (int i = 0; i < frontOps; i++) {
                linkFront.add(0, new Song("Front_" + i, "Promo", 180));
            }
            long timeLinkedFront = System.nanoTime() - tStart;

            System.out.printf("4. Insert at Front (%d ops) | ArrayList: %8.3f ms | LinkedList: %8.3f ms%n",
                    frontOps, timeArrayFront / 1e6, timeLinkedFront / 1e6);

            // 5. Insert in Middle (1,000 insertions at index size/2)
            int midOps = 1000;
            List<Song> arrMid = new ArrayList<>(arrayList);
            tStart = System.nanoTime();
            for (int i = 0; i < midOps; i++) {
                arrMid.add(arrMid.size() / 2, new Song("Mid_" + i, "Interlude", 90));
            }
            long timeArrayMid = System.nanoTime() - tStart;

            List<Song> linkMid = new LinkedList<>(linkedList);
            tStart = System.nanoTime();
            for (int i = 0; i < midOps; i++) {
                linkMid.add(linkMid.size() / 2, new Song("Mid_" + i, "Interlude", 90));
            }
            long timeLinkedMid = System.nanoTime() - tStart;

            System.out.printf("5. Insert Middle (%d ops)   | ArrayList: %8.3f ms | LinkedList: %8.3f ms%n",
                    midOps, timeArrayMid / 1e6, timeLinkedMid / 1e6);
        }

        // Changed Condition: "Up Next" Queue (Add at both ends, remove from front)
        System.out.println("\n==================================================================");
        System.out.println("  CHANGED CONDITION: 'UP NEXT' QUEUE EVALUATION (N = 50,000 ops)");
        System.out.println("==================================================================");
        int queueOps = 50000;

        // LinkedList as Deque
        long tStart = System.nanoTime();
        LinkedList<Song> llDeque = new LinkedList<>();
        for (int i = 0; i < queueOps; i++) {
            if ((i & 1) == 0) llDeque.addFirst(new Song("Head_" + i, "Artist", 200));
            else llDeque.addLast(new Song("Tail_" + i, "Artist", 200));
            if (i > 1000) llDeque.removeFirst();
        }
        long timeLLDeque = System.nanoTime() - tStart;

        // ArrayDeque
        tStart = System.nanoTime();
        ArrayDeque<Song> arrDeque = new ArrayDeque<>();
        for (int i = 0; i < queueOps; i++) {
            if ((i & 1) == 0) arrDeque.addFirst(new Song("Head_" + i, "Artist", 200));
            else arrDeque.addLast(new Song("Tail_" + i, "Artist", 200));
            if (i > 1000) arrDeque.removeFirst();
        }
        long timeArrDeque = System.nanoTime() - tStart;

        System.out.printf("LinkedList as Deque : %8.3f ms%n", timeLLDeque / 1e6);
        System.out.printf("ArrayDeque          : %8.3f ms%n", timeArrDeque / 1e6);
        System.out.printf("ArrayDeque is %.2fx faster than LinkedList for double-ended queue ops!%n",
                (double) timeLLDeque / timeArrDeque);
    }
}
