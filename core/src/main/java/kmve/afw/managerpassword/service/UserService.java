package kmve.afw.managerpassword.service;

import com.github.fge.jsonpatch.JsonPatch;
import kmve.afw.managerpassword.dto.department.DepartmentDTO;
import kmve.afw.managerpassword.dto.department.DepartmentDTOFull;
import kmve.afw.managerpassword.dto.record.RecordDTOWithUser;
import kmve.afw.managerpassword.dto.record.RecordInfoDTO;
import kmve.afw.managerpassword.dto.user.UserDTO;
import kmve.afw.managerpassword.dto.user.UserDTOFull;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<UserDTO> getInfo();
    ResponseEntity<UserDTO> createUser(UserDTO userDTO);
    ResponseEntity<UserDTOFull> updateFull(UserDTOFull userDTOFull);
    ResponseEntity<UserDTOFull> partialUpdate(JsonPatch jsonPatch);
    ResponseEntity<String> delete();
    ResponseEntity<DepartmentDTOFull> getInfoDepartment(String name);
    ResponseEntity<DepartmentDTOFull> createDepartment(DepartmentDTO departmentDTO);
    ResponseEntity<DepartmentDTOFull> updateFullDepartment(DepartmentDTOFull departmentDTOFull, String name);
    ResponseEntity<DepartmentDTOFull> partialUpdateDepartment(String name, JsonPatch jsonPatch);
    ResponseEntity<String> deleteDepartment(String name);
    ResponseEntity<RecordInfoDTO> getRecordUser(String name);
    ResponseEntity<RecordInfoDTO> createRecordUser(RecordInfoDTO recordInfoDTO);
    ResponseEntity<RecordDTOWithUser> updateRecordUser(String name, RecordDTOWithUser recordDTOWithUser);
    ResponseEntity<RecordDTOWithUser> partialUpdateRecordUser(String name, JsonPatch jsonPatch);
    ResponseEntity<String> deleteRecordUser(String name);
}
