package hr.algebra.javafxinsurance.service;

import hr.algebra.javafxinsurance.dto.AcceptReportRequest;
import hr.algebra.javafxinsurance.dto.ReportDTO;
import hr.algebra.javafxinsurance.dto.ReportRequestDTO;
import hr.algebra.javafxinsurance.model.ReportStatus;
import hr.algebra.javafxinsurance.model.ReportStatusFilter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static hr.algebra.javafxinsurance.configuration.ApiConfig.API_URL;

public enum ReportService {
    INSTANCE;

    private final String BASIC_URL = API_URL + "/report";

    public List<ReportDTO> getReportsFiltered(ReportStatusFilter filter) throws IllegalAccessException {
        HttpEntity<String> entity = new HttpEntity<>(AuthService.INSTANCE.getAuthHeader());

        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<ReportDTO>> responseType = new ParameterizedTypeReference<List<ReportDTO>>() {};

        ResponseEntity<List<ReportDTO>> responseEntity = restTemplate.exchange(
                BASIC_URL+filter.getUrl(),
                HttpMethod.GET,
                entity,
                responseType
        );
        return responseEntity.getBody();
    }
    public void openReport(ReportRequestDTO reportRequestDTO) throws IllegalAccessException {
        HttpEntity<ReportRequestDTO> entity = new HttpEntity<>(reportRequestDTO,AuthService.INSTANCE.getAuthHeader());

        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<ReportDTO> responseType = new ParameterizedTypeReference<ReportDTO>() {};

        ResponseEntity<ReportDTO> response = restTemplate.exchange(
                BASIC_URL,
                HttpMethod.POST,
                entity,
                responseType
        );
        if (response.getStatusCode().isError()) {
            throw new IllegalArgumentException("Failed to open report");
        }
    }

    public void acceptReport(ReportDTO report) throws IllegalArgumentException, IllegalAccessException {
        if(report!=null){
            HttpEntity<AcceptReportRequest> entity = new HttpEntity<>(new AcceptReportRequest(report),AuthService.INSTANCE.getAuthHeader());

            RestTemplate restTemplate = new RestTemplate();
            String s = BASIC_URL + "/accept/" + report.getId();
            ResponseEntity<Void> response = restTemplate.exchange(
                    s,
                    HttpMethod.DELETE,
                    entity,
                    Void.class
            );

            if (response.getStatusCode().isError()) {
                throw new IllegalArgumentException("Failed to accept: " + report.getId());
            }
        }
    }

    public void rejectReport(ReportDTO report) throws IllegalArgumentException, IllegalAccessException {
        if(report!=null){
            HttpEntity<String> entity = new HttpEntity<>(AuthService.INSTANCE.getAuthHeader());

            RestTemplate restTemplate = new RestTemplate();
            String s = BASIC_URL + "/reject/" + report.getId();
            ResponseEntity<Void> response = restTemplate.exchange(
                    s,
                    HttpMethod.DELETE,
                    entity,
                    Void.class
            );

            if (response.getStatusCode().isError()) {
                throw new IllegalArgumentException("Failed to accept: " + report.getId());
            }
        }
    }
}
