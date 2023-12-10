package com.stackoverflow.controllers;

import com.stackoverflow.dtos.AuthenticationRequestDTO;
import com.stackoverflow.dtos.AuthenticationResponseDTO;
import com.stackoverflow.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/auth")
    public AuthenticationResponseDTO createAuthenticationToken(@RequestBody AuthenticationRequestDTO authenticationRequestDTO, HttpServletResponse response) throws IOException {
        try{
            //authenticate a user by verifying the provided email and password against the configured authentication manager.
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getEmail(), authenticationRequestDTO.getPassword()));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect Email or password");
        }catch (DisabledException disabledException){
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not created");
            return null;
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequestDTO.getEmail());

        final String jwt = jwtUtil.generateToken(userDetails);

        return new AuthenticationResponseDTO(jwt);
    }
}
