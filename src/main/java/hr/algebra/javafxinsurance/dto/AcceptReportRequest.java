package hr.algebra.javafxinsurance.dto;

public class AcceptReportRequest {
    private String damages;

    public AcceptReportRequest(String damages) {
        this.damages = damages;
    }
    public AcceptReportRequest(ReportDTO reportDTO) {
        this.damages = reportDTO.getInfoReportDriverB().getDamages();
    }

    public AcceptReportRequest() {
    }

    public String getDamages() {
        return damages;
    }

    public void setDamages(String damages) {
        this.damages = damages;
    }
}
