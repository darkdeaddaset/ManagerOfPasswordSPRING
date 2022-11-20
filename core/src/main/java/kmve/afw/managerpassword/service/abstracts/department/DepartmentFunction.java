package kmve.afw.managerpassword.service.abstracts.department;

import com.github.fge.jsonpatch.JsonPatch;
import kmve.afw.managerpassword.dto.department.DepartmentDTO;
import kmve.afw.managerpassword.dto.department.DepartmentDTOFull;
import kmve.afw.managerpassword.model.User;
import org.springframework.http.ResponseEntity;

public interface DepartmentFunction {
    ResponseEntity<DepartmentDTOFull> getInfoAbstractDepartment(String name, User user);
    ResponseEntity<DepartmentDTOFull> createAbstractDepartment(DepartmentDTOFull departmentDTOFull);
    //ResponseEntity<DepartmentDTOFull> updateAbstractDepartment(DepartmentDTOFull departmentDTOFull);

    ResponseEntity<DepartmentDTOFull> updateAbstractDepartment(DepartmentDTOFull departmentDTOFull, String name, String login);
    ResponseEntity<DepartmentDTOFull> partialUpdateAbstractDepartment(String name, JsonPatch jsonPatch, String login);
    ResponseEntity<String> deleteAbstractDepartment(String name, User user);
}
