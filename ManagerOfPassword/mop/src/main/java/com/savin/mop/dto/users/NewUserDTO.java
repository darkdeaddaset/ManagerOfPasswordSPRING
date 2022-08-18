package com.savin.mop.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewUserDTO {
    private String name;
    private String password;
    private long department_id;
}
