package hr.algebra.javafxinsurance.service;

import hr.algebra.javafxinsurance.dto.DriverDTO;
import hr.algebra.javafxinsurance.dto.EmailRequestDTO;
import org.springframework.http.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static hr.algebra.javafxinsurance.configuration.ApiConfig.API_URL;

public enum DriverService {
    INSTANCE;

    private final String BASIC_URL = API_URL + "/driver";
    private final String GET_BY_EMAIL_URL = BASIC_URL+"/byEmail" + "?email=";
    private final String ASSOCIATE_BY_EMAIL_URL = BASIC_URL+"/associate";
    private final String DISASSOCIATE_BY_EMAIL_URL = BASIC_URL+"/disassociate";

    public List<DriverDTO> getDrivers() throws IllegalAccessException {
        HttpEntity<String> entity = new HttpEntity<>(AuthService.INSTANCE.getAuthHeader());

        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<DriverDTO>> responseType = new ParameterizedTypeReference<List<DriverDTO>>() {};

        ResponseEntity<List<DriverDTO>> responseEntity = restTemplate.exchange(
                BASIC_URL,
                HttpMethod.GET,
                entity,
                responseType
        );
        return responseEntity.getBody();
    }

    public List<DriverDTO> getDriversByVehicle(String plate) throws IllegalAccessException {
        HttpEntity<String> entity = new HttpEntity<>(AuthService.INSTANCE.getAuthHeader());

        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<DriverDTO>> responseType = new ParameterizedTypeReference<List<DriverDTO>>() {};

        ResponseEntity<List<DriverDTO>> responseEntity = restTemplate.exchange(
                BASIC_URL+"/byVehicle?plate="+plate,
                HttpMethod.GET,
                entity,
                responseType
        );
        return responseEntity.getBody();
    }

    

    public DriverDTO getDriverByEmail(String email) throws IllegalAccessException {
        HttpEntity<String> entity = new HttpEntity<>(AuthService.INSTANCE.getAuthHeader());

        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<DriverDTO> responseType = new ParameterizedTypeReference<>() {};

        ResponseEntity<DriverDTO> responseEntity = restTemplate.exchange(
                GET_BY_EMAIL_URL + email,
                HttpMethod.GET,
                entity,
                responseType
        );
        return responseEntity.getBody();
    }

    public void createDriverAndAssociate(DriverDTO driver) throws Exception {

        HttpEntity<DriverDTO> requestEntity = new HttpEntity<>(driver, AuthService.INSTANCE.getAuthHeader());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DriverDTO> response = restTemplate.exchange(
                BASIC_URL,
                HttpMethod.POST,
                requestEntity,
                DriverDTO.class);

        if (response.getStatusCode().isError()) {
            throw new Exception("Failed to create: " + response.getBody());
        }
    }

    public void associateDriver(String email) throws Exception {
        HttpEntity<EmailRequestDTO> requestEntity = new HttpEntity<>(new EmailRequestDTO(email), AuthService.INSTANCE.getAuthHeader());

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<DriverDTO> response = restTemplate.exchange(
                ASSOCIATE_BY_EMAIL_URL,
                HttpMethod.POST,
                requestEntity,
                DriverDTO.class
        );

        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Failed to associate driver: " + response.getBody());
        }
    }

    public void disassociateDriver(String email) throws Exception {
        HttpEntity<EmailRequestDTO> requestEntity = new HttpEntity<>(new EmailRequestDTO(email), AuthService.INSTANCE.getAuthHeader());

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<DriverDTO> response = restTemplate.exchange(
                DISASSOCIATE_BY_EMAIL_URL,
                HttpMethod.DELETE,
                requestEntity,
                DriverDTO.class
        );

        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Failed to disassociate driver: " + response.getBody());
        }
    }


}
