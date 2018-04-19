package ar.com.webapp.ticketing.core.model.entities;

import ar.com.webapp.ticketing.core.validator.model.annotation.AcceptedStringValidation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table
public class User {

    @Id @GeneratedValue
    private Long id;
    @NotBlank
    @Column(unique=true)
    private String userName;
    @NotBlank
    @Size(max=25)
    @Column
    private String name;
    @NotBlank
    @Column
    private String password;
    @NotBlank
    @Column
    @AcceptedStringValidation(acceptedValues={"ADMINISTRATOR","ANALYST","USER"}, message ="Invalid user profile")
    private String profile;
    @NotBlank
    @Column
    private String area;
    @NotBlank
    @Column
    private String email;
    @NotBlank
    @Column
    @AcceptedStringValidation(acceptedValues={"ACTIVE","INACTIVE"}, message ="Invalid user status")
    private String status;

    public User(@NotBlank String userName, @NotBlank @Size(max = 25) String name, @NotBlank String password, @NotBlank String profile, @NotBlank String area, @NotBlank String email) {
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.profile = profile;
        this.area = area;
        this.email = email;
    }

    public User(Long id, @NotBlank String userName, @NotBlank @Size(max = 25) String name, @NotBlank String password, @NotBlank String profile, @NotBlank String area, @NotBlank String email, @NotBlank String status) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.profile = profile;
        this.area = area;
        this.email = email;
        this.status = status;
    }

    public User(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", profile='" + profile + '\'' +
                ", area='" + area + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

