
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

public class DAO {

    public static ArrayList<Vehicle> getAllVehicles() {
        ArrayList<Vehicle> list = new ArrayList<>();
        String sql = "SELECT * FROM Vehicle";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleId(rs.getInt("vehicle_id"));
                vehicle.setBrand(rs.getString("brand"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setPricePerDay(rs.getDouble("price_per_day"));
                vehicle.setStatus(rs.getString("status"));
                list.add(vehicle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateStatus(int vehicleId, String status) {
        String sql = "UPDATE Vehicle SET status = ? WHERE vehicle_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, vehicleId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM Customer";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer x = new Customer();
                x.setCustomerId(rs.getInt("customer_id"));
                x.setFullName(rs.getString("full_name"));
                x.setPhoneNumber(rs.getString("phone_number"));
                x.setLicenseNumber(rs.getString("license_number"));
                list.add(x);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addCustomer(Customer c) {
        String sql = "INSERT INTO Customer (full_name, phone_number, license_number) VALUES(?, ?, ?);";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getFullName());
            ps.setString(2, c.getPhoneNumber());
            ps.setString(3, c.getLicenseNumber());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int realId = rs.getInt(1);
                    c.setCustomerId(realId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchCustomer(Customer c) {
        String sql = "SELECT customer_id FROM Customer WHERE license_number = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getLicenseNumber());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int realId = rs.getInt("customer_id");
                c.setCustomerId(realId);
                return;
            }
            addCustomer(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Rental> getAllRental() {
        ArrayList<Rental> list = new ArrayList<>();
        String sql = "SELECT * FROM Rental";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Rental rental = new Rental();
                rental.setRentalId(rs.getInt("rental_id"));
                rental.setVehicleId(rs.getInt("vehicle_id"));
                rental.setCustomerId(rs.getInt("customer_id"));
                rental.setRenDate(rs.getDate("rent_date").toLocalDate());
                rental.setReturnDate(rs.getDate("return_date").toLocalDate());
                rental.setTotalCost(rs.getDouble("total_cost"));
                rental.setRentalStatus(rs.getString("rental_status"));
                list.add(rental);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }

    public static void addInforRental(int vehicleId, int customerId, LocalDate rentDate, LocalDate returnDate,
            double ttCost, String status) {
        String sql = "INSERT INTO Rental (vehicle_id, customer_id, rent_date, return_date, total_cost, rental_status) VALUES(?, ?, ?, ?, ?, ?);";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, vehicleId);
            ps.setInt(2, customerId);
            ps.setDate(3, java.sql.Date.valueOf(rentDate));
            ps.setDate(4, java.sql.Date.valueOf(returnDate));
            ps.setDouble(5, ttCost);
            ps.setString(6, status);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Rental findActiveRentalByCustomer(String code) {
        String sql = "SELECT * FROM Rental r " +
                "JOIN Customer c ON c.customer_id = r.customer_id " +
                "WHERE c.license_number = ? AND r.rental_status = 'RENTED'";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Rental rental = new Rental();
                rental.setRentalId(rs.getInt("rental_id"));
                rental.setVehicleId(rs.getInt("vehicle_id"));
                rental.setCustomerId(rs.getInt("customer_id"));
                rental.setRenDate(rs.getDate("rent_date").toLocalDate());
                rental.setReturnDate(rs.getDate("return_date").toLocalDate());
                rental.setTotalCost(rs.getDouble("total_cost"));
                rental.setRentalStatus(rs.getString("rental_status"));
                return rental;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No data available for processing...");
        }
        return null;
    }

    public void UpdateRentalAfterReturn(int rentalId, double finalCost) {
        String sql = "UPDATE Rental SET rental_status = 'RETURNED', total_cost = ? WHERE rental_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, finalCost);
            ps.setInt(2, rentalId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't update data...");
        }
    }

    public Customer getCustomerSpecifically(String code) {
        String sql = "SELECT * FROM Customer WHERE license_number = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setFullName(rs.getString("full_name"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setLicenseNumber(rs.getString("license_number"));
                return customer;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Errol because no find out information customer...");
        }
        return null;
    }

}
