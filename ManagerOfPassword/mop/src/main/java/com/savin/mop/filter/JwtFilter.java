package com.savin.mop.filter;

import com.savin.mop.jwt.JwtProvider;
import com.savin.mop.jwt.JwtUtils;
import com.savin.mop.model.JwtAuthentication;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
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
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class JwtFilter extends GenericFilterBean {
    private final String AUTHORIZATION = "Authorization";
    private final String BEARER = "Bearer ";
    private JwtProvider jwtProvider;

    private String getTokenFromRequest(HttpServletRequest httpServletRequest){
        String bearer = httpServletRequest.getHeader(AUTHORIZATION);

        if (StringUtils.hasText(bearer) && bearer.startsWith(BEARER)){
            return bearer.substring(BEARER.length());
        }
        return null;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);

        if (token != null && jwtProvider.validateAccess(token)){
            Claims claims = jwtProvider.getAccessToken(token);
            JwtAuthentication jwtAuthentication = JwtUtils.generate(claims);
            jwtAuthentication.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
