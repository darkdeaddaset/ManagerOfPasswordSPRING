package kmve.afw.managerpassword.mapper;

import javax.annotation.processing.Generated;
import kmve.afw.managerpassword.dto.department.DepartmentDTO;
import kmve.afw.managerpassword.dto.department.DepartmentDTOFull;
import kmve.afw.managerpassword.model.Department;
import kmve.afw.managerpassword.model.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-16T21:27:55+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.16 (Ubuntu)"
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
    public DepartmentDTO getFromModel(Department department) {
        if ( department == null ) {
            return null;
        }

        DepartmentDTO departmentDTO = new DepartmentDTO();

        departmentDTO.setName( department.getName() );
        departmentDTO.setPassword( department.getPassword() );

        return departmentDTO;
    }

    @Override
    public void getFromModelForFull(DepartmentDTOFull departmentDTOFull, Department department) {
        if ( departmentDTOFull == null ) {
            return;
        }

        if ( department.getUserAdmin() == null ) {
            department.setUserAdmin( new User() );
        }
        departmentDTOFullToUser( departmentDTOFull, department.getUserAdmin() );
        department.setName( departmentDTOFull.getName() );
        department.setPassword( departmentDTOFull.getPassword() );
    }

    @Override
    public Department getFromDTOFull(DepartmentDTOFull departmentDTOFull) {
        if ( departmentDTOFull == null ) {
            return null;
        }

        Department department = new Department();

        department.setUserAdmin( departmentDTOFullToUser1( departmentDTOFull ) );
        department.setName( departmentDTOFull.getName() );
        department.setPassword( departmentDTOFull.getPassword() );

        return department;
    }

    @Override
    public DepartmentDTOFull getFromModelForFull(Department department) {
        if ( department == null ) {
            return null;
        }

        DepartmentDTOFull departmentDTOFull = new DepartmentDTOFull();

        departmentDTOFull.setUserAdminId( departmentUserAdminId( department ) );
        departmentDTOFull.setName( department.getName() );
        departmentDTOFull.setPassword( department.getPassword() );

        return departmentDTOFull;
    }

    protected void departmentDTOFullToUser(DepartmentDTOFull departmentDTOFull, User mappingTarget) {
        if ( departmentDTOFull == null ) {
            return;
        }

        mappingTarget.setId( departmentDTOFull.getUserAdminId() );
    }

    protected User departmentDTOFullToUser1(DepartmentDTOFull departmentDTOFull) {
        if ( departmentDTOFull == null ) {
            return null;
        }

        User user = new User();

        user.setId( departmentDTOFull.getUserAdminId() );

        return user;
    }

    private long departmentUserAdminId(Department department) {
        if ( department == null ) {
            return 0L;
        }
        User userAdmin = department.getUserAdmin();
        if ( userAdmin == null ) {
            return 0L;
        }
        long id = userAdmin.getId();
        return id;
    }
}
