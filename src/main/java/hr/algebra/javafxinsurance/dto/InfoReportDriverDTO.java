package hr.algebra.javafxinsurance.dto;

import hr.algebra.javafxinsurance.model.ReportStatus;

public class InfoReportDriverDTO {

    private long id;
    private VehicleInfoDTO vehicle;
    private DriverDTO driver;
    private String damages;
    private ReportStatus status;

    public InfoReportDriverDTO(long id, VehicleInfoDTO vehicle, DriverDTO driver, String damages, ReportStatus status) {
        this.id = id;
        this.vehicle = vehicle;
        this.driver = driver;
        this.damages = damages;
        this.status = status;
    }

    public InfoReportDriverDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public VehicleInfoDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleInfoDTO vehicle) {
        this.vehicle = vehicle;
    }

    public DriverDTO getDriver() {
        return driver;
    }

    public void setDriver(DriverDTO driver) {
        this.driver = driver;
    }

    public String getDamages() {
        return damages;
    }

    public void setDamages(String damages) {
        this.damages = damages;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(String status) {
        ReportStatus reportStatus = ReportStatus.valueOf(status);

        this.status = reportStatus;
    }


}
