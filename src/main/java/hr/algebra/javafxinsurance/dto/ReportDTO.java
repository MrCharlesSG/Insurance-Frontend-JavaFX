package hr.algebra.javafxinsurance.dto;

import hr.algebra.javafxinsurance.model.ReportStatus;

import java.util.Date;

public class ReportDTO {

    private long id;
    private InfoReportDriverDTO infoReportDriverA;
    private InfoReportDriverDTO infoReportDriverB;
    private Date date;
    private String place;
    private String details;

    public ReportDTO(long id, InfoReportDriverDTO infoReportDriverA, InfoReportDriverDTO infoReportDriverB, Date date, String place, String details) {
        this.id = id;
        this.infoReportDriverA = infoReportDriverA;
        this.infoReportDriverB = infoReportDriverB;
        this.date = date;
        this.place = place;
        this.details = details;
    }

    public ReportDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public InfoReportDriverDTO getInfoReportDriverA() {
        return infoReportDriverA;
    }

    public void setInfoReportDriverA(InfoReportDriverDTO infoReportDriverA) {
        this.infoReportDriverA = infoReportDriverA;
    }

    public InfoReportDriverDTO getInfoReportDriverB() {
        return infoReportDriverB;
    }

    public void setInfoReportDriverB(InfoReportDriverDTO infoReportDriverB) {
        this.infoReportDriverB = infoReportDriverB;
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

    public ReportStatus getStatus(){
        return this.infoReportDriverB.getStatus();
    }
}
