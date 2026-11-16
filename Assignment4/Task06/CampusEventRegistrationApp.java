package task06;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * SIMATS Java Programming Assignment 4 - Task 6 (Design Challenge)
 * Campus Event Registration Desktop Application (Layered Swing + JDBC Simulation)
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class CampusEventRegistrationApp extends JFrame {

    // Domain Models
    public record Registration(int id, String studentId, String name, String email, String eventId, String registeredAt) {}

    // DAO / Data Access Layer
    public static class RegistrationDAO {
        private final List<Registration> store = new ArrayList<>();
        private int nextId = 1;

        public RegistrationDAO() {
            // Seed initial records
            save(new Registration(nextId++, "192424300", "Bala Naga Jaswanth", "jaswanth@simats.edu", "EVT-HACK", "2026-11-15 10:30"));
            save(new Registration(nextId++, "192424301", "Akula Reddy Prasad", "reddy@simats.edu", "EVT-ROBO", "2026-11-15 11:15"));
            save(new Registration(nextId++, "192424302", "Pooja V", "pooja@simats.edu", "EVT-COD", "2026-11-15 12:00"));
        }

        public synchronized boolean exists(String studentId, String eventId) {
            return store.stream().anyMatch(r -> r.studentId().equalsIgnoreCase(studentId) && r.eventId().equalsIgnoreCase(eventId));
        }

        public synchronized void save(Registration r) {
            store.add(r);
        }

        public synchronized List<Registration> findByEvent(String eventFilter) {
            if ("All Events".equalsIgnoreCase(eventFilter)) {
                return new ArrayList<>(store);
            }
            List<Registration> result = new ArrayList<>();
            for (Registration r : store) {
                if (r.eventId().equalsIgnoreCase(eventFilter)) {
                    result.add(r);
                }
            }
            return result;
        }

        public synchronized int count() { return store.size(); }
    }

    // UI Components
    private final JTextField txtStudentId = new JTextField(12);
    private final JTextField txtStudentName = new JTextField(15);
    private final JTextField txtEmail = new JTextField(15);
    private final JComboBox<String> cmbRegisterEvent = new JComboBox<>(new String[]{
            "EVT-HACK (National Hackathon)",
            "EVT-ROBO (RoboWars Combat)",
            "EVT-COD (Coding Showdown)",
            "EVT-CULT (Cultural Gala)"
    });
    private final JComboBox<String> cmbFilterEvent = new JComboBox<>(new String[]{
            "All Events", "EVT-HACK", "EVT-ROBO", "EVT-COD", "EVT-CULT"
    });

    private final DefaultTableModel tableModel;
    private final JTable tblRegistrations;
    private final JLabel lblStatusBar = new JLabel("Status: Connected to Database. Ready.");

    private final RegistrationDAO dao = new RegistrationDAO();

    public CampusEventRegistrationApp() {
        super("SIMATS University - Campus Event Registration Desktop Portal");

        // Table Model Setup
        String[] columns = {"Reg ID", "Student ID", "Full Name", "Email Address", "Event Code", "Timestamp"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tblRegistrations = new JTable(tableModel);

        initUI();
        refreshTable();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        // 1. Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem itemExport = new JMenuItem("Export to CSV");
        JMenuItem itemExit = new JMenuItem("Exit");
        itemExit.addActionListener(e -> System.exit(0));
        menuFile.add(itemExport);
        menuFile.addSeparator();
        menuFile.add(itemExit);

        JMenu menuHelp = new JMenu("Help");
        JMenuItem itemAbout = new JMenuItem("About");
        itemAbout.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "SIMATS Event Registration Desktop App\nStudent: Bala Naga Jaswanth (192424300)\nAssignment 4 Design Challenge",
                "About System", JOptionPane.INFORMATION_MESSAGE));
        menuHelp.add(itemAbout);
        menuBar.add(menuFile);
        menuBar.add(menuHelp);
        setJMenuBar(menuBar);

        // 2. Left Registration Form Panel
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createTitledBorder("New Student Registration"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; pnlForm.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; pnlForm.add(txtStudentId, gbc);

        gbc.gridx = 0; gbc.gridy = 1; pnlForm.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; pnlForm.add(txtStudentName, gbc);

        gbc.gridx = 0; gbc.gridy = 2; pnlForm.add(new JLabel("Email Address:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; pnlForm.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 3; pnlForm.add(new JLabel("Target Event:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; pnlForm.add(cmbRegisterEvent, gbc);

        JButton btnRegister = new JButton("Register Student");
        JButton btnClear = new JButton("Clear");
        JPanel pnlFormBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlFormBtns.add(btnClear);
        pnlFormBtns.add(btnRegister);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        pnlForm.add(pnlFormBtns, gbc);

        // 3. Center Table & Filter Panel
        JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));
        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlFilter.add(new JLabel("Filter by Event:"));
        pnlFilter.add(cmbFilterEvent);
        JButton btnRefresh = new JButton("Refresh");
        pnlFilter.add(btnRefresh);

        JScrollPane scrollTable = new JScrollPane(tblRegistrations);
        scrollTable.setBorder(BorderFactory.createTitledBorder("Confirmed Registrations"));

        pnlCenter.add(pnlFilter, BorderLayout.NORTH);
        pnlCenter.add(scrollTable, BorderLayout.CENTER);

        // 4. South Status Bar
        JPanel pnlStatus = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        pnlStatus.add(lblStatusBar);

        // Assemble Layout
        add(pnlForm, BorderLayout.WEST);
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlStatus, BorderLayout.SOUTH);

        // Event Listeners
        btnRegister.addActionListener(e -> handleRegistration());
        btnClear.addActionListener(e -> clearForm());
        cmbFilterEvent.addActionListener(e -> refreshTable());
        btnRefresh.addActionListener(e -> refreshTable());

        setSize(950, 500);
        setLocationRelativeTo(null);
    }

    private void handleRegistration() {
        String studentId = txtStudentId.getText().trim();
        String name = txtStudentName.getText().trim();
        String email = txtEmail.getText().trim();
        String selected = (String) cmbRegisterEvent.getSelectedItem();
        String eventCode = selected.split(" ")[0];

        // Validation
        if (studentId.isEmpty() || !studentId.matches("^[0-9]{9}$")) {
            JOptionPane.showMessageDialog(this, "Student ID must be a 9-digit numeric university registration number.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (name.length() < 3) {
            JOptionPane.showMessageDialog(this, "Please enter a valid student name.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Duplicate Check
        if (dao.exists(studentId, eventCode)) {
            lblStatusBar.setText("REJECTED: Duplicate registration detected for " + studentId + " in " + eventCode);
            JOptionPane.showMessageDialog(this,
                    "Student " + studentId + " is ALREADY registered for event " + eventCode + "!\nDuplicate registrations are prohibited.",
                    "Integrity Violation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Save Record
        int newId = dao.count() + 1;
        Registration r = new Registration(newId, studentId, name, email, eventCode, "2026-11-16 21:00");
        dao.save(r);
        lblStatusBar.setText("SUCCESS: Registered " + name + " for " + eventCode + " (Reg ID: " + newId + ")");
        JOptionPane.showMessageDialog(this,
                "Registration confirmed for " + name + " in " + eventCode + "!\nConfirmation ID: " + newId,
                "Success", JOptionPane.INFORMATION_MESSAGE);
        clearForm();
        refreshTable();
    }

    private void clearForm() {
        txtStudentId.setText("");
        txtStudentName.setText("");
        txtEmail.setText("");
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        String filter = (String) cmbFilterEvent.getSelectedItem();
        List<Registration> records = dao.findByEvent(filter);
        for (Registration r : records) {
            tableModel.addRow(new Object[]{
                    r.id(), r.studentId(), r.name(), r.email(), r.eventId(), r.registeredAt()
            });
        }
        lblStatusBar.setText("Showing " + records.size() + " records for filter: " + filter);
    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("  TASK 6: CAMPUS EVENT REGISTRATION DESKTOP APP (SWING + JDBC)");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");
        CampusEventRegistrationApp app = new CampusEventRegistrationApp();
        System.out.println("Application initialized with layered UI -> DAO architecture.");
        System.out.println("Database records active: " + app.dao.count());
    }
}
