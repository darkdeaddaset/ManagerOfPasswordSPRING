package kmve.afw.managerpassword.service;

import com.github.fge.jsonpatch.JsonPatch;
import kmve.afw.managerpassword.dto.department.DepartmentDTO;
import kmve.afw.managerpassword.dto.department.DepartmentDTOFull;
import kmve.afw.managerpassword.service.abstracts.department.DepartmentFunction;
import org.springframework.http.ResponseEntity;

public interface DepartmentUserService extends DepartmentFunction {
    ResponseEntity<DepartmentDTOFull> getInfoUser(String name, String login);
    ResponseEntity<DepartmentDTOFull> createDepartmentUser(DepartmentDTO departmentDTO, String login);
    ResponseEntity<DepartmentDTOFull> updateFullDepartmentUser(DepartmentDTOFull departmentDTOFull, String name, String login);
    ResponseEntity<DepartmentDTOFull> partialUpdateDepartmentUser(String name, JsonPatch jsonPatch, String login);
    ResponseEntity<String> deleteDepartmentUser(String name, String login);
}
