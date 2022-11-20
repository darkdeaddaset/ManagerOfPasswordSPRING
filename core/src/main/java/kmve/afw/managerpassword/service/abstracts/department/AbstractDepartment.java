package kmve.afw.managerpassword.service.abstracts.department;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import kmve.afw.managerpassword.dto.department.DepartmentDTO;
import kmve.afw.managerpassword.dto.department.DepartmentDTOFull;
import kmve.afw.managerpassword.mapper.DepartmentMapper;
import kmve.afw.managerpassword.model.User;
import kmve.afw.managerpassword.repository.DepartmentRepository;
import kmve.afw.managerpassword.repository.UserRepository;
import kmve.afw.managerpassword.utils.GeneratePassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public abstract class AbstractDepartment implements DepartmentFunction{
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final GeneratePassword generatePassword;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    protected AbstractDepartment(DepartmentRepository departmentRepository,
                                 DepartmentMapper departmentMapper,
                                 GeneratePassword generatePassword,
                                 ObjectMapper objectMapper, UserRepository userRepository) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
        this.generatePassword = generatePassword;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<DepartmentDTOFull> getInfoAbstractDepartment(String name, User user) {
        return departmentRepository.getDepartmentByNameAndUserAdmin(name, user)
                .map(departmentMapper::getFromModelForFull)
                .map(get -> new ResponseEntity<>(get, HttpStatus.OK))
                .orElseThrow(() -> new EntityNotFoundException("Неудалось получить информацию об отделе"));
    }

    @Override
    public ResponseEntity<DepartmentDTOFull> createAbstractDepartment(DepartmentDTOFull departmentDTOFull) {
        return Optional.of(departmentDTOFull)
                .map(departmentMapper::getFromDTOFull)
                .map(create -> {
                    if (departmentDTOFull.getPassword().isBlank() || departmentDTOFull.getPassword().isEmpty()) {
                        departmentDTOFull.setPassword(generatePassword.generatePassword(32, true, true));
                    }
                    System.out.println(create.getUserAdmin());
                    departmentRepository.save(create);
                    return new ResponseEntity<>(departmentDTOFull, HttpStatus.OK);})
                .orElseThrow(() -> new EntityNotFoundException("Невозможно создать отдел"));
    }

    @Override
    public ResponseEntity<DepartmentDTOFull> updateAbstractDepartment(DepartmentDTOFull departmentDTOFull,
                                                                      String name,
                                                                      String login) {
        User user = generateUser(login);
        return departmentRepository.getDepartmentByNameAndUserAdmin(name, user)
                .map(updateFull -> {
                    if (departmentDTOFull.getPassword().isBlank() || departmentDTOFull.getPassword().isEmpty()) {
                        departmentDTOFull.setPassword(generatePassword.generatePassword(32, true, true));
                    }

                    departmentMapper.getFromModelForFull(departmentDTOFull, updateFull);
                    departmentRepository.save(updateFull);

                    return new ResponseEntity<>(departmentDTOFull, HttpStatus.OK);
                }).orElseThrow(() -> new EntityNotFoundException("Невозможно обновить данные отдела"));
    }

    @Override
    public ResponseEntity<DepartmentDTOFull> partialUpdateAbstractDepartment(String name, JsonPatch jsonPatch,
                                                                             String login) {
        User user = generateUser(login);
        DepartmentDTOFull departmentDTOFull = departmentRepository.getDepartmentByNameAndUserAdmin(name, user)
                .map(departmentMapper::getFromModelForFull)
                .orElseThrow(() -> new EntityNotFoundException("Невозможно обновить выборочно данные отдела"));
        DepartmentDTOFull departmentDTOFull1;

        try {
            departmentDTOFull1 = applyPatchDepartment(jsonPatch, departmentDTOFull);
        } catch (JsonPatchException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return updateAbstractDepartment(departmentDTOFull1, name, login);
    }

    public ResponseEntity<String> deleteAbstractDepartment(String name, User user) {
        return departmentRepository.getDepartmentByNameAndUserAdmin(name, user)
                .map(delete -> {
                    departmentRepository.delete(delete);
                return new ResponseEntity<>("Отдел удален", HttpStatus.OK);})
                .orElseThrow(() -> new EntityNotFoundException("Невозможно удалить отдел"));
    }

    private DepartmentDTOFull applyPatchDepartment(JsonPatch jsonPatch, DepartmentDTOFull departmentDTOFull) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(departmentDTOFull, JsonNode.class));
        return objectMapper.treeToValue(patched, DepartmentDTOFull.class);
    }

    protected DepartmentDTOFull generateDTOFull(String login, DepartmentDTO departmentDTO) {
        Long user_id = userRepository.getUserId(login);
        System.out.println("User_id: " + user_id);
        return new DepartmentDTOFull(departmentDTO.getName(), departmentDTO.getPassword(), user_id);
    }

    protected User generateUser(String login) {
        return userRepository.getUserByLogin(login).orElseThrow();
    }
}
