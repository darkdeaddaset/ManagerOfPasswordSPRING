package kmve.afw.managerpassword.service;

import kmve.afw.managerpassword.dto.user.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<String> signIn(UserDTO userDTO);
    ResponseEntity<String> signUp(UserDTO userDTO);
}
