package task05;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * SIMATS Java Programming Assignment 4 - Task 5
 * Same Screen, Different Event-Handling Styles: Currency Converter (Lambda Version)
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class Converter extends JFrame {
    private final JTextField amount = new JTextField(10);
    private final JComboBox<String> target =
            new JComboBox<>(new String[]{"USD", "EUR"});
    private final JLabel result = new JLabel(" ");

    private static double rate(String currency) {
        return currency.equals("USD") ? 0.012 : 0.011;
    }

    public Converter() {
        super("Currency Converter");
        setLayout(new FlowLayout());
        JButton convertButton = new JButton("Convert");
        add(new JLabel("INR"));
        add(amount);
        add(target);
        add(convertButton);
        add(result);

        // TODO 1: Register ONE lambda listener for both the Convert button and the Enter key in the amount field
        ActionListener convertListener = e -> convert();
        convertButton.addActionListener(convertListener);
        amount.addActionListener(convertListener);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void convert() {
        // TODO 2: Parse the amount and show the converted value with 2 decimals, or "Invalid amount" if parsing fails
        try {
            double inr = Double.parseDouble(amount.getText().trim());
            if (inr < 0) {
                result.setText("Invalid amount");
                return;
            }
            String curr = (String) target.getSelectedItem();
            double converted = inr * rate(curr);
            result.setText(String.format("%.2f %s", converted, curr));
        } catch (NumberFormatException ex) {
            result.setText("Invalid amount");
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("  TASK 5: SAME SCREEN, DIFFERENT EVENT-HANDLING STYLES");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");
        Converter c = new Converter();
        System.out.println("Initialized Converter with unified lambda listener for JButton and JTextField Enter key.");
    }
}
