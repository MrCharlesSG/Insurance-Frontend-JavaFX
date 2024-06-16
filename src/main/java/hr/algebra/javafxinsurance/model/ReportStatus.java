package hr.algebra.javafxinsurance.model;

import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

public enum ReportStatus implements ReportStatusFilter, ReportStatusColor {
    ALL("All", "", ""),
    WAITING("Waiting", "/waiting", "#312e81"),
    REJECTED("Rejected", "/rejected", "#881337"),
    ACCEPTED("Accepted", "/accepted", "#064e3b");


    private final String text;
    private final String url;

    private final String color;

    ReportStatus(String text, String url, String color) {
        this.text = text;
        this.url = url;
        this.color=color;
    }

    public static List<ReportStatus> getAll(){
        return Arrays.asList(values());
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public Color getColor() {
        return Color.web(color);
    }
}
