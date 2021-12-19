package raf.web.Domaci3.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Collection<Permissions> permissionsList;


    public Long getUserId() {
        return userId;
    }

    public User() {
        permissionsList = new HashSet();
    }

    public User(String firstname, String lastname, String email, String password) {
        this();
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Permissions> getPermissionsList() {
        return permissionsList;
    }

    public void setPermissionsList(Collection<Permissions> permissionsList) {
        this.permissionsList = permissionsList;
    }

    public boolean addPermission(Permissions permissions){
        return permissionsList.add(permissions);
    }

    public boolean removePermission(Permissions permissions){
        return permissionsList.remove(permissions);
    }
}
