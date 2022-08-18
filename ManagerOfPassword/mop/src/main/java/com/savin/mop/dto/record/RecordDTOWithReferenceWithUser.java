package com.savin.mop.dto.record;

import com.savin.mop.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RecordDTOWithReferenceWithUser {
    private String name;
    private String password;
    private String url;
    private User user;
}
