package hr.algebra.javafxinsurance.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static hr.algebra.javafxinsurance.configuration.TokenConfig.EXPIRATION_TIME_ACCESS_TOKEN;
import static hr.algebra.javafxinsurance.configuration.TokenConfig.EXPIRATION_TIME_TOKEN;

public class Token implements Serializable {
    private static final long serialVersionUID = 1L;
    private String token;
    private String accessToken;
    private Instant accessTokenExpiry;
    private Instant refreshTokenExpiry;

    public Token(Map<String, Object> responseBody, Instant accessTokenExpiry, Instant refreshTokenExpiry) {
        token = (String) responseBody.get("token");
        accessToken = (String) responseBody.get("accessToken");
        this.accessTokenExpiry = accessTokenExpiry;
        this.refreshTokenExpiry = refreshTokenExpiry;
    }
    public Token(Map<String, Object> responseBody) {
        token = (String) responseBody.get("token");
        accessToken = (String) responseBody.get("accessToken");
    }

    public Token(){}

    public boolean isAccessTokenExpired(){
        return accessTokenExpiry.isBefore((new Date()).toInstant());
    }
    public boolean isRefreshTokenExpired(){
        return refreshTokenExpiry.isBefore((new Date()).toInstant());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void initExpirations() {
        this.refreshTokenExpiry = new Date(System.currentTimeMillis()+ EXPIRATION_TIME_TOKEN).toInstant();
        this.refreshToken();
    }

    public void refreshToken(Token tokenRefreshed){
        refreshToken();
        this.accessToken=tokenRefreshed.getAccessToken();
        //this.token=tokenRefreshed.getToken();
    }
    private void refreshToken(){
        this.accessTokenExpiry = new Date(System.currentTimeMillis()+ EXPIRATION_TIME_ACCESS_TOKEN).toInstant();
    }
}
