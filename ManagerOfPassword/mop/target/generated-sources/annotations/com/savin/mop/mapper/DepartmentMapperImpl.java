package com.savin.mop.mapper;

import com.savin.mop.dto.department.DepartmentDTO;
import com.savin.mop.dto.department.DepartmentDTOAndListOfUsers;
import com.savin.mop.model.Department;
import com.savin.mop.model.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-18T16:46:51+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.16 (Ubuntu)"
)
@Component
public class DepartmentMapperImpl implements DepartmentMapper {

    @Override
    public Department getFromDTO(DepartmentDTO departmentDTO) {
        if ( departmentDTO == null ) {
            return null;
        }

        Department department = new Department();

        department.setName( departmentDTO.getName() );
        department.setPassword( departmentDTO.getPassword() );

        return department;
    }

    @Override
    public DepartmentDTO getFromDepartment(Department department) {
        if ( department == null ) {
            return null;
        }

        String name = null;
        String password = null;

        name = department.getName();
        password = department.getPassword();

        DepartmentDTO departmentDTO = new DepartmentDTO( name, password );

        return departmentDTO;
    }

    @Override
    public DepartmentDTOAndListOfUsers getFromDepartmentWithList(Department department) {
        if ( department == null ) {
            return null;
        }

        Set<User> users = null;
        String name = null;
        String password = null;

        Set<User> set = department.getUsers();
        if ( set != null ) {
            users = new LinkedHashSet<User>( set );
        }
        name = department.getName();
        password = department.getPassword();

        DepartmentDTOAndListOfUsers departmentDTOAndListOfUsers = new DepartmentDTOAndListOfUsers( name, password, users );

        return departmentDTOAndListOfUsers;
    }

    @Override
    public void updateFull(DepartmentDTO departmentDTO, Department department) {
        if ( departmentDTO == null ) {
            return;
        }

        department.setName( departmentDTO.getName() );
        department.setPassword( departmentDTO.getPassword() );
    }
}
