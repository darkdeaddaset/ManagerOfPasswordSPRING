package kmve.afw.managerpassword.service;

import kmve.afw.managerpassword.dto.user.UserDTO;
import kmve.afw.managerpassword.jwt.JwtProvider;
import kmve.afw.managerpassword.mapper.UserMapper;
import kmve.afw.managerpassword.model.User;
import kmve.afw.managerpassword.model.enums.Role;
import kmve.afw.managerpassword.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;

    @Override
    public ResponseEntity<String> signUp(UserDTO userDTO) {
        return Optional.of(userDTO)
                .map(userMapper::getFromDTO)
                .map(result -> {
                    result.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
                    result.setRoles(roleService.getRoleUser());
                    userRepository.save(result);
                    return new ResponseEntity<>("Пользователь создан", HttpStatus.OK);})
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не может быть создан"));
    }

    @Override
    public ResponseEntity<String> signIn(UserDTO userDTO) {
        final User user = userRepository.getUserByLogin(userDTO.getLogin())
                .orElseThrow(() -> new AuthenticationServiceException("Пароль не найден"));

        if (bCryptPasswordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            String accessToken = jwtProvider.generateAccessToken(user);
            return new ResponseEntity<>(accessToken, HttpStatus.OK);
        } else {
            throw new AuthenticationServiceException("Ошибка авторизации");
        }
    }
}
