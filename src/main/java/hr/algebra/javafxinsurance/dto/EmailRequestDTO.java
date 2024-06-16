package hr.algebra.javafxinsurance.dto;


public class EmailRequestDTO {

    private String email;

    public EmailRequestDTO(String email) {
        this.email = email;
    }

    public EmailRequestDTO(){}

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
