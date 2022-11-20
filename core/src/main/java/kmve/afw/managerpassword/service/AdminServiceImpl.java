package kmve.afw.managerpassword.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kmve.afw.managerpassword.dto.record.RecordDTOWithUser;
import kmve.afw.managerpassword.dto.record.RecordInfoDTO;
import kmve.afw.managerpassword.dto.user.UserDTO;
import kmve.afw.managerpassword.dto.user.UserDTOFull;
import kmve.afw.managerpassword.mapper.RecordMapper;
import kmve.afw.managerpassword.mapper.UserMapper;
import kmve.afw.managerpassword.model.User;
import kmve.afw.managerpassword.model.enums.Role;
import kmve.afw.managerpassword.repository.DepartmentRepository;
import kmve.afw.managerpassword.repository.RecordRepository;
import kmve.afw.managerpassword.repository.UserRepository;
import kmve.afw.managerpassword.service.abstracts.user.AbstractAdmin;
import kmve.afw.managerpassword.utils.GeneratePassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

@Service
public class AdminServiceImpl extends AbstractAdmin implements AdminService {
    public AdminServiceImpl(UserRepository userRepository,
                            GeneratePassword generatePassword,
                            UserMapper userMapper,
                            ObjectMapper objectMapper,
                            DepartmentRepository departmentRepository,
                            RecordRepository recordRepository,
                            RecordMapper recordMapper) {
        super(userRepository, generatePassword, userMapper, objectMapper, departmentRepository, recordRepository, recordMapper);
    }

    @Override
    public ResponseEntity<String> setRoleForUser(String login, String role) {
        return userRepository.getUserByLogin(login)
                .map(setRole -> {
                    if (role.equals("ADMIN") || role.equals("admin")) {
                        setRole.setRoles(Collections.singleton(Role.ADMIN));
                    } else {
                        setRole.setRoles(Collections.singleton(Role.USER));
                    }
                    userRepository.save(setRole);
                    return new ResponseEntity<>("Роль для пользователя установлена", HttpStatus.OK);
                }).orElseThrow(() -> new EntityNotFoundException("Роль не может быть изменена"));
    }
    @Override
    public ResponseEntity<User> createUserForAdmin(UserDTO userDTO) {
        return createUser(userDTO);
    }

    @Override
    public List<UserDTO> getAllUserForAdmin() {
        return getAllUser();
    }

    @Override
    public List<UserDTOFull> getAllUserFullForAdmin() {
        return getAllUserFull();
    }

    @Override
    public List<RecordInfoDTO> getAllRecordsForAdmin() {
        return getAllRecordForAdmin();
    }

    @Override
    public List<RecordDTOWithUser> getAllRecordsFullForAdmin() {
        return getAllRecordFullForAdmin();
    }

    @Override
    public UserDTO getUserForAdmin(String login) {
        return getUser(login);
    }

    @Override
    public UserDTOFull getUserFullForAdmin(String login) {
        return getUserFull(login);
    }

    @Override
    public ResponseEntity<String> deleteDepartmentForAdmin(String name) {
        return deleteDepartment(name);
    }

    @Override
    public ResponseEntity<String> deleteUserForAdmin(String login) {
        return deleteUser(login);
    }

    @Override
    public ResponseEntity<String> deleteRecordForAdmin(String name) {
        return deleteRecord(name);
    }
}
