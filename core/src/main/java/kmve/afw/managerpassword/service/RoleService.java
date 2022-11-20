package kmve.afw.managerpassword.service;

import kmve.afw.managerpassword.model.enums.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getRoleUser();
    Set<Role> getRoleAdmin();
}
