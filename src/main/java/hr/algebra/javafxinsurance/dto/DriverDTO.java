package hr.algebra.javafxinsurance.dto;

import java.util.Date;

public class DriverDTO {
    private long id;
    private String name;

    private String surnames;

    private String passport;

    private String email;

    private Date birthday;

    public DriverDTO(long id, String name, String surnames, String passport, String email, Date birthday) {
        this.id = id;
        this.name = name;
        this.surnames = surnames;
        this.passport = passport;
        this.email = email;
        this.birthday = birthday;
    }

    public DriverDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return name + " " + surnames;
    }
}
