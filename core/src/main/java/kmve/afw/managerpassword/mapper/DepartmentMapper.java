package kmve.afw.managerpassword.mapper;

import kmve.afw.managerpassword.dto.department.DepartmentDTO;
import kmve.afw.managerpassword.dto.department.DepartmentDTOFull;
import kmve.afw.managerpassword.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    Department getFromDTO(DepartmentDTO departmentDTO);
    DepartmentDTO getFromModel(Department department);

    @Mapping(source = "userAdminId", target = "userAdmin.id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "password", target = "password")
    void getFromModelForFull(DepartmentDTOFull departmentDTOFull, @MappingTarget Department department);

    @Mapping(source = "userAdminId", target = "userAdmin.id")
    Department getFromDTOFull(DepartmentDTOFull departmentDTOFull);
    @Mapping(source = "userAdmin.id", target = "userAdminId")
    DepartmentDTOFull getFromModelForFull(Department department);
}
