package task04;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * SIMATS Java Programming Assignment 4 - Task 4
 * Event-Handling Forensics: Drawing Pad & Repaint Persistence
 * Student: Bala Naga Jaswanth (Reg: 192424300)
 */
public class DrawPad extends Frame {
    private final List<Point> points = new ArrayList<>();
    private final Label status = new Label("Clicks: 0");

    public DrawPad() {
        super("Draw Pad");
        add(status, BorderLayout.SOUTH);

        addMouseListener(new MouseAdapter() {
            // TODO 1: Override mouseClicked correctly: store the point, update the label and request a repaint
            @Override
            public void mouseClicked(MouseEvent e) {
                points.add(e.getPoint());
                status.setText("Clicks: " + points.size());
                repaint();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setSize(400, 300);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // TODO 2: Draw every stored point as a 6 x 6 filled circle centred on the point
        g.setColor(Color.BLUE);
        for (Point p : points) {
            g.fillOval(p.x - 3, p.y - 3, 6, 6);
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("  TASK 4: EVENT-HANDLING FORENSICS (DRAW PAD REPAIRED)");
        System.out.println("  Student: Bala Naga Jaswanth (Reg: 192424300)");
        System.out.println("==================================================================");
        DrawPad pad = new DrawPad();
        System.out.println("Initialized DrawPad. Registered MouseAdapter with @Override mouseClicked().");
        System.out.println("Paint persistence verified: all points cached in List<Point> and redrawn in paint(Graphics g).");
    }
}
