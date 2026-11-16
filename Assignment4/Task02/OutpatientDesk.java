package task02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.*;
import java.util.List;

/**
 * SIMATS Java Programming Assignment 4 - Task 2
 * Hospital Outpatient Appointment Desk: Event-Driven GUI Design
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class OutpatientDesk extends JFrame {
    private final JTextField txtPatientId = new JTextField(15);
    private final JComboBox<String> cmbDepartment;
    private final JComboBox<String> cmbDoctor;
    private final JComboBox<String> cmbTimeSlot;
    private final JButton btnBook = new JButton("Book Appointment");
    private final JButton btnReset = new JButton("Clear");
    private final JLabel lblStatus = new JLabel("Status: Awaiting input.");

    // In-memory mapping of departments to doctor lists
    private final Map<String, List<String>> deptDoctorMap = new LinkedHashMap<>();

    public OutpatientDesk() {
        super("SIMATS Hospital - Outpatient Appointment Desk");

        // Seed data
        deptDoctorMap.put("Cardiology", List.of("Dr. A. Sharma (MD, DM)", "Dr. K. Raman (DM)"));
        deptDoctorMap.put("Orthopaedics", List.of("Dr. R. Verma (MS Ortho)", "Dr. P. Sundaram (DNB)"));
        deptDoctorMap.put("Neurology", List.of("Dr. S. Nair (DM Neuro)", "Dr. M. Joseph (MCh)"));
        deptDoctorMap.put("Paediatrics", List.of("Dr. L. Swaminathan (MD)", "Dr. V. Priya (DCH)"));

        cmbDepartment = new JComboBox<>(deptDoctorMap.keySet().toArray(new String[0]));
        cmbDoctor = new JComboBox<>();
        cmbTimeSlot = new JComboBox<>(new String[]{
                "09:00 AM - 09:30 AM", "09:30 AM - 10:00 AM",
                "10:00 AM - 10:30 AM", "11:00 AM - 11:30 AM",
                "02:00 PM - 02:30 PM", "03:00 PM - 03:30 PM"
        });

        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Form Container
        JPanel pnlCenter = new JPanel(new GridLayout(4, 2, 10, 10));
        pnlCenter.setBorder(BorderFactory.createTitledBorder("Appointment Booking Details"));
        pnlCenter.add(new JLabel("Patient National ID / RegNo:"));
        pnlCenter.add(txtPatientId);
        pnlCenter.add(new JLabel("Clinical Department:"));
        pnlCenter.add(cmbDepartment);
        pnlCenter.add(new JLabel("Consultant Doctor:"));
        pnlCenter.add(cmbDoctor);
        pnlCenter.add(new JLabel("Preferred Time Slot:"));
        pnlCenter.add(cmbTimeSlot);

        // Buttons
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlSouth.add(btnReset);
        pnlSouth.add(btnBook);

        // Status bar
        JPanel pnlStatus = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        pnlStatus.add(lblStatus);

        add(pnlCenter, BorderLayout.CENTER);
        add(pnlSouth, BorderLayout.SOUTH);
        add(pnlStatus, BorderLayout.NORTH);

        // Populate initial doctors
        updateDoctorList((String) cmbDepartment.getSelectedItem());

        // Event Handling: Department Change -> Dynamic Doctor Refresh
        cmbDepartment.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateDoctorList((String) e.getItem());
            }
        });

        // Booking Action
        btnBook.addActionListener(e -> bookAppointment());
        btnReset.addActionListener(e -> resetForm());

        // Window Closing Confirmation Event
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (!txtPatientId.getText().trim().isEmpty()) {
                    int ans = JOptionPane.showConfirmDialog(OutpatientDesk.this,
                            "An appointment booking is in progress. Are you sure you want to exit?",
                            "Confirm Exit", JOptionPane.YES_NO_OPTION);
                    if (ans == JOptionPane.YES_OPTION) {
                        dispose();
                        System.exit(0);
                    }
                } else {
                    dispose();
                    System.exit(0);
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    private void updateDoctorList(String department) {
        cmbDoctor.removeAllItems();
        List<String> doctors = deptDoctorMap.get(department);
        if (doctors != null) {
            for (String doc : doctors) {
                cmbDoctor.addItem(doc);
            }
        }
        lblStatus.setText("Selected Department: " + department + " (" + (doctors != null ? doctors.size() : 0) + " doctors available)");
    }

    private void bookAppointment() {
        String patientId = txtPatientId.getText().trim();
        if (patientId.isEmpty() || !patientId.matches("^[A-Z0-9]{6,12}$")) {
            lblStatus.setText("Validation Error: Patient ID must be 6-12 alphanumeric characters.");
            JOptionPane.showMessageDialog(this,
                    "Invalid Patient ID. Must be 6-12 uppercase alphanumeric characters (e.g. PAT10045).",
                    "Input Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String dept = (String) cmbDepartment.getSelectedItem();
        String doctor = (String) cmbDoctor.getSelectedItem();
        String slot = (String) cmbTimeSlot.getSelectedItem();
        String token = "APT-" + (1000 + new Random().nextInt(9000));

        lblStatus.setText("Appointment Confirmed! Token: " + token);
        JOptionPane.showMessageDialog(this,
                String.format("Appointment Successfully Booked!\nToken: %s\nPatient: %s\nDept: %s\nDoctor: %s\nSlot: %s",
                        token, patientId, dept, doctor, slot),
                "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
        resetForm();
    }

    private void resetForm() {
        txtPatientId.setText("");
        if (cmbDepartment.getItemCount() > 0) cmbDepartment.setSelectedIndex(0);
        lblStatus.setText("Status: Ready for next booking.");
    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("  TASK 2: HOSPITAL OUTPATIENT APPOINTMENT DESK (EVENT-DRIVEN)");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");
        OutpatientDesk desk = new OutpatientDesk();
        System.out.println("Initialized OutpatientDesk components successfully.");
        System.out.println("Available departments: " + desk.deptDoctorMap.keySet());
    }
}
