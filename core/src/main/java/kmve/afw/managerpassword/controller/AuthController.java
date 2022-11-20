package kmve.afw.managerpassword.controller;

import kmve.afw.managerpassword.dto.user.UserDTO;
import kmve.afw.managerpassword.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    ResponseEntity<String> signUp(@RequestBody UserDTO userDTO) {
        return authService.signUp(userDTO);
    }

    @PostMapping("/sign-in")
    ResponseEntity<String> signIn(@RequestBody UserDTO userDTO) {
        return authService.signIn(userDTO);
    }
}
