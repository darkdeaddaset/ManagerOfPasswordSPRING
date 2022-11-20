package kmve.afw.managerpassword.service.abstracts.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import kmve.afw.managerpassword.dto.record.RecordDTOWithUser;
import kmve.afw.managerpassword.dto.record.RecordInfoDTO;
import kmve.afw.managerpassword.dto.user.UserDTO;
import kmve.afw.managerpassword.dto.user.UserDTOFull;
import kmve.afw.managerpassword.mapper.RecordMapper;
import kmve.afw.managerpassword.mapper.UserMapper;
import kmve.afw.managerpassword.model.User;
import kmve.afw.managerpassword.repository.DepartmentRepository;
import kmve.afw.managerpassword.repository.RecordRepository;
import kmve.afw.managerpassword.repository.UserRepository;
import kmve.afw.managerpassword.utils.GeneratePassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public abstract class AbstractAdmin extends AbstractUser implements AdminFunction {
    private final DepartmentRepository departmentRepository;
    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;
    protected AbstractAdmin(UserRepository userRepository,
                            GeneratePassword generatePassword,
                            UserMapper userMapper,
                            ObjectMapper objectMapper,
                            DepartmentRepository departmentRepository,
                            RecordRepository recordRepository,
                            RecordMapper recordMapper) {
        super(userRepository, generatePassword, userMapper, objectMapper);
        this.departmentRepository = departmentRepository;
        this.recordRepository = recordRepository;
        this.recordMapper = recordMapper;
    }

    @Override
    public List<UserDTO> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::getFromModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTOFull> getAllUserFull() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::getFromModelForFull)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecordInfoDTO> getAllRecordForAdmin() {
        return recordRepository.findAll()
                .stream()
                .map(recordMapper::getFromModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecordDTOWithUser> getAllRecordFullForAdmin() {
        return recordRepository.findAll()
                .stream()
                .map(recordMapper::getFromModelForDTOFull)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<User> createUser(UserDTO userDTO) {
        return Optional.of(userDTO)
                .map(userMapper::getFromDTO)
                .map(createUser -> {
                    if (createUser.getPassword().isEmpty() || createUser.getPassword().isBlank()) {
                        createUser.setPassword(generatePassword.generatePassword(32, true, true));
                    }
                    userRepository.save(createUser);
                    return new ResponseEntity<>(createUser, HttpStatus.OK);})
                .orElseThrow(() -> new EntityNotFoundException("Невозможно создать пользователя"));
    }

    @Override
    public UserDTO getUser(String login) {
        return userRepository.getUserByLogin(login)
                .map(userMapper::getFromModel)
                .orElseThrow(() -> new EntityNotFoundException("Невозможно получить информацию о пользователе"));
    }

    @Override
    public UserDTOFull getUserFull(String login) {
        return userRepository.getUserByLogin(login)
                .map(userMapper::getFromModelForFull)
                .orElseThrow(() -> new EntityNotFoundException("Невозможно получить полную информацию о пользователе"));
    }

    @Override
    public ResponseEntity<String> deleteUser(String login) {
        return userRepository.getUserByLogin(login)
                .map(deleteUser -> {
                    recordRepository.deleteByUser(deleteUser);
                    userRepository.delete(deleteUser);
                    return new ResponseEntity<>("Пользователь удален", HttpStatus.OK);})
                .orElseThrow();
    }

    @Override
    public ResponseEntity<String> deleteDepartment(String name) {
        return departmentRepository.getDepartmentByName(name)
                .map(deleteDepartment -> {
                    departmentRepository.delete(deleteDepartment);
                    return new ResponseEntity<>("Отдел удален", HttpStatus.OK);
                }).orElseThrow(() -> new EntityNotFoundException("Отдел не может быть удален"));
    }

    @Override
    public ResponseEntity<String> deleteRecord(String name) {
        return recordRepository.getRecordByName(name)
                .map(deleteRecord -> {
                    recordRepository.delete(deleteRecord);
                    return new ResponseEntity<>("Запись удалена", HttpStatus.OK);
                }).orElseThrow(() -> new EntityNotFoundException("Запись невозможно удалить"));
    }
}