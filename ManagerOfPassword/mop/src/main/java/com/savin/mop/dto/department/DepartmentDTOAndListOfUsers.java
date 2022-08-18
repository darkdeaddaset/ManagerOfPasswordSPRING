package com.savin.mop.dto.department;

import com.savin.mop.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class DepartmentDTOAndListOfUsers {
    private String name;
    private String password;
    private Set<User> users;
}
