package raf.web.Domaci3.form;

import raf.web.Domaci3.model.PermissionsEnum;

import java.util.Collection;

public class PermissionsResponse {

    private final Collection<PermissionsEnum> permissionsEnumList;

    public PermissionsResponse(Collection<PermissionsEnum> permissionsEnumList) {
        this.permissionsEnumList = permissionsEnumList;
    }

    public Collection<PermissionsEnum> getPermissionsList() {
        return permissionsEnumList;
    }
}
