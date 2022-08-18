package com.savin.mop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.savin.mop.dto.department.DepartmentDTO;
import com.savin.mop.dto.department.DepartmentDTOAndListOfUsers;
import com.savin.mop.mapper.DepartmentMapper;
import com.savin.mop.repository.DepartmentRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class DepartmentService {
    private final GeneratePassword generatePassword;
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper mapper;
    ObjectMapper objectMapper;

    public DepartmentDTOAndListOfUsers getDepartmentInfo(DepartmentDTO departmentDTO){
        return departmentRepository.getDepartmentByNameAndPassword(departmentDTO.getName(), departmentDTO.getPassword())
                .map(mapper::getFromDepartmentWithList)
                .orElseThrow(() -> new ResourceAccessException("Отдел не найден"));
    }

    public DepartmentDTOAndListOfUsers getDepartmentInfo(String name){
        return departmentRepository.getDepartmentByName(name)
                .map(mapper::getFromDepartmentWithList)
                .orElseThrow(() -> new EntityNotFoundException("Отдел не найден"));
    }

    public List<DepartmentDTO> getDepartmentsAll(){
        return departmentRepository.findAll()
                .stream()
                .map(mapper::getFromDepartment)
                .collect(Collectors.toList());
    }

    public ResponseEntity createNewDepartment(DepartmentDTO departmentDTO){
        return Optional.of(departmentDTO)
                .map(mapper::getFromDTO)
                .map(saveNewDepartment -> {
                    if (departmentDTO.getPassword() == null || departmentDTO.getPassword().isEmpty() || departmentDTO.getPassword().isBlank()){
                        saveNewDepartment.setPassword(generatePassword.generatePassword(30, true, true));
                    }
                    departmentRepository.save(saveNewDepartment);
                    return new ResponseEntity("Новый отдел сохранен", HttpStatus.OK);})
                .orElseThrow(() -> new ResourceAccessException("Прозошла ошибка, из-за сохранение невозможно"));
    }

    public DepartmentDTO fullUpdateDepartment(String nameDepartment, DepartmentDTO updateFull){
        return departmentRepository.getDepartmentByName(nameDepartment)
                .map(update -> {
                    if (updateFull.getPassword() == null || update.getPassword().isEmpty() || updateFull.getPassword().isBlank()){
                        update.setPassword(generatePassword.generatePassword(30, true, true));
                    }
                    mapper.updateFull(updateFull, update);
                    departmentRepository.save(update);
                    return mapper.getFromDepartment(update);})
                .orElseThrow(() -> new ResourceAccessException("Произошла ошибка, из-за которой изменение невозможно"));
    }

    public DepartmentDTO partialUpdate(String nameDepartment, JsonPatch patch){
        DepartmentDTO department = departmentRepository.getDepartmentByName(nameDepartment)
                .map(mapper::getFromDepartment)
                .orElseThrow();
        DepartmentDTO departmentDTO;
        try {
            departmentDTO = applyPatchDepartment(patch, department);
        } catch (JsonPatchException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return fullUpdateDepartment(nameDepartment, departmentDTO);
    }

    public ResponseEntity deleteDepartment(DepartmentDTO departmentDTO){
        return departmentRepository.getDepartmentByNameAndPassword(departmentDTO.getName(), departmentDTO.getPassword())
                .map(delete -> {departmentRepository.delete(delete);
                    return new ResponseEntity("Отдел " + departmentDTO.getName() + " удален", HttpStatus.OK);})
                .orElseThrow(() -> new ResourceAccessException("Произошла ошибка, из-за которой удаление невозможно"));
    }

    private DepartmentDTO applyPatchDepartment(JsonPatch patch, DepartmentDTO targetDepartment) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetDepartment, JsonNode.class));
        DepartmentDTO department = objectMapper.treeToValue(patched, DepartmentDTO.class);
        return department;
    }
}