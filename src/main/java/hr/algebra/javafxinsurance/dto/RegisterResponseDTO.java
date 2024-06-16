package hr.algebra.javafxinsurance.dto;

import hr.algebra.javafxinsurance.model.Token;

public class RegisterResponseDTO {

    private Token token;
    private VehicleInfoDTO wrapped;

    public RegisterResponseDTO(Token token, VehicleInfoDTO wrapped) {
        this.token = token;
        this.wrapped = wrapped;
    }

    public RegisterResponseDTO() {
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public VehicleInfoDTO getWrapped() {
        return wrapped;
    }

    public void setWrapped(VehicleInfoDTO wrapped) {
        this.wrapped = wrapped;
    }
}

