package raf.web.Domaci3.response_request;

import raf.web.Domaci3.model.PermissionsEnum;

import javax.persistence.*;
import java.util.Collection;

public class UserRequest {

    private long userId;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private Collection<PermissionsEnum> permissions;

    public UserRequest(long userId,String firstname, String lastname, String email, String password, Collection<PermissionsEnum> permissions) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.permissions = permissions;
    }

    public UserRequest() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<PermissionsEnum> getPermissions() {
        return permissions;
    }

    public void setPermissions(Collection<PermissionsEnum> permissions) {
        this.permissions = permissions;
    }
}
