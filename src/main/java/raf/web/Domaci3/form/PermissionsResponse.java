package raf.web.Domaci3.form;

import raf.web.Domaci3.model.Permissions;

import java.util.Collection;
import java.util.List;

public class PermissionsResponse {

    private final Collection<Permissions> permissionsList;

    public PermissionsResponse(Collection<Permissions> permissionsList) {
        this.permissionsList = permissionsList;
    }

    public Collection<Permissions> getPermissionsList() {
        return permissionsList;
    }
}
