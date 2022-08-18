package com.savin.mop.dto.users;

import com.savin.mop.model.Record;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class UserDTOAndListOfRecords {
    private String name;
    private String password;
    private Set<Record> records;
}
