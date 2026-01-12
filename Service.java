import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Service {
    private DAO dao = new DAO();

    public void rentVehicle(Vehicle vehicle) {
        dao.updateStatus(vehicle.getVehicleId(), "RENTED");
    }

    public void returnVehicle(Vehicle vehicle) {
        dao.updateStatus(vehicle.getVehicleId(), "AVAILABLE");
    }

    public void filterCustomer(Customer customer) {
        dao.searchCustomer(customer);
    }

    public ArrayList<Vehicle> getAvailableVehicles() {
        ArrayList<Vehicle> allVeheicles = dao.getAllVehicles();
        ArrayList<Vehicle> availableVehicles = new ArrayList<>();
        for (Vehicle vehicles : allVeheicles) {
            if (vehicles.getStatus().equalsIgnoreCase("AVAILABLE")) {
                availableVehicles.add(vehicles);
            }
        }
        return availableVehicles;
    }

    public double totalCost(Vehicle vehicle, int days)  {
        if (days <= 0) return 0;
        else if (days >= 10) return vehicle.getPricePerDay() * days * 0.9;
        else if (days >= 20) return vehicle.getPricePerDay() * days * 0.7;
        else if (days >= 30) return vehicle.getPricePerDay() * days * 0.5;
        return vehicle.getPricePerDay() * days;
    }

    public void addInforIntoDB(Vehicle vehicle, Customer customer, int days, double total, String status) {
        LocalDate renDate = LocalDate.now();
        LocalDate returnDate = renDate.plusDays(days);
        dao.addInforRental(vehicle.getVehicleId(), customer.getCustomerId(), renDate, returnDate, total, status);
    }

    public String processReturnVehicle(String name, String phone, String code) {
        Customer customer = dao.getCustomerSpecifically(code);
        if (customer == null) {return "DOES NOT CUSTOMER EXIST!";}
        Rental rental = dao.findActiveRentalByCustomer(customer.getLicenseNumber());
        if (rental == null) {
            return "THE SYSTEM HAS RECEIVED THE VEHICLE";
        }

        //2. Calculate the overdue days
        LocalDate today = LocalDate.now();
        LocalDate scheduledReturn = rental.getReturnDate();
        long daysLate = ChronoUnit.DAYS.between(scheduledReturn, today);
        double penalty = 0;
        double finalTotal = rental.getTotalCost();

        StringBuilder sb = new StringBuilder("Vehicle returned successfully!\n");
        if (daysLate > 0) {
            penalty = daysLate * 70;
            finalTotal += penalty;
            sb.append("Customer returned late ").append(daysLate).append(" days\n");
            sb.append("Penalty fee: ").append(String.format("%,.0f", penalty)).append(" $\n");

        } else {
            sb.append("Status: Returned on time.\n ");
        }
        sb.append("Total Final Payment: ").append(String.format("%,.0f", finalTotal)).append(" $");

        try {
            if (rental.getRentalStatus().equals("AVAILABLE")) {return "The vehicle has been returned";}
                dao.UpdateRentalAfterReturn(rental.getRentalId(), finalTotal);
                dao.updateStatus(rental.getVehicleId(), "AVAILABLE");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
