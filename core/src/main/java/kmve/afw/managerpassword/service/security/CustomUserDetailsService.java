package kmve.afw.managerpassword.service.security;

import kmve.afw.managerpassword.model.User;
import kmve.afw.managerpassword.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByLogin(username)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        return build(user);
    }

    private static User build(User user) {
        List<GrantedAuthority> grantedAuthorityList = user.getRoles()
                .stream()
                .collect(Collectors.toList());
        return new User(user.getId(),
                        user.getLogin(),
                        user.getPassword(),
                        grantedAuthorityList);
    }
}