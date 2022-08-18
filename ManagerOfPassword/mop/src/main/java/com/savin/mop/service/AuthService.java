package com.savin.mop.service;

import com.savin.mop.jwt.JwtProvider;
import com.savin.mop.model.JwtAuthentication;
import com.savin.mop.model.User;
import com.savin.mop.request.JwtRequest;
import com.savin.mop.response.JwtResponse;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AuthService {
   private UserService userService;
   private JwtProvider jwtProvider;
   private Map<String, String> refreshStorage = new HashMap<>();

   public JwtResponse login(@NonNull JwtRequest request) throws AuthException {
      final User user = userService.getUserByName(request.getName())
              .orElseThrow(() -> new AuthException("Пользователь не найден"));
      if (user.getPassword().equals(request.getPassword())){
         String accessToken = jwtProvider.generateAccessToken(user);
         String refreshToken = jwtProvider.generateRefreshToken(user);
         refreshStorage.put(user.getName(), refreshToken);
         return new JwtResponse(accessToken, refreshToken);
      } else {
         throw new AuthException("Пароль неверен");
      }
   }

   public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
      if (jwtProvider.validateRefresh(refreshToken)){
         Claims claims = jwtProvider.getRefreshToken(refreshToken);
         String name = claims.getSubject();
         String saveRefreshToken = refreshStorage.get(name);
         if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)){
            User user = userService.getUserByName(name)
                    .orElseThrow(() -> new AuthException("Пользователь не найден"));
            String accessToken = jwtProvider.generateAccessToken(user);
            return new JwtResponse(accessToken, null);
         }
      }
      return new JwtResponse(null, null);
   }

   public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
      if (jwtProvider.validateRefresh(refreshToken)){
         Claims claims = jwtProvider.getRefreshToken(refreshToken);
         String name = claims.getSubject();
         String saveRefreshToken = refreshStorage.get(name);
         if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)){
            User user = userService.getUserByName(name)
                    .orElseThrow(() -> new AuthException("Пользователь не найден"));
            String accessToken = jwtProvider.generateAccessToken(user);
            String refreshNewToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getName(), refreshNewToken);
            return new JwtResponse(accessToken, refreshToken);
         }
      }
      throw new AuthException("Невалидный токен");
   }

   public JwtAuthentication getAuthInfo(){
      return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
   }
}
