package kmve.afw.managerpassword.mapper;

import kmve.afw.managerpassword.dto.user.UserDTO;
import kmve.afw.managerpassword.dto.user.UserDTOFull;
import kmve.afw.managerpassword.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO getFromModel(User user);
    User getFromDTO(UserDTO userDTO);

    void getFromModelForFull(UserDTOFull userDTOFull, @MappingTarget User user);
    User getFromDTOFull(UserDTOFull userDTOFull);
    UserDTOFull getFromModelForFull(User user);
}
