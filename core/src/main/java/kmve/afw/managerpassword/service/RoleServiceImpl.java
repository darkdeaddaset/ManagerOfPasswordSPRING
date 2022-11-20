package kmve.afw.managerpassword.service;

import kmve.afw.managerpassword.model.enums.Role;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{
    @Override
    public Set<Role> getRoleUser() {
        Set<Role> set = new HashSet<>();
        set.add(Role.USER);
        return set;
    }

    @Override
    public Set<Role> getRoleAdmin() {
        Set<Role> set = new HashSet<>();
        set.add(Role.ADMIN);
        return set;
    }
}
