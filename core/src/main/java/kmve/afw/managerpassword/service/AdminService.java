package kmve.afw.managerpassword.service;

import kmve.afw.managerpassword.dto.record.RecordDTOWithUser;
import kmve.afw.managerpassword.dto.record.RecordInfoDTO;
import kmve.afw.managerpassword.dto.user.UserDTO;
import kmve.afw.managerpassword.dto.user.UserDTOFull;
import kmve.afw.managerpassword.model.User;
import kmve.afw.managerpassword.model.enums.Role;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
    ResponseEntity<String> setRoleForUser(String login, String role);
    ResponseEntity<User> createUserForAdmin(UserDTO userDTO);
    List<UserDTO> getAllUserForAdmin();
    List<UserDTOFull> getAllUserFullForAdmin();
    List<RecordInfoDTO> getAllRecordsForAdmin();
    List<RecordDTOWithUser> getAllRecordsFullForAdmin();
    UserDTO getUserForAdmin(String login);
    UserDTOFull getUserFullForAdmin(String login);
    ResponseEntity<String> deleteDepartmentForAdmin(String name);
    ResponseEntity<String> deleteUserForAdmin(String login);
    ResponseEntity<String> deleteRecordForAdmin(String name);
}
