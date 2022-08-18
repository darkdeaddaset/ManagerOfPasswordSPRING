package com.savin.mop.jwt;

import com.savin.mop.model.JwtAuthentication;
import com.savin.mop.model.enums.Roles;
import io.jsonwebtoken.Claims;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtUtils {

    public static JwtAuthentication generate(Claims claims){
        JwtAuthentication jwtAuthentication = new JwtAuthentication();
        jwtAuthentication.setName(claims.getSubject());
        jwtAuthentication.setRoles(getRole(claims));
        return jwtAuthentication;
    }

    private static Set<Roles> getRole(Claims claims){
        List<String> role = claims.get("role", List.class);
        return role.stream()
                .map(Roles::valueOf)
                .collect(Collectors.toSet());
    }
}
