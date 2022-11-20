package kmve.afw.managerpassword.jwt;

import io.jsonwebtoken.Claims;
import kmve.afw.managerpassword.model.JwtAuthentication;
import kmve.afw.managerpassword.model.enums.Role;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtUtils {
    public static JwtAuthentication generate(Claims claims) {
        JwtAuthentication jwtAuthentication = new JwtAuthentication();
        jwtAuthentication.setName(claims.getSubject());
        jwtAuthentication.setRoles(getRole(claims));
        return jwtAuthentication;
    }

    private static Set<Role> getRole(Claims claims) {
        List<String> role = claims.get("roles", List.class);
        return role
                .stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }
}
