import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ReturnScreen extends JDialog {

    private Service sv = new Service();
    private JTextField txtName, txtPhone, txtCarId;

    public ReturnScreen(Frame owner) {

        super(owner,"Return Vehicle", true);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Dimension dimension1 = new Dimension(400, 40);
        txtName = new JTextField("");
        txtName.setPreferredSize(dimension1);
        txtPhone = new JTextField("");
        txtPhone.setPreferredSize(dimension1);
        txtCarId = new JTextField("");
        txtCarId.setPreferredSize(dimension1);
        
        setLayout(new BorderLayout());
        JPanel middlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        middlePanel.add(new JLabel("Full Name"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        middlePanel.add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        middlePanel.add(new JLabel("Phone Number"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        middlePanel.add(txtPhone, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        middlePanel.add(new JLabel("License Number"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        middlePanel.add(txtCarId, gbc);

        add(middlePanel, BorderLayout.CENTER);

        JPanel downPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btReturn = new JButton("Return Vehicle");
        JButton btBack = new JButton("Back");
        Dimension btSize = new Dimension(200, 60);
        btReturn.setPreferredSize(btSize);
        btBack.setPreferredSize(btSize);
        downPanel.add(btReturn);
        downPanel.add(btBack);
        add(downPanel, BorderLayout.SOUTH);

        btReturn.addActionListener(e -> {
            handleReturnVehicle();
        });

        btBack.addActionListener(e -> {
            this.dispose();
        });
    }

    private void handleReturnVehicle() {
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String code = txtCarId.getText();
        if (name.isEmpty() || phone.isEmpty() || code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please check again your information...");
            return;
        }
        if (!phone.matches("\\d{10,}")) {
            JOptionPane.showMessageDialog(this, "Please check again your information...");
            return;
        }

        String receipt = sv.processReturnVehicle(name, phone, code);
        JOptionPane.showMessageDialog(this, receipt);
        txtName.setText("");
        txtPhone.setText("");
        txtCarId.setText("");
    }
}
