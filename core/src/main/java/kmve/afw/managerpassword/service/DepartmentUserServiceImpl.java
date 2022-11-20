package kmve.afw.managerpassword.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import kmve.afw.managerpassword.dto.department.DepartmentDTO;
import kmve.afw.managerpassword.dto.department.DepartmentDTOFull;
import kmve.afw.managerpassword.mapper.DepartmentMapper;
import kmve.afw.managerpassword.model.User;
import kmve.afw.managerpassword.repository.DepartmentRepository;
import kmve.afw.managerpassword.repository.UserRepository;
import kmve.afw.managerpassword.service.abstracts.department.AbstractDepartment;
import kmve.afw.managerpassword.utils.GeneratePassword;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DepartmentUserServiceImpl extends AbstractDepartment implements DepartmentUserService{
    public DepartmentUserServiceImpl(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper, GeneratePassword generatePassword, ObjectMapper objectMapper, UserRepository userRepository) {
        super(departmentRepository, departmentMapper, generatePassword, objectMapper, userRepository);
    }

    @Override
    public ResponseEntity<DepartmentDTOFull> getInfoUser(String name, String login) {
        User user = generateUser(login);
        return getInfoAbstractDepartment(name, user);
    }

    @Override
    public ResponseEntity<DepartmentDTOFull> createDepartmentUser(DepartmentDTO departmentDTO, String login) {
        DepartmentDTOFull departmentDTOFull = generateDTOFull(login, departmentDTO);
        return createAbstractDepartment(departmentDTOFull);
    }

    @Override
    public ResponseEntity<DepartmentDTOFull> updateFullDepartmentUser(DepartmentDTOFull departmentDTOFull,
                                                                      String name,
                                                                      String login) {
        return updateAbstractDepartment(departmentDTOFull, name, login);
    }

    @Override
    public ResponseEntity<DepartmentDTOFull> partialUpdateDepartmentUser(String name, JsonPatch jsonPatch, String login) {
        return partialUpdateAbstractDepartment(name, jsonPatch, login);
    }

    @Override
    public ResponseEntity<String> deleteDepartmentUser(String name, String login) {
        User user = generateUser(login);
        return deleteAbstractDepartment(name, user);
    }
}
