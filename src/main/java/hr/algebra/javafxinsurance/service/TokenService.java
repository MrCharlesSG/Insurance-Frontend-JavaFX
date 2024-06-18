package hr.algebra.javafxinsurance.service;

import hr.algebra.javafxinsurance.Main;
import hr.algebra.javafxinsurance.model.Token;
import hr.algebra.javafxinsurance.serialization.TokenSerializer;
import hr.algebra.javafxinsurance.serialization.exceptions.NonSerializableClassException;

import java.util.Optional;

public enum TokenService {
    INSTANCE;

    private Optional<Token> token = Optional.empty();

    public String getAccessToken() throws IllegalAccessException {
        if(token.isEmpty()){
            token = Optional.ofNullable(loadTokens());
            if(token.isEmpty() || token.get().isEmpty()){
                throw new IllegalAccessException("User Is not Authenticated");
            }
        }
        Token tokenNotNull = token.get();
        if(tokenNotNull.isRefreshTokenExpired() || token.get().isEmpty()){
            Main.showLoginScreen();
            throw new IllegalAccessException("User need an authentication, becouse sesion has expired");
        }
        if(tokenNotNull.isAccessTokenExpired()){
            // refresh token OK, access is KO
            AuthService.INSTANCE.refreshToken(tokenNotNull);
        }
        return tokenNotNull.getAccessToken();
    }

    public void initSession(Token response) throws NonSerializableClassException {
        token = Optional.of(response);
        token.get().initExpirations();
        saveTokens();
    }

    public boolean isAuthenticated() throws IllegalAccessException {
        token = Optional.ofNullable(loadTokens());
        if(token.isPresent() && !token.get().isEmpty() && !token.get().isRefreshTokenExpired()){
            try {
                AuthService.INSTANCE.refreshToken(token.get());
            } catch (IllegalAccessException e) {
                return false;
            }
            return !token.get().isAccessTokenExpired() && !token.get().isRefreshTokenExpired();
        }
        return false;
    }

    private void saveTokens() throws NonSerializableClassException {
        if(token.isPresent()) {
            new TokenSerializer().save(token.get());
        }
    }

    private Token loadTokens() throws IllegalAccessException {
        try {
            return new TokenSerializer().load();
        } catch (hr.algebra.javafxinsurance.serialization.exceptions.NonSerializableClassException e) {
            throw new IllegalAccessException("Someone tried to attack the files");
        }
    }

    public void refreshToken(Token tokenRefreshed) throws NonSerializableClassException {
        if (token.isPresent()){
            this.token.get().refreshToken(tokenRefreshed);
            saveTokens();
        }
    }

    public void invalidateSession() throws NonSerializableClassException {
        new TokenSerializer().save(new Token());
    }
}


