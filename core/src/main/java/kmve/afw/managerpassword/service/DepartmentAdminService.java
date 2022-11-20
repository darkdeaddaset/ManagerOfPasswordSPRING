package kmve.afw.managerpassword.service;

import com.github.fge.jsonpatch.JsonPatch;
import kmve.afw.managerpassword.dto.department.DepartmentDTO;
import kmve.afw.managerpassword.dto.department.DepartmentDTOFull;
import org.springframework.http.ResponseEntity;

public interface DepartmentAdminService {
    ResponseEntity<DepartmentDTO> getInfoDepartment(String name);
    ResponseEntity<DepartmentDTOFull> getInfoFullDepartment(String name);
    ResponseEntity<String> deleteDepartment(String name);
    ResponseEntity<DepartmentDTOFull> createDepartment(DepartmentDTO departmentDTO, String login);
    ResponseEntity<DepartmentDTOFull> updateFullDepartment(DepartmentDTOFull departmentDTOFull, String name, String login);
    ResponseEntity<DepartmentDTOFull> partialUpdateDepartment(String name, JsonPatch jsonPatch, String login);
}
