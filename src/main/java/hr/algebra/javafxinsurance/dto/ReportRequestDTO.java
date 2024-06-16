package hr.algebra.javafxinsurance.dto;

import java.util.Date;

public class ReportRequestDTO {
    private long driverAId;
    private long driverBId;
    private String vehicleB;
    private String damages;
    private Date date;
    private String place;
    private String details;

    public ReportRequestDTO(long driverAId, long driverBId, String vehicleB, String damages, Date date, String place, String details) {
        this.driverAId = driverAId;
        this.driverBId = driverBId;
        this.vehicleB = vehicleB;
        this.damages = damages;
        this.date = date;
        this.place = place;
        this.details = details;
    }

    public ReportRequestDTO() {
    }

    public long getDriverAId() {
        return driverAId;
    }

    public void setDriverAId(long driverAId) {
        this.driverAId = driverAId;
    }

    public long getDriverBId() {
        return driverBId;
    }

    public void setDriverBId(long driverBId) {
        this.driverBId = driverBId;
    }

    public String getVehicleB() {
        return vehicleB;
    }

    public void setVehicleB(String vehicleB) {
        this.vehicleB = vehicleB;
    }

    public String getDamages() {
        return damages;
    }

    public void setDamages(String damages) {
        this.damages = damages;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
