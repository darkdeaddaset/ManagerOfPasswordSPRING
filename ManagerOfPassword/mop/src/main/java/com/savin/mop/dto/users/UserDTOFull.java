package com.savin.mop.dto.users;

import com.savin.mop.model.Department;
import com.savin.mop.model.Record;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class UserDTOFull {
    private String name;
    private String password;
    private long department_id;
    private Set<Record> records;
}
