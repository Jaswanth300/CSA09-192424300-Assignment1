package task03;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * SIMATS Java Programming Assignment 4 - Task 3
 * Campus Library Search Screen: Nested Container & Resizing Layout Architecture
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class LibrarySearchScreen extends JFrame {
    private final JTextField txtSearch = new JTextField(20);
    private final JComboBox<String> cmbCategory = new JComboBox<>(new String[]{
            "All Categories", "Computer Science", "Electrical Engineering", "Mechanical", "Mathematics", "Fiction"
    });
    private final JButton btnSearch = new JButton("Search Catalog");
    private final JButton btnPrev = new JButton("<< Previous");
    private final JButton btnNext = new JButton("Next >>");
    private final JButton btnReserve = new JButton("Reserve Book");
    private final JLabel lblStatus = new JLabel("Showing 4 results for query.");

    private final DefaultTableModel tableModel;
    private final JTable tblResults;

    public LibrarySearchScreen() {
        super("SIMATS Central Library - Catalog Search System");

        // Table Model Setup
        String[] columns = {"Accession No", "Title", "Author(s)", "Edition", "Availability"};
        Object[][] data = {
                {"CS-10921", "Effective Java (3rd Edition)", "Joshua Bloch", "2018", "Available (Shelf 4A)"},
                {"CS-10922", "Java: The Complete Reference", "Herbert Schildt", "12th Ed", "Available (Shelf 4B)"},
                {"CS-10923", "Introduction to Algorithms (CLRS)", "Cormen et al.", "4th Ed", "Checked Out (Due 24-Nov)"},
                {"CS-10924", "Operating System Concepts", "Silberschatz et al.", "10th Ed", "Available (Shelf 2C)"}
        };
        tableModel = new DefaultTableModel(data, columns);
        tblResults = new JTable(tableModel);

        initLayout();
    }

    private void initLayout() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Master Container uses BorderLayout
        setLayout(new BorderLayout(5, 5));

        // 1. NORTH: Title & Search Row Container
        JPanel pnlNorth = new JPanel(new BorderLayout(5, 5));
        JLabel lblHeader = new JLabel("SIMATS LIBRARY CATALOG SEARCH", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        pnlNorth.add(lblHeader, BorderLayout.NORTH);

        // Search Row uses FlowLayout to maintain compact component sizes during resize
        JPanel pnlSearchRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        pnlSearchRow.add(new JLabel("Keywords:"));
        pnlSearchRow.add(txtSearch);
        pnlSearchRow.add(new JLabel("Category:"));
        pnlSearchRow.add(cmbCategory);
        pnlSearchRow.add(btnSearch);
        pnlNorth.add(pnlSearchRow, BorderLayout.SOUTH);

        // 2. CENTER: Results Table in JScrollPane (Takes ALL expanding space on resize)
        JScrollPane scrollPane = new JScrollPane(tblResults);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Catalog Query Results"));

        // 3. SOUTH: Pagination, Actions, and Status Bar
        JPanel pnlSouth = new JPanel(new BorderLayout());
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        pnlButtons.add(btnPrev);
        pnlButtons.add(btnNext);
        pnlButtons.add(btnReserve);

        JPanel pnlStatus = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        pnlStatus.add(lblStatus);

        pnlSouth.add(pnlButtons, BorderLayout.NORTH);
        pnlSouth.add(pnlStatus, BorderLayout.SOUTH);

        // Add to Frame
        add(pnlNorth, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(pnlSouth, BorderLayout.SOUTH);

        setSize(750, 450);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("  TASK 3: CAMPUS LIBRARY SEARCH SCREEN (LAYOUT RESIZING)");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");
        LibrarySearchScreen screen = new LibrarySearchScreen();
        System.out.println("Layout configured: BorderLayout master with nested FlowLayout and center JScrollPane.");
        System.out.println("Table columns: Accession No, Title, Author, Edition, Availability.");
    }
}
