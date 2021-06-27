package com.severo.jwt.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.severo.jwt.data.UserData;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticatorFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticatorFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            UserData credencials = new ObjectMapper()
                    .readValue(req.getInputStream(), UserData.class);
            return authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                credencials.getUserName(),
                                credencials.getPassword(),
                                new ArrayList<>())
            );
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                             HttpServletResponse res,
                                             FilterChain filterChain,
                                             Authentication auth) {
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstraints.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstraints.SECRET.getBytes()));

        res.addHeader(SecurityConstraints.HEADER_STRING, SecurityConstraints.TOKEN_PREFIX + token);
    }
}
