package kmve.afw.managerpassword.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import kmve.afw.managerpassword.dto.department.DepartmentDTO;
import kmve.afw.managerpassword.dto.department.DepartmentDTOFull;
import kmve.afw.managerpassword.dto.record.RecordDTOWithUser;
import kmve.afw.managerpassword.dto.record.RecordInfoDTO;
import kmve.afw.managerpassword.dto.user.UserDTO;
import kmve.afw.managerpassword.dto.user.UserDTOFull;
import kmve.afw.managerpassword.mapper.UserMapper;
import kmve.afw.managerpassword.repository.UserRepository;
import kmve.afw.managerpassword.service.abstracts.user.AbstractUser;
import kmve.afw.managerpassword.utils.GeneratePassword;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractUser implements UserService {
    private final DepartmentUserService departmentUserService;
    private final RecordService recordService;
    public UserServiceImpl(UserRepository userRepository, GeneratePassword generatePassword, UserMapper userMapper, ObjectMapper objectMapper, DepartmentUserService departmentUserService, RecordService recordService) {
        super(userRepository, generatePassword, userMapper, objectMapper);
        this.departmentUserService = departmentUserService;
        this.recordService = recordService;
    }
    @Override
    public ResponseEntity<UserDTO> getInfo() {
        return getInfoAboutMe(getAuthenticationUser());
    }

    @Override
    public ResponseEntity<UserDTO> createUser(UserDTO userDTO) {
        return createNewUser(userDTO);
    }

    @Override
    public ResponseEntity<UserDTOFull> updateFull(UserDTOFull userDTOFull) {
        return updateFullMeUser(getAuthenticationUser(), userDTOFull);
    }

    @Override
    public ResponseEntity<UserDTOFull> partialUpdate(JsonPatch jsonPatch) {
        return partialUpdateMeUser(getAuthenticationUser(), jsonPatch);
    }

    @Override
    public ResponseEntity<String> delete() {
        return deleteMeUser(getAuthenticationUser());
    }

    @Override
    public ResponseEntity<DepartmentDTOFull> getInfoDepartment(String name) {
        return departmentUserService.getInfoUser(name, getAuthenticationUser());
    }

    @Override
    public ResponseEntity<DepartmentDTOFull> createDepartment(DepartmentDTO departmentDTO) {
        return departmentUserService.createDepartmentUser(departmentDTO, getAuthenticationUser());
    }

    @Override
    public ResponseEntity<DepartmentDTOFull> updateFullDepartment(DepartmentDTOFull departmentDTOFull, String name) {
        return departmentUserService.updateFullDepartmentUser(departmentDTOFull, name, getAuthenticationUser());
    }

    @Override
    public ResponseEntity<DepartmentDTOFull> partialUpdateDepartment(String name, JsonPatch jsonPatch) {
        return departmentUserService.partialUpdateDepartmentUser(name, jsonPatch, getAuthenticationUser());
    }

    @Override
    public ResponseEntity<String> deleteDepartment(String name) {
        return departmentUserService.deleteDepartmentUser(name, getAuthenticationUser());
    }

    @Override
    public ResponseEntity<RecordInfoDTO> getRecordUser(String name) {
        return recordService.getRecordUser(name, getAuthenticationUser());
    }

    @Override
    public ResponseEntity<RecordInfoDTO> createRecordUser(RecordInfoDTO recordInfoDTO) {
        return recordService.createRecordUser(getAuthenticationUser(), recordInfoDTO);
    }

    @Override
    public ResponseEntity<RecordDTOWithUser> updateRecordUser(String name, RecordDTOWithUser recordDTOWithUser) {
        return recordService.updateRecordUser(name, recordDTOWithUser, getAuthenticationUser());
    }

    @Override
    public ResponseEntity<RecordDTOWithUser> partialUpdateRecordUser(String name, JsonPatch jsonPatch) {
        return recordService.partialUpdateRecordUser(name, getAuthenticationUser(), jsonPatch);
    }

    @Override
    public ResponseEntity<String> deleteRecordUser(String name) {
        return recordService.deleteRecordUser(name, getAuthenticationUser());
    }
}
