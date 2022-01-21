package com.example.TitanicProbability.controller;

import com.example.TitanicProbability.config.JwtTokenUtil;
import com.example.TitanicProbability.dtos.AuthenticationDTO;
import com.example.TitanicProbability.dtos.LoginDTO;
import com.example.TitanicProbability.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/login")
    public AuthenticationDTO login(@RequestBody LoginDTO loginDTO) {
        authenticate(loginDTO.getUsername(), loginDTO.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUsername());

        String token = jwtTokenUtil.generateToken(userDetails);

        return new AuthenticationDTO(token, userDetails.getUsername());
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
