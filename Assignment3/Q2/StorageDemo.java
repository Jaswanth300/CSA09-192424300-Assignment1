package q2;

import java.io.IOException;

/**
 * SIMATS Java Programming Assignment 3 - Q2
 * User-Defined Exceptions and Propagation in an Online Examination Portal
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
class ExamException extends Exception {
    ExamException(String message) {
        super(message);
    }

    ExamException(String message, Throwable cause) {
        super(message, cause);
    }
}

class SubmissionStorageException extends ExamException {
    SubmissionStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

class WindowClosedException extends ExamException {
    WindowClosedException(String message) {
        super(message);
    }
}

class StudentNotRegisteredException extends ExamException {
    StudentNotRegisteredException(String message) {
        super(message);
    }
}

class Storage {
    void save(String fileName, long sizeKb) throws SubmissionStorageException {
        try {
            if (sizeKb > 500) {
                throw new IOException("Disk quota exceeded");
            }
            System.out.println("Saved " + fileName);
        } catch (IOException e) {
            // TODO 1: Wrap the IOException in a SubmissionStorageException, keeping the IOException as its cause
            throw new SubmissionStorageException("Could not store " + fileName, e);
        }
    }
}

public class StorageDemo {
    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("  ONLINE EXAMINATION PORTAL: EXCEPTION PROPAGATION & CHAINING");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");

        // OFFICIAL COMPLETE THE CODE RUN
        Storage storage = new Storage();
        try {
            storage.save("answer01.pdf", 120);
            storage.save("answer02.pdf", 800);
        } catch (ExamException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Cause: " + (e.getCause() != null ? e.getCause().getMessage() : "null"));
        }

        System.out.println("\nExpected output:");
        System.out.println("Saved answer01.pdf\nError: Could not store answer02.pdf\nCause: Disk quota exceeded");

        // DEMONSTRATING MULTI-LAYER FAILURE SCENARIOS
        System.out.println("\n--- MULTI-LAYER FAILURE SCENARIOS ---");
        try {
            validateSubmission("192424300", "CS101", 1750, 1700); // Window closed
        } catch (ExamException ee) {
            System.out.println("[CONTROLLER CAUGHT] " + ee.getClass().getSimpleName() + ": " + ee.getMessage());
        }

        try {
            validateSubmission("999999999", "CS101", 1600, 1700); // Unregistered
        } catch (ExamException ee) {
            System.out.println("[CONTROLLER CAUGHT] " + ee.getClass().getSimpleName() + ": " + ee.getMessage());
        }
    }

    private static void validateSubmission(String regNo, String course, int currentTime, int deadlineTime) throws ExamException {
        if (currentTime > deadlineTime) {
            throw new WindowClosedException("Submission portal deadline (" + deadlineTime + " hrs) has expired. Current time: " + currentTime + " hrs.");
        }
        if (!regNo.startsWith("1924")) {
            throw new StudentNotRegisteredException("Student " + regNo + " is not formally enrolled in " + course);
        }
    }
}
