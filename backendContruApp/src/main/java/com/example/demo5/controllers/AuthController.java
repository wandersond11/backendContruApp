package com.example.demo5.controllers;

import com.example.demo5.configuration.JwtTokenUtil;
import com.example.demo5.domain.Cliente;
import com.example.demo5.domain.JwtRequest;
import com.example.demo5.domain.JwtResponse;
import com.example.demo5.dtos.AuthenticationResponse;
import com.example.demo5.dtos.RegisterResponse;
import com.example.demo5.repositories.ClienteRepositoryJPA;
import com.example.demo5.service.AuthService;
import com.example.demo5.service.JwtUserDetailsService;
import com.example.demo5.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private ClienteRepositoryJPA clienteRepositoryJPA;
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        final UserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);
        System.out.println(token);

        return ResponseEntity.ok(new RegisterResponse(token,clienteRepositoryJPA.findByEmail(authenticationRequest.getEmail())));
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Cliente newCliente) {
        String register = authService.register(newCliente);
        return register == null ? ResponseUtil.createUnauthorizedResponse("Unauthorized") : ResponseEntity.ok(new RegisterResponse(register, newCliente));

    }

//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> login(@RequestParam("email") String email, @RequestParam("password") String password) {
//        AuthenticationResponse response = authService.authenticate(email, password);
//        if (response != null) {
//            return ResponseUtil.createOkResponse(response);
//        } else {
//            return ResponseUtil.createUnauthorizedResponse("Unauthorized");
//        }
//    }

}
