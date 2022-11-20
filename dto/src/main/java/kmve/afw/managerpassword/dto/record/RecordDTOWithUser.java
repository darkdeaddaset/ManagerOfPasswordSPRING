package kmve.afw.managerpassword.dto.record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordDTOWithUser {
    private String name;
    private String password;
    private String url;
    private long user;
}
