package ar.com.webapp.ticketing.rest.resources;

import ar.com.webapp.ticketing.core.model.entities.User;
import org.springframework.hateoas.ResourceSupport;

public class UserResource extends ResourceSupport {

    private Long rid;
    private String userName;
    private String name;
    private String password;
    private String profile;
    private String area;
    private String email;
    private String status;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
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

    public User toUser() {
        return new User(rid, userName, name,password, profile, area, email,status);
    }
}
