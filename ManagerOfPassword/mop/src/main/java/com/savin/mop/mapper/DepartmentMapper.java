package com.savin.mop.mapper;

import com.savin.mop.dto.department.DepartmentDTO;
import com.savin.mop.dto.department.DepartmentDTOAndListOfUsers;
import com.savin.mop.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    @Mapping(target = "id", ignore = true)
    Department getFromDTO(DepartmentDTO departmentDTO);
    DepartmentDTO getFromDepartment(Department department);
    DepartmentDTOAndListOfUsers getFromDepartmentWithList(Department department);
    void updateFull(DepartmentDTO departmentDTO, @MappingTarget Department department);
}
