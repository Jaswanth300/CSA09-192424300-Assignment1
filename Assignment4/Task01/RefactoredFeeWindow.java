package task01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * SIMATS Java Programming Assignment 4 - Task 1
 * Diagnose and Repair a Poor Event-Handling Design
 * Refactored Fee Payment System with Layered Architecture
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class RefactoredFeeWindow extends JFrame {
    private final JTextField txtRegNo = new JTextField(12);
    private final JTextField txtAmount = new JTextField(12);
    private final JButton btnPay = new JButton("Pay");
    private final JButton btnClear = new JButton("Clear");
    private final JLabel lblStatus = new JLabel("Enter registration number and fee amount.");

    private final FeeService feeService = new FeeService();

    public RefactoredFeeWindow() {
        super("SIMATS Student Fee Payment Portal");
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        formPanel.add(new JLabel("Register Number:"));
        formPanel.add(txtRegNo);
        formPanel.add(new JLabel("Fee Amount (INR):"));
        formPanel.add(txtAmount);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(btnPay);
        buttonPanel.add(btnClear);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(BorderFactory.createEtchedBorder());
        statusPanel.add(lblStatus);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(statusPanel, BorderLayout.NORTH);

        // Clean event handling decoupled from business logic
        btnPay.addActionListener(this::handlePay);
        btnClear.addActionListener(e -> clearFields());

        pack();
        setLocationRelativeTo(null);
    }

    private void handlePay(ActionEvent e) {
        String regNo = txtRegNo.getText().trim();
        String amountStr = txtAmount.getText().trim();

        btnPay.setEnabled(false);
        lblStatus.setText("Processing payment securely with bank gateway...");

        // Offload database / network work to background thread to prevent UI freezing
        SwingWorker<PaymentResult, Void> worker = new SwingWorker<>() {
            @Override
            protected PaymentResult doInBackground() {
                return feeService.processPayment(regNo, amountStr);
            }

            @Override
            protected void done() {
                btnPay.setEnabled(true);
                try {
                    PaymentResult res = get();
                    if (res.isSuccess()) {
                        lblStatus.setText("Payment successful! Receipt ID: " + res.getReceiptId());
                        JOptionPane.showMessageDialog(RefactoredFeeWindow.this,
                                "Payment of INR " + res.getAmount() + " recorded for " + regNo + ".\nReceipt ID: " + res.getReceiptId(),
                                "Payment Confirmed", JOptionPane.INFORMATION_MESSAGE);
                        clearFields();
                    } else {
                        lblStatus.setText("Error: " + res.getErrorMessage());
                        JOptionPane.showMessageDialog(RefactoredFeeWindow.this,
                                res.getErrorMessage(), "Payment Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    lblStatus.setText("System error during payment.");
                }
            }
        };
        worker.execute();
    }

    private void clearFields() {
        txtRegNo.setText("");
        txtAmount.setText("");
        lblStatus.setText("Ready for next transaction.");
    }

    // Separate Service Layer
    static class FeeService {
        public PaymentResult processPayment(String regNo, String amountStr) {
            // Validation
            if (regNo.isEmpty() || !regNo.matches("^[0-9]{9}$")) {
                return new PaymentResult(false, 0, null, "Invalid Register Number. Must be 9 numeric digits.");
            }
            double amount;
            try {
                amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    return new PaymentResult(false, 0, null, "Payment amount must be greater than zero.");
                }
            } catch (NumberFormatException e) {
                return new PaymentResult(false, 0, null, "Amount must be a valid numeric figure.");
            }

            // Simulate database update & secure network bank confirmation
            try {
                Thread.sleep(500); // Simulated network latency
            } catch (InterruptedException ignored) {}

            String receipt = "RCP-" + System.currentTimeMillis() % 1000000;
            return new PaymentResult(true, amount, receipt, null);
        }
    }

    static class PaymentResult {
        private final boolean success;
        private final double amount;
        private final String receiptId;
        private final String errorMessage;

        PaymentResult(boolean success, double amount, String receiptId, String errorMessage) {
            this.success = success;
            this.amount = amount;
            this.receiptId = receiptId;
            this.errorMessage = errorMessage;
        }

        public boolean isSuccess() { return success; }
        public double getAmount() { return amount; }
        public String getReceiptId() { return receiptId; }
        public String getErrorMessage() { return errorMessage; }
    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("  TASK 1: REFACTORED STUDENT FEE WINDOW (MVC ARCHITECTURE)");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");

        FeeService service = new FeeService();
        System.out.println("Testing FeeService Logic Headless:");
        System.out.println("Test 1 (Valid)   : " + service.processPayment("192424300", "25000.00").isSuccess());
        System.out.println("Test 2 (Invalid) : " + service.processPayment("badReg", "25000.00").getErrorMessage());
        System.out.println("Test 3 (Negative): " + service.processPayment("192424300", "-500").getErrorMessage());
    }
}
