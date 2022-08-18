package com.savin.mop.dto.users;

import com.savin.mop.model.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDTO {
    private String name;
    private String password;
}
