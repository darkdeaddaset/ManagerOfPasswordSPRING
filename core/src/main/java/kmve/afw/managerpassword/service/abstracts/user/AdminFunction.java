package kmve.afw.managerpassword.service.abstracts.user;

import kmve.afw.managerpassword.dto.record.RecordDTOWithUser;
import kmve.afw.managerpassword.dto.record.RecordInfoDTO;
import kmve.afw.managerpassword.dto.user.UserDTO;
import kmve.afw.managerpassword.dto.user.UserDTOFull;
import kmve.afw.managerpassword.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminFunction extends UserFunction {
    ResponseEntity<User> createUser(UserDTO userDTO);
    List<UserDTO> getAllUser();
    List<UserDTOFull> getAllUserFull();
    List<RecordInfoDTO> getAllRecordForAdmin();
    List<RecordDTOWithUser> getAllRecordFullForAdmin();
    UserDTO getUser(String login);
    UserDTOFull getUserFull(String login);
    ResponseEntity<String> deleteUser(String login);
    ResponseEntity<String> deleteDepartment(String name);
    ResponseEntity<String> deleteRecord(String name);
}
