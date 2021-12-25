package raf.web.Domaci3.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

public enum PermissionsEnum implements GrantedAuthority {
    CAN_READ_USERS,CAN_CREATE_USERS,CAN_UPDATE_USERS,CAN_DELETE_USERS;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
