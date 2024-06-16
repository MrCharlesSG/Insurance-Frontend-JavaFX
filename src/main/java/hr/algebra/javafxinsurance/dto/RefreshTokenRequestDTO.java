package hr.algebra.javafxinsurance.dto;

import hr.algebra.javafxinsurance.model.Token;

public class RefreshTokenRequestDTO {
    private String token;

    public RefreshTokenRequestDTO(Token token) {
        this.token=token.getToken();
    }
    public RefreshTokenRequestDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
