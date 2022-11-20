package kmve.afw.managerpassword.repository;

import kmve.afw.managerpassword.model.Department;
import kmve.afw.managerpassword.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> getDepartmentByName(String name);
    Optional<Department> getDepartmentByNameAndUserAdmin(String name, User userAdmin);
    Optional<Department> getDepartmentByNameAndPassword(String name, String password);
    Optional<Department> getDepartmentByNameAndPasswordAndUserAdmin(String name, String password, User userAdmin);
}
