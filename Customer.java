public class Customer {
    private int customerId;
    private String fullName, phoneNumber, licenseNumber;

    public Customer(int customerId, String fullName, String phoneNumber, String licenseNumber) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
    }

    public Customer(String fullName, String phoneNumber, String licenseNumber) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
    }

    public Customer() {
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

}
