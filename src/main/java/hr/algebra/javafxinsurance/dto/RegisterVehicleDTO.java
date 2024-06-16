package hr.algebra.javafxinsurance.dto;

import java.time.Year;

public class RegisterVehicleDTO {
    private String username;

    private String password;
    private String matchingPassword;

    private VehicleInfoDTO vehicle;

    public RegisterVehicleDTO(String plate, String password, String brand, String model, Year yearParsed) {
        this.username=plate;
        this.password=password;
        this.matchingPassword=password;
        this.vehicle= new VehicleInfoDTO(plate, brand, model, yearParsed);

    }

    public RegisterVehicleDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public VehicleInfoDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleInfoDTO vehicle) {
        this.vehicle = vehicle;
    }
}
