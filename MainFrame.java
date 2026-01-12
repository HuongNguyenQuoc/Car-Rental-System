import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Car Rental System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,50, 150));
        Dimension dimension = new Dimension(150, 35);
        JButton rentalButton = new JButton("Rental Vehicle");
        rentalButton.setPreferredSize(dimension);
        JButton returnButton = new JButton("Return Vehicle");
        returnButton.setPreferredSize(dimension);
        jPanel.setBackground(Color.lightGray);
        jPanel.add(rentalButton);
        jPanel.add(returnButton);
        add(jPanel, BorderLayout.CENTER);

        rentalButton.addActionListener(e -> {
            RentalScreen rs = new RentalScreen(this);
            rs.setVisible(true);
        });
        returnButton.addActionListener(e -> {
            ReturnScreen rs = new ReturnScreen(this);
            rs.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
