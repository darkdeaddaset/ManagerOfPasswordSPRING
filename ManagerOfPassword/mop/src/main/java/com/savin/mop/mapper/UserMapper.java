package com.savin.mop.mapper;

import com.savin.mop.dto.users.NewUserDTO;
import com.savin.mop.dto.users.UserDTO;
import com.savin.mop.dto.users.UserDTOAndListOfRecords;
import com.savin.mop.dto.users.UserDTOFull;
import com.savin.mop.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "department.id", source = "department_id")
    User createNewUser(NewUserDTO newUserDTO);

    User getFromDTO(UserDTO userDTO);
    UserDTO getFromUser(User user);

    UserDTOAndListOfRecords getFromUserAndList(User user);
    User getFromDTOUserANdList(UserDTOAndListOfRecords userDTOAndListOfRecords);
    @Mapping(target = "department_id", source = "department.id")
    UserDTOFull getFromUserFull(User user);
    @Mapping(target = "department.id", source = "department_id")
    User getFromDTOFull(UserDTOFull userDTOFull);
    @Mapping(target = "id", ignore = true)
    void updateFull(UserDTOFull userDTOFull, @MappingTarget User user);
}
