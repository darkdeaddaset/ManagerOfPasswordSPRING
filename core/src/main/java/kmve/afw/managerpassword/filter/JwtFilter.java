package kmve.afw.managerpassword.filter;

import io.jsonwebtoken.Claims;
import kmve.afw.managerpassword.jwt.JwtProvider;
import kmve.afw.managerpassword.jwt.JwtUtils;
import kmve.afw.managerpassword.model.JwtAuthentication;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private final String AUTHORIZATION = "Authorization";
    private final String BEARER = "Bearer ";
    private final JwtProvider jwtProvider;

    private String getTokenFromRequest(HttpServletRequest httpServletRequest) {
        String bearer = httpServletRequest.getHeader(AUTHORIZATION);

        if (StringUtils.hasText(bearer) && bearer.startsWith(BEARER)) {
            return bearer.substring(BEARER.length());
        }

        return null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) request);

        if (token != null && jwtProvider.validateAccessToken(token)) {
            Claims claims = jwtProvider.getAccessToken(token);
            JwtAuthentication jwtAuthentication = JwtUtils.generate(claims);
            jwtAuthentication.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
        }
        chain.doFilter(request, response);
    }
}
