package kmve.afw.managerpassword.dto.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTOFull {
    private String name;
    private String password;
    private long userAdminId;
}
