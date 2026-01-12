public class Vehicle {
    private int vehicleId;
    private String brand, model;
    private double pricePerDay;
    private String status;

    public Vehicle(int vehicleId, String brand, String model, double pricePerDay, String status) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.status = status;
    }

    public Vehicle() {
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
