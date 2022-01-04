package raf.web.Domaci3.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

public enum PermissionsEnum implements GrantedAuthority {
    CAN_READ_USERS,CAN_CREATE_USERS,CAN_UPDATE_USERS,CAN_DELETE_USERS,
    CAN_SEARCH_MACHINE, CAN_START_MACHINE,CAN_STOP_MACHINE,
    CAN_RESTART_MACHINE, CAN_CREATE_MACHINE, CAN_DESTROY_MACHINE;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
