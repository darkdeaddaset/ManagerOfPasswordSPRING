package com.savin.mop.mapper;

import com.savin.mop.dto.users.NewUserDTO;
import com.savin.mop.dto.users.UserDTO;
import com.savin.mop.dto.users.UserDTOAndListOfRecords;
import com.savin.mop.dto.users.UserDTOFull;
import com.savin.mop.model.Department;
import com.savin.mop.model.Record;
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
public class UserMapperImpl implements UserMapper {

    @Override
    public User createNewUser(NewUserDTO newUserDTO) {
        if ( newUserDTO == null ) {
            return null;
        }

        User user = new User();

        user.setDepartment( newUserDTOToDepartment( newUserDTO ) );
        user.setName( newUserDTO.getName() );
        user.setPassword( newUserDTO.getPassword() );

        return user;
    }

    @Override
    public User getFromDTO(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setName( userDTO.getName() );
        user.setPassword( userDTO.getPassword() );

        return user;
    }

    @Override
    public UserDTO getFromUser(User user) {
        if ( user == null ) {
            return null;
        }

        String name = null;
        String password = null;

        name = user.getName();
        password = user.getPassword();

        UserDTO userDTO = new UserDTO( name, password );

        return userDTO;
    }

    @Override
    public UserDTOAndListOfRecords getFromUserAndList(User user) {
        if ( user == null ) {
            return null;
        }

        Set<Record> records = null;
        String name = null;
        String password = null;

        Set<Record> set = user.getRecords();
        if ( set != null ) {
            records = new LinkedHashSet<Record>( set );
        }
        name = user.getName();
        password = user.getPassword();

        UserDTOAndListOfRecords userDTOAndListOfRecords = new UserDTOAndListOfRecords( name, password, records );

        return userDTOAndListOfRecords;
    }

    @Override
    public User getFromDTOUserANdList(UserDTOAndListOfRecords userDTOAndListOfRecords) {
        if ( userDTOAndListOfRecords == null ) {
            return null;
        }

        User user = new User();

        user.setName( userDTOAndListOfRecords.getName() );
        user.setPassword( userDTOAndListOfRecords.getPassword() );
        Set<Record> set = userDTOAndListOfRecords.getRecords();
        if ( set != null ) {
            user.setRecords( new LinkedHashSet<Record>( set ) );
        }

        return user;
    }

    @Override
    public UserDTOFull getFromUserFull(User user) {
        if ( user == null ) {
            return null;
        }

        long department_id = 0L;
        String name = null;
        String password = null;
        Set<Record> records = null;

        department_id = userDepartmentId( user );
        name = user.getName();
        password = user.getPassword();
        Set<Record> set = user.getRecords();
        if ( set != null ) {
            records = new LinkedHashSet<Record>( set );
        }

        UserDTOFull userDTOFull = new UserDTOFull( name, password, department_id, records );

        return userDTOFull;
    }

    @Override
    public User getFromDTOFull(UserDTOFull userDTOFull) {
        if ( userDTOFull == null ) {
            return null;
        }

        User user = new User();

        user.setDepartment( userDTOFullToDepartment( userDTOFull ) );
        user.setName( userDTOFull.getName() );
        user.setPassword( userDTOFull.getPassword() );
        Set<Record> set = userDTOFull.getRecords();
        if ( set != null ) {
            user.setRecords( new LinkedHashSet<Record>( set ) );
        }

        return user;
    }

    @Override
    public void updateFull(UserDTOFull userDTOFull, User user) {
        if ( userDTOFull == null ) {
            return;
        }

        user.setName( userDTOFull.getName() );
        user.setPassword( userDTOFull.getPassword() );
        if ( user.getRecords() != null ) {
            Set<Record> set = userDTOFull.getRecords();
            if ( set != null ) {
                user.getRecords().clear();
                user.getRecords().addAll( set );
            }
            else {
                user.setRecords( null );
            }
        }
        else {
            Set<Record> set = userDTOFull.getRecords();
            if ( set != null ) {
                user.setRecords( new LinkedHashSet<Record>( set ) );
            }
        }
    }

    protected Department newUserDTOToDepartment(NewUserDTO newUserDTO) {
        if ( newUserDTO == null ) {
            return null;
        }

        Department department = new Department();

        department.setId( newUserDTO.getDepartment_id() );

        return department;
    }

    private long userDepartmentId(User user) {
        if ( user == null ) {
            return 0L;
        }
        Department department = user.getDepartment();
        if ( department == null ) {
            return 0L;
        }
        long id = department.getId();
        return id;
    }

    protected Department userDTOFullToDepartment(UserDTOFull userDTOFull) {
        if ( userDTOFull == null ) {
            return null;
        }

        Department department = new Department();

        department.setId( userDTOFull.getDepartment_id() );

        return department;
    }
}
