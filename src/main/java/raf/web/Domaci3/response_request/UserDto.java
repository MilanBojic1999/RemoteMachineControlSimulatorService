package raf.web.Domaci3.response_request;


import raf.web.Domaci3.model.PermissionsEnum;

import java.util.Collection;

public class UserDto {

    private Long userId;

    private String firstname;

    private String lastname;

    private String email;

    private Collection<PermissionsEnum> permissions;


    public UserDto(Long userId, String firstname, String lastname, String email,Collection<PermissionsEnum> permissions) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.permissions = permissions;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public Collection<PermissionsEnum> getPermissions() {
        return permissions;
    }

    public void setPermissions(Collection<PermissionsEnum> permissions) {
        this.permissions = permissions;
    }
}
