package hr.algebra.javafxinsurance.dto;

public class LoginRequestDTO {

    private String username;
    private String password;
    public LoginRequestDTO(String plate, String password) {
        this.password = password;
        this.username=plate;
    }

    public LoginRequestDTO() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
