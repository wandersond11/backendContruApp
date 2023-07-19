package com.example.demo5.service;

import com.example.demo5.configuration.JwtTokenUtil;
import com.example.demo5.domain.Cliente;
import com.example.demo5.domain.JwtResponse;
import com.example.demo5.repositories.ClienteRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private ClienteRepositoryJPA clienteRepository;

    @Autowired
    private JwtTokenUtil jwtTokenProvider;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    public AuthenticationResponse  authenticate(String email, String password) {
//        Cliente cliente = clienteRepository.findByEmail(email);
//        if (cliente != null && passwordEncoder.matches(password, cliente.getSenha())) {
//            String token = jwtTokenProvider.generateToken(userDetailsService.loadUserByEmail(email));
//            return new AuthenticationResponse(token, cliente);
//        }
//        return null;
//    }

    public String register(Cliente newCliente) {
        Cliente cliente = clienteRepository.findByEmail(newCliente.getEmail());
        if (cliente != null) {
            return null;
        }

        newCliente.setSenha(passwordEncoder.encode(newCliente.getSenha()));
        clienteRepository.save(newCliente);
        String token = jwtTokenProvider.generateToken(userDetailsService.loadUserByUsername(newCliente.getEmail()));
        return token;
    }
}
