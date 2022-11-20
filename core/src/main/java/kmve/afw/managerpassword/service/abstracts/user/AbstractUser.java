package kmve.afw.managerpassword.service.abstracts.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import kmve.afw.managerpassword.dto.user.UserDTO;
import kmve.afw.managerpassword.dto.user.UserDTOFull;
import kmve.afw.managerpassword.mapper.UserMapper;
import kmve.afw.managerpassword.model.User;
import kmve.afw.managerpassword.repository.UserRepository;
import kmve.afw.managerpassword.utils.GeneratePassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
public abstract class AbstractUser implements UserFunction {
    protected final UserRepository userRepository;
    protected final GeneratePassword generatePassword;
    protected final UserMapper userMapper;
    protected final ObjectMapper objectMapper;

    protected AbstractUser(UserRepository userRepository,
                           GeneratePassword generatePassword,
                           UserMapper userMapper,
                           ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.generatePassword = generatePassword;
        this.userMapper = userMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseEntity<UserDTO> getInfoAboutMe(String login) {
        return userRepository.getUserByLogin(login)
                .map(userMapper::getFromModel)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Информация недоступна"));
    }

    @Override
    public ResponseEntity<UserDTO> createNewUser(UserDTO userDTO) {
        return Optional.of(userDTO)
                .map(userMapper::getFromDTO)
                .map(create -> {
                    if (userDTO.getPassword().isEmpty() || userDTO.getPassword().isBlank()) {
                        userDTO.setPassword(generatePassword.generatePassword(32, true, true));
                    }
                    userRepository.save(create);
                    UserDTO userDTO1 = userMapper.getFromModel(create);
                    return new ResponseEntity<>(userDTO1, HttpStatus.OK);
                }).orElseThrow(() -> new EntityNotFoundException("При создании аккаунта произошла ошибка"));
    }

    @Override
    public ResponseEntity<UserDTOFull> updateFullMeUser(String login,
                                                        UserDTOFull userDTOFull) {
        return userRepository.getUserByLogin(login)
                .map(updateFull -> {
                    if (userDTOFull.getPassword().isBlank() || userDTOFull.getPassword().isEmpty()) {
                        userDTOFull.setPassword(generatePassword.generatePassword(32, true, true));
                    }
                    userMapper.getFromModelForFull(userDTOFull, updateFull);
                    userRepository.save(updateFull);
                    UserDTOFull userDTOFull1 = userMapper.getFromModelForFull(updateFull);
                    return new ResponseEntity<>(userDTOFull1, HttpStatus.OK);
                }).orElseThrow(() -> new EntityNotFoundException("Невозможно обновить данные"));
    }

    @Override
    public ResponseEntity<UserDTOFull> partialUpdateMeUser(String login, JsonPatch jsonPatch) {
        UserDTOFull userDTOFull = userRepository.getUserByLogin(login)
                .map(userMapper::getFromModelForFull)
                .orElseThrow(() -> new EntityNotFoundException("Невозможно обновить выборочно данные пользователя"));
        UserDTOFull userDTOFull1;

        try {
            userDTOFull1 = applyPatchUser(jsonPatch, userDTOFull);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (JsonPatchException e) {
            throw new RuntimeException(e);
        }
        return updateFullMeUser(login, userDTOFull1);
    }

    private UserDTOFull applyPatchUser(JsonPatch jsonPatch, UserDTOFull userDTOFull) throws JsonProcessingException, JsonPatchException {
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(userDTOFull, JsonNode.class));
        return objectMapper.treeToValue(patched, UserDTOFull.class);
    }

    @Override
    public ResponseEntity<String> deleteMeUser(String login) {
        return userRepository.getUserByLogin(login)
                .map(delete -> {
                    userRepository.delete(delete);
                return new ResponseEntity<>("Пользователь удален", HttpStatus.OK);})
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не может быть удален"));
    }

    protected String getAuthenticationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getUserByLogin(authentication.getName()).orElseThrow().getLogin();
    }
}