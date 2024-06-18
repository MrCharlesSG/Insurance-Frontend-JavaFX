package hr.algebra.javafxinsurance.service;

import hr.algebra.javafxinsurance.dto.DriverDTO;
import hr.algebra.javafxinsurance.dto.VehicleInfoDTO;
import hr.algebra.javafxinsurance.utils.UIUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static hr.algebra.javafxinsurance.configuration.ApiConfig.API_URL;

public enum VehicleService {
    INSTANCE;

    private final String BASIC_URL = API_URL + "/vehicles";

    public VehicleInfoDTO getVehicleByPlate(String plate) throws IllegalAccessException {
        HttpEntity<String> entity = new HttpEntity<>(AuthService.INSTANCE.getAuthHeader());

        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<VehicleInfoDTO> responseType = new ParameterizedTypeReference<VehicleInfoDTO>() {};

        ResponseEntity<VehicleInfoDTO> responseEntity = restTemplate.exchange(
                BASIC_URL+"/byPlate?plate="+plate,
                HttpMethod.GET,
                entity,
                responseType
        );
        return responseEntity.getBody();
    }

    public VehicleInfoDTO getVehicleAndDriversByPlate(String plate) throws IllegalAccessException {
        HttpEntity<String> entity = new HttpEntity<>(AuthService.INSTANCE.getAuthHeader());

        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<VehicleInfoDTO> responseType = new ParameterizedTypeReference<VehicleInfoDTO>() {};

        ResponseEntity<VehicleInfoDTO> responseEntity = restTemplate.exchange(
                BASIC_URL+"/byPlate?plate="+plate,
                HttpMethod.GET,
                entity,
                responseType
        );
        return responseEntity.getBody();
    }
}
