package q5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * SIMATS Java Programming Assignment 2 - Q5
 * Derive a Generic Ranking Component from a Principle
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
class Team {
    final String name;
    final int points;
    final int goalDiff;

    Team(String name, int points, int goalDiff) {
        this.name = name;
        this.points = points;
        this.goalDiff = goalDiff;
    }

    @Override
    public String toString() {
        return name + "(" + points + "," + goalDiff + ")";
    }
}

class Leaderboard<T> {
    private final List<T> items = new ArrayList<>();
    private final Comparator<? super T> order;

    Leaderboard(Comparator<? super T> order) {
        this.order = order;
    }

    void add(T item) {
        items.add(item);
    }

    List<T> ranking() {
        // TODO 1: Return a new sorted copy of items using order, without modifying items
        List<T> sortedList = new ArrayList<>(items);
        sortedList.sort(order);
        return sortedList;
    }
}

public class LeaderboardDemo {
    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("  GENERIC LEADERBOARD RANKING COMPONENT FROM FIRST PRINCIPLES");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");

        // TODO 2: Build the comparator: points (high first), then goal difference (high first), then name (A-Z)
        Comparator<Team> byRank = Comparator
                .comparingInt((Team t) -> t.points).reversed()
                .thenComparing(Comparator.comparingInt((Team t) -> t.goalDiff).reversed())
                .thenComparing((Team t) -> t.name);

        Leaderboard<Team> board = new Leaderboard<>(byRank);
        board.add(new Team("Panthers", 9, 4));
        board.add(new Team("Eagles", 9, 6));
        board.add(new Team("Cobras", 6, 1));
        board.add(new Team("Bisons", 9, 4));

        List<Team> results = board.ranking();
        System.out.println("\nOfficial Leaderboard Ranking Output:");
        System.out.println(results);
        System.out.println("Expected output: [Eagles(9,6), Bisons(9,4), Panthers(9,4), Cobras(6,1)]");

        System.out.println("\n--- TIE-BREAKER VERIFICATION ANALYSIS ---");
        System.out.println("Eagles: 9 pts, +6 diff -> Rank 1 (Highest goal difference among 9-pointers)");
        System.out.println("Bisons vs Panthers: Both have 9 pts and +4 diff (TIED on points & goal difference).");
        System.out.println("Tie-breaker (Lexicographical name): \"Bisons\".compareTo(\"Panthers\") < 0 -> Bisons ranks higher!");

        // FAULTY COMPARATOR DEMONSTRATION (Subtraction overflow & missing tie-breaker)
        System.out.println("\n--- FAULTY COMPARATOR FAILURE DEMO ---");
        Comparator<Team> faultyOrder = (t1, t2) -> (t2.points - t1.points); // Subtraction bug & ignores ties!
        Leaderboard<Team> faultyBoard = new Leaderboard<>(faultyOrder);
        faultyBoard.add(new Team("Panthers", 9, 4));
        faultyBoard.add(new Team("Bisons", 9, 4));
        System.out.println("Faulty Board Ranking: " + faultyBoard.ranking());
        System.out.println("Result: Inconsistent ordering because Bisons and Panthers evaluate to 0 (treated as equal).");

        // REUSABILITY PROOF: Generic Leaderboard for Quiz Teams
        System.out.println("\n--- REUSABILITY PROOF: Ranking Quiz Teams using Leaderboard<QuizTeam> ---");
        record QuizTeam(String college, int score, int accuracy) {}
        Comparator<QuizTeam> quizOrder = Comparator.comparingInt(QuizTeam::score).reversed()
                .thenComparing(Comparator.comparingInt(QuizTeam::accuracy).reversed());
        Leaderboard<QuizTeam> quizBoard = new Leaderboard<>(quizOrder);
        quizBoard.add(new QuizTeam("IIT Madras", 95, 98));
        quizBoard.add(new QuizTeam("SIMATS Eng", 95, 99));
        quizBoard.add(new QuizTeam("Anna Univ", 85, 90));
        System.out.println("Quiz Leaderboard: " + quizBoard.ranking());
    }
}
