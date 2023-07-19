package com.example.demo5.service;

import com.example.demo5.domain.Cliente;
import com.example.demo5.repositories.ClienteRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteRepositoryJPA clienteRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente byEmail = clienteRepository.findByEmail(email);
        if (byEmail == null
        ) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        } else {
            System.out.println(byEmail.getEmail());
            return new User(byEmail.getEmail(), byEmail.getSenha(),
                    new ArrayList<>());
        }
    }
}
