package kmve.afw.managerpassword.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import kmve.afw.managerpassword.dto.department.DepartmentDTO;
import kmve.afw.managerpassword.dto.department.DepartmentDTOFull;
import kmve.afw.managerpassword.mapper.DepartmentMapper;
import kmve.afw.managerpassword.repository.DepartmentRepository;
import kmve.afw.managerpassword.repository.UserRepository;
import kmve.afw.managerpassword.service.abstracts.department.AbstractDepartment;
import kmve.afw.managerpassword.utils.GeneratePassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class DepartmentAdminServiceImpl extends AbstractDepartment implements DepartmentAdminService {
    private final DepartmentUserService departmentUserService;
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentAdminServiceImpl(DepartmentRepository departmentRepository,
                                      DepartmentMapper departmentMapper,
                                      GeneratePassword generatePassword,
                                      ObjectMapper objectMapper,
                                      UserRepository userRepository,
                                      DepartmentUserService departmentUserService) {
        super(departmentRepository, departmentMapper, generatePassword, objectMapper, userRepository);
        this.departmentUserService = departmentUserService;
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }


    @Override
    public ResponseEntity<DepartmentDTO> getInfoDepartment(String name) {
        return departmentRepository.getDepartmentByName(name)
                .map(departmentMapper::getFromModel)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Отдел с таким именем не найден"));
    }

    @Override
    public ResponseEntity<DepartmentDTOFull> getInfoFullDepartment(String name) {
        return departmentRepository.getDepartmentByName(name)
                .map(departmentMapper::getFromModelForFull)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Отдел с таким именем не найден"));
    }

    @Override
    public ResponseEntity<String> deleteDepartment(String name) {
        return departmentRepository.getDepartmentByName(name)
                .map(delete -> {departmentRepository.delete(delete);
                return new ResponseEntity<>("Отдел удален", HttpStatus.OK);})
                .orElseThrow(() -> new EntityNotFoundException("Невозможно найти отдел для удаления"));
    }

    @Override
    public ResponseEntity<DepartmentDTOFull> createDepartment(DepartmentDTO departmentDTO, String login) {
        return departmentUserService.createDepartmentUser(departmentDTO, login);
    }

    @Override
    public ResponseEntity<DepartmentDTOFull> updateFullDepartment(DepartmentDTOFull departmentDTOFull, String name, String login) {
        return departmentUserService.updateFullDepartmentUser(departmentDTOFull, name, login);
    }

    @Override
    public ResponseEntity<DepartmentDTOFull> partialUpdateDepartment(String name, JsonPatch jsonPatch, String login) {
        return departmentUserService.partialUpdateDepartmentUser(name, jsonPatch, login);
    }

}