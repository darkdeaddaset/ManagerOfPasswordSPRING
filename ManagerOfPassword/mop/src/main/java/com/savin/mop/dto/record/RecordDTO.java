package com.savin.mop.dto.record;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class RecordDTO {
    private String name;
    private String password;
    private String url;
}
