import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class RentalScreen extends JDialog {

    private JTextField txtName, txtPhone, txtLi, txtDays, txtTot;

    private JTable table1;
    private DefaultTableModel model;
    private JScrollPane jScrollPane;

    private DAO dao = new DAO();
    private Service service = new Service();
    ArrayList<Vehicle> list;

    private double totalCost;
    private int days;

    public RentalScreen(Frame owner) {

        super(owner, "Rent Vehicle", true);
        setSize(1000, 800);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(
                new String[] {"ID", "Brand", "Model", "Price/Day", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1 = new JTable(model);
        jScrollPane = new JScrollPane(table1);
        loadVehiclesOnTable(); // Upload data to the table

        JPanel upPanel = new JPanel(new BorderLayout());
        upPanel.setBorder(BorderFactory.createTitledBorder("List vehicle available"));
        upPanel.add(jScrollPane, BorderLayout.CENTER);
        add(upPanel, BorderLayout.NORTH);

        JPanel middlePanel = new JPanel(new GridLayout(5, 2, 4, 4));
        middlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        middlePanel.add(new JLabel("FullName"));
        txtName = new JTextField("");
        middlePanel.add(txtName);
        middlePanel.add(new JLabel("PhoneNumber"));
        txtPhone = new JTextField("");
        middlePanel.add(txtPhone);
        middlePanel.add(new JLabel("LicenseNumber"));
        txtLi = new JTextField("");
        middlePanel.add(txtLi);
        middlePanel.add(new JLabel("Number of days you wish to rent the car"));
        txtDays = new JTextField("");
        middlePanel.add(txtDays);
        middlePanel.add(new JLabel("TotalCost"));
        txtTot = new JTextField("");
        middlePanel.add(txtTot);
        add(middlePanel, BorderLayout.CENTER);

        JPanel downPanel = new JPanel();
        JButton btChoice = new JButton("Rent");
        JButton btBack = new JButton("Back");
        downPanel.add(btChoice);
        downPanel.add(btBack);
        add(downPanel, BorderLayout.SOUTH);
        setupAutoCalculator();
        btChoice.addActionListener(e -> {
            RentVehicle();
        });
        btBack.addActionListener(e -> {
            new MainFrame().setVisible(true);
            this.dispose();
        });
        table1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                txtDays.setText("");
                setupAutoCalculator();
            }
        });
        txtTot.setEditable(false);
        txtTot.setFocusable(false);
    }

    private void RentVehicle() {
        try {
            int selectRow = table1.getSelectedRow();
            if (selectRow == -1) {
                JOptionPane.showMessageDialog(this, "PLEASE ENTER ALL THE REQUIRED INFORMATION");
                return;
            }

            String fullName = txtName.getText().trim();
            String phone = txtPhone.getText().trim();
            String license = txtLi.getText().trim();

            if (fullName.isEmpty() || phone.isEmpty() || license.isEmpty()) {
                JOptionPane.showMessageDialog(this, "PLEASE ENTER ALL THE REQUIRED INFORMATION");
                return;
            }

            if (!phone.matches("\\d{10,}")) {
                JOptionPane.showMessageDialog(this, "PLEASE CHECK AGAIN YOUR PHONE");
                return;
            }

            int days = 0;
            try {
                days = Integer.parseInt(txtDays.getText().trim());
                if (days < 0) {
                    JOptionPane.showMessageDialog(this, "PLEASE CHECK AGAIN YOUR INFORMATION");
                    return;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "PLEASE CHECK NUMBER OF DAYS RENT YOUR VEHICLE");
                return;
            }

            Customer customer = new Customer(fullName, phone, license);
            service.filterCustomer(customer);
            service.rentVehicle(list.get(selectRow));
            service.addInforIntoDB(list.get(selectRow), customer, days, totalCost, "RENTED");
            JOptionPane.showMessageDialog(this, "RENTED");
            loadVehiclesOnTable();
            txtName.setText("");
            txtPhone.setText("");
            txtDays.setText("");
            txtLi.setText("");
            txtTot.setText("");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR SYSTEM...");
            return;
        }
    }

    private void loadVehiclesOnTable() {
        list = service.getAvailableVehicles();
        model.setRowCount(0);
        for (Vehicle vehicle : list) {
            model.addRow(new Object[] {
                    vehicle.getVehicleId(),
                    vehicle.getBrand(),
                    vehicle.getModel(),
                    vehicle.getPricePerDay() + " $",
                    vehicle.getStatus()
            });
        }
    }

    private void setupAutoCalculator() {
        txtDays.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateCost();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateCost();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateCost();
            }

        });
    }

    private void updateCost() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow == -1 || txtDays.getText().trim().isEmpty()) {
            txtTot.setText("0 $");
            return;
        }

        try {
            days = Integer.parseInt(txtDays.getText());
            if (days <= 0) {
                txtTot.setText("0 $");
                return;
            }
            Vehicle vehicle = list.get(selectedRow);
            totalCost = service.totalCost(vehicle, days);
            SwingUtilities.invokeLater(() -> {
                txtTot.setText("" + totalCost + " $");
            });
        } catch (Exception e) {
            System.out.println("PLEASE DOUBLE CHECK THE NUMBER OF DAYS YOU WISH TO RENT...");
            e.printStackTrace();
        }
    }

}
