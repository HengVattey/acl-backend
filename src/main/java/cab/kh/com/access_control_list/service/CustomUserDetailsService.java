package cab.kh.com.access_control_list.service;

// package com.example.acl.security


import cab.kh.com.access_control_list.model.User;
import cab.kh.com.access_control_list.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        var auths = u.getRoles().stream()
                .flatMap(r -> {
                    var set = r.getPermissions().stream()
                            .map(p -> new SimpleGrantedAuthority(p.getName())); // permission as authority
                    var role = new SimpleGrantedAuthority("ROLE_" + r.getName()); // role as authority
                    return java.util.stream.Stream.concat(java.util.stream.Stream.of(role), set);
                })
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), u.isEnabled(), true,true,true, auths);
    }
}

