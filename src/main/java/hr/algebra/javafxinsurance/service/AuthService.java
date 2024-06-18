package hr.algebra.javafxinsurance.service;

import com.google.gson.Gson;
import hr.algebra.javafxinsurance.dto.*;
import hr.algebra.javafxinsurance.model.Token;
import hr.algebra.javafxinsurance.serialization.VehicleSerializer;
import hr.algebra.javafxinsurance.serialization.exceptions.NonSerializableClassException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import java.util.List;

import static hr.algebra.javafxinsurance.configuration.ApiConfig.API_URL;

public enum AuthService {
    INSTANCE;
    private final String AUTH_API_URL = "/auth/api/v1/";
    private final String LOGIN_URL = API_URL+AUTH_API_URL+"/login";
    private final String LOGOUT_URL = API_URL+AUTH_API_URL+"/logout";
    private final String REGISTER_URL = API_URL+AUTH_API_URL+"/register/vehicle";
    private final String REFRESH_TOKEN_URL = API_URL+AUTH_API_URL+"/refreshToken";

    private VehicleInfoDTO authenticatedVehicle;

    public void authenticate(LoginRequestDTO loginRequestDTO) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequestDTO> requestEntity = new HttpEntity<>(loginRequestDTO, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Token> tokenResponseEntity = restTemplate.exchange(LOGIN_URL, HttpMethod.POST, requestEntity, Token.class);

        if (tokenResponseEntity.getStatusCode().is2xxSuccessful()) {
            TokenService.INSTANCE.initSession(tokenResponseEntity.getBody());
            this.authenticatedVehicle = VehicleService.INSTANCE.getVehicleByPlate(loginRequestDTO.getUsername());
            try{
                new VehicleSerializer().save(authenticatedVehicle);
            }catch (NonSerializableClassException e){
                throw new NonSerializableClassException("Someone try to attack files");
            }
        } else {
            throw new RuntimeException("Failed to authenticate: " + tokenResponseEntity.getBody());
        }
    }

    public VehicleInfoDTO getAuthenticatedVehicle() throws IllegalAccessException {
        if(authenticatedVehicle==null){
            try {
                authenticatedVehicle = new VehicleSerializer().load();
            } catch (NonSerializableClassException e) {
                throw new IllegalAccessException("Someone tried to attack files");
            }
        }
        return authenticatedVehicle;
    }

    public HttpHeaders getAuthHeader() throws IllegalAccessException {
        HttpHeaders headers = new HttpHeaders();
        String accessToken = TokenService.INSTANCE.getAccessToken();
        headers.set("Authorization", "Bearer "+ accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public void registerVehicle(RegisterVehicleDTO registerVehicleDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RegisterVehicleDTO> requestEntity = new HttpEntity<>(registerVehicleDTO, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RegisterResponseDTO> tokenResponseEntity = restTemplate.exchange(REGISTER_URL, HttpMethod.POST, requestEntity, RegisterResponseDTO.class);

        if (tokenResponseEntity.getStatusCode().is2xxSuccessful() && tokenResponseEntity.getBody()!=null) {
            try {
                TokenService.INSTANCE.initSession(tokenResponseEntity.getBody().getToken());
                this.authenticatedVehicle = tokenResponseEntity.getBody().getWrapped();
                new VehicleSerializer().save(authenticatedVehicle);
            } catch (hr.algebra.javafxinsurance.serialization.exceptions.NonSerializableClassException e) {
                throw new RuntimeException("Someone tried to attack files");
            }
        } else {
            throw new RuntimeException("Failed to authenticate: " + tokenResponseEntity.getBody());
        }
    }

    public void refreshToken(Token token) throws IllegalAccessException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RefreshTokenRequestDTO> requestEntity = new HttpEntity<>(new RefreshTokenRequestDTO(token), headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Token> tokenResponseEntity;
        try {
            tokenResponseEntity = restTemplate
                    .exchange(
                            REFRESH_TOKEN_URL,
                            HttpMethod.POST,
                            requestEntity,
                            Token.class
                    );
        }catch (RestClientException e){
            throw new IllegalAccessException(e.getMessage());
        }
        if(tokenResponseEntity.getStatusCode().is2xxSuccessful()){
            try {
                TokenService.INSTANCE.refreshToken(tokenResponseEntity.getBody());
            } catch (NonSerializableClassException e) {
                throw new IllegalAccessException("Someone tried to attack the files");
            }
        }else{
            throw new IllegalAccessException("Not good token");
        }
    }

    public void logOut() throws IllegalAccessException, NonSerializableClassException {
        HttpHeaders headers = getAuthHeader();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> responseEntity;
        try {
            responseEntity = restTemplate.exchange(LOGOUT_URL, HttpMethod.POST, entity, Void.class);
        } catch (RestClientException e) {
            throw new IllegalAccessException("Failed to log out: " + e.getMessage());
        }

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            TokenService.INSTANCE.invalidateSession();
            authenticatedVehicle = null;
            new VehicleSerializer().save(new VehicleInfoDTO());
        } else {
            throw new IllegalAccessException("Failed to log out: " + responseEntity.getStatusCode());
        }
    }
}
