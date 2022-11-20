package kmve.afw.managerpassword.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTOFull {
    private String login;
    private String password;
    private long departmentId;
}
