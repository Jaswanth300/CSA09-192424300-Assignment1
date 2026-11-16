package q1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * SIMATS Java Programming Assignment 3 - Q1
 * Handling Failure in a Bank ATM Simulation
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
class InsufficientFundsException extends Exception {
    private final double deficit;

    public InsufficientFundsException(String message, double deficit) {
        super(message);
        this.deficit = deficit;
    }

    public double getDeficit() {
        return deficit;
    }
}

public class ATMSimulation {
    private double balance = 5000.00;

    public void processWithdrawal(String accInput, String amountInput, String cardFileName) {
        System.out.println("\n--------------------------------------------------------------");
        System.out.printf("TRANSACTION ATTEMPT | Acc: '%s', Amount: '%s', CardFile: '%s'%n",
                accInput, amountInput, cardFileName);

        Scanner fileScanner = null;
        try {
            // 1. Check card-reader hardware file (Checked Exception: FileNotFoundException)
            File cardFile = new File(cardFileName);
            if (!cardFile.exists()) {
                throw new FileNotFoundException("Card-reader peripheral configuration missing: " + cardFileName);
            }
            fileScanner = new Scanner(cardFile);

            // 2. Validate Account Number format (Unchecked: NumberFormatException)
            int accountNo = Integer.parseInt(accInput.trim());
            if (accountNo <= 0) {
                throw new IllegalArgumentException("Account number must be a positive integer.");
            }

            // 3. Validate Withdrawal Amount (Unchecked: NumberFormatException / IllegalArgumentException)
            double amount = Double.parseDouble(amountInput.trim());
            if (amount <= 0) {
                throw new IllegalArgumentException("Withdrawal amount must be strictly greater than zero.");
            }

            // 4. Check Balance (Custom Checked Exception: InsufficientFundsException)
            if (amount > balance) {
                double deficit = amount - balance;
                throw new InsufficientFundsException(
                        String.format("Insufficient funds! Requested: INR %.2f, Available: INR %.2f", amount, balance),
                        deficit);
            }

            // Successful transaction
            balance -= amount;
            System.out.printf(">> SUCCESS: Dispensed INR %.2f | New Account Balance: INR %.2f%n", amount, balance);

        } catch (FileNotFoundException fnfe) {
            System.err.println("[HARDWARE ERROR] " + fnfe.getMessage());
        } catch (NumberFormatException nfe) {
            System.err.println("[INPUT FORMAT ERROR] Invalid alphanumeric input detected. Please enter numbers only: " + nfe.getMessage());
        } catch (IllegalArgumentException iae) {
            System.err.println("[BUSINESS LOGIC ERROR] " + iae.getMessage());
        } catch (InsufficientFundsException ife) {
            System.err.printf("[OVERDRAFT REJECTED] %s (Deficit: INR %.2f)%n", ife.getMessage(), ife.getDeficit());
        } finally {
            if (fileScanner != null) {
                fileScanner.close();
            }
            System.out.println("[FINALLY GUARANTEE] Hardware lock released, card ejected, session audit logged.");
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("==================================================================");
        System.out.println("  BANK ATM SIMULATION: ROBUST EXCEPTION HANDLING & TRACE");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");

        // Create dummy card file for tests
        File validCard = new File("card_reader.cfg");
        validCard.createNewFile();
        validCard.deleteOnExit();

        ATMSimulation atm = new ATMSimulation();

        // Test 1: Valid Transaction
        atm.processWithdrawal("100234", "1500.00", "card_reader.cfg");

        // Test 2: Input Mismatch / Alphabetic input
        atm.processWithdrawal("100234", "FiveHundred", "card_reader.cfg");

        // Test 3: Negative Amount
        atm.processWithdrawal("100234", "-200.00", "card_reader.cfg");

        // Test 4: Insufficient Funds
        atm.processWithdrawal("100234", "8000.00", "card_reader.cfg");

        // Test 5: Missing Card Reader Hardware File
        atm.processWithdrawal("100234", "500.00", "non_existent_card_reader.cfg");
    }
}
