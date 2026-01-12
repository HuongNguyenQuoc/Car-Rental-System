import java.sql.Date;
import java.time.LocalDate;

public class Rental {
    private int rentalId;
    private int vehicleId;
    private int customerId;
    private LocalDate renDate;
    private LocalDate returnDate;
    private double totalCost;
    private String rentalStatus;

    public Rental() {
    }

    public Rental(int rentalId, int vehicleId, int customerId, LocalDate renDate, LocalDate returnDate,
            double totalCost, String rentalStatus) {
        this.rentalId = rentalId;
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.renDate = renDate;
        this.returnDate = returnDate;
        this.totalCost = totalCost;
        this.rentalStatus = rentalStatus;
    }

    public LocalDate getRenDate() {
        return renDate;
    }

    public void setRenDate(LocalDate renDate) {
        this.renDate = renDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

}
