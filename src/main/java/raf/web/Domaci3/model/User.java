package raf.web.Domaci3.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "my_user")
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
    @ElementCollection(targetClass = PermissionsEnum.class, fetch = FetchType.EAGER)
    @JoinTable(name = "permissions", joinColumns = @JoinColumn(name = "user_id"))
    private Collection<PermissionsEnum> permissionsEnumList;


    public Long getUserId() {
        return userId;
    }

    public User() {
        permissionsEnumList = new ArrayList<>();
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

    public Collection<PermissionsEnum> getPermissionsList() {
        return permissionsEnumList;
    }

    public void setPermissionsList(List<PermissionsEnum> permissionsEnumList) {
        this.permissionsEnumList = permissionsEnumList;
    }

    public boolean addPermission(PermissionsEnum permissionsEnum){
        return permissionsEnumList.add(permissionsEnum);
    }

    public boolean removePermission(PermissionsEnum permissionsEnum){
        return permissionsEnumList.remove(permissionsEnum);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", permissionsEnumList=" + permissionsEnumList +
                '}';
    }
}
