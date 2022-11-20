package kmve.afw.managerpassword.mapper;

import javax.annotation.processing.Generated;
import kmve.afw.managerpassword.dto.user.UserDTO;
import kmve.afw.managerpassword.dto.user.UserDTOFull;
import kmve.afw.managerpassword.model.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-16T21:27:55+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.16 (Ubuntu)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO getFromModel(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setLogin( user.getLogin() );
        userDTO.setPassword( user.getPassword() );

        return userDTO;
    }

    @Override
    public User getFromDTO(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setLogin( userDTO.getLogin() );
        user.setPassword( userDTO.getPassword() );

        return user;
    }

    @Override
    public void getFromModelForFull(UserDTOFull userDTOFull, User user) {
        if ( userDTOFull == null ) {
            return;
        }

        user.setLogin( userDTOFull.getLogin() );
        user.setPassword( userDTOFull.getPassword() );
    }

    @Override
    public User getFromDTOFull(UserDTOFull userDTOFull) {
        if ( userDTOFull == null ) {
            return null;
        }

        User user = new User();

        user.setLogin( userDTOFull.getLogin() );
        user.setPassword( userDTOFull.getPassword() );

        return user;
    }

    @Override
    public UserDTOFull getFromModelForFull(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTOFull userDTOFull = new UserDTOFull();

        userDTOFull.setLogin( user.getLogin() );
        userDTOFull.setPassword( user.getPassword() );

        return userDTOFull;
    }
}
