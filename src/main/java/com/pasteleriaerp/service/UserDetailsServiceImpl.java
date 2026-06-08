package com.pasteleriaerp.service;
import com.pasteleriaerp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsuarioRepository repo;
    @Override
    public UserDetails loadUserByUsername(String u) throws UsernameNotFoundException {
        var usr = repo.findByUsername(u).orElseThrow(() -> new UsernameNotFoundException(u));
        return User.builder().username(usr.getUsername()).password(usr.getPassword())
            .roles(usr.getRol().name()).build();
    }
}
