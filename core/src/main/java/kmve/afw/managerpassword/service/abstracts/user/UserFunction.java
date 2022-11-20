package kmve.afw.managerpassword.service.abstracts.user;

import com.github.fge.jsonpatch.JsonPatch;
import kmve.afw.managerpassword.dto.user.UserDTO;
import kmve.afw.managerpassword.dto.user.UserDTOFull;
import org.springframework.http.ResponseEntity;

public interface UserFunction {
    ResponseEntity<UserDTO> getInfoAboutMe(String login);
    ResponseEntity<UserDTO> createNewUser(UserDTO userDTO);
    ResponseEntity<UserDTOFull> updateFullMeUser(String login, UserDTOFull userDTOFull);
    ResponseEntity<UserDTOFull> partialUpdateMeUser(String login, JsonPatch jsonPatch);
    ResponseEntity<String> deleteMeUser(String login);
}
