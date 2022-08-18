package com.savin.mop.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
}
