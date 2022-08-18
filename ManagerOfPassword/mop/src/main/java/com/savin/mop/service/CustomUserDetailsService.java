package com.savin.mop.service;

import com.savin.mop.model.User;
import com.savin.mop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        return build(user);
    }

    public User loadUserById(Long id){
        return userRepository.getUserById(id).orElseThrow(null);
    }

    public static User build(User user){
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .collect(Collectors.toList());
        return new User(
                user.getId(),
                user.getName(),
                user.getPassword(),
                authorities);
    }
}