package hr.algebra.javafxinsurance.dto;

import java.io.Serializable;
import java.time.Year;
import java.util.List;

public class VehicleInfoDTO implements Serializable {
    private static final long serialVersionUID = 2L;

    private String plate;

    private String brand;

    private String model;

    private Year manufacturingYear;

    public VehicleInfoDTO(String plate, String brand, String model, Year yearParsed) {
        this.plate=plate;
        this.brand=brand;
        this.manufacturingYear=yearParsed;
        this.model=model;
    }

    public VehicleInfoDTO() {
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
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

    public Year getManufacturingYear() {
        return manufacturingYear;
    }

    public void setManufacturingYear(Year manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }

    @Override
    public String toString() {
        return getPlate();
    }
}
