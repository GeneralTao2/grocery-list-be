package com.grocery.shop.security;

import com.grocery.shop.dto.JwtUserData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.grocery.shop.security.JwtConstants.HEADER_STRING;


@AllArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    JwtTokenValidator jwtTokenValidator;
    JwtUserDataDecoder jwtUserDataDecoder;
    JwtUserDataValidator jwtUserDataValidator;
    UserDetailsAuthenticator userDetailsAuthenticator;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String token = req.getHeader(HEADER_STRING);
        JwtUserData jwtUserData = null;

        if (jwtTokenValidator.validate(token)) {
            jwtUserData = jwtUserDataDecoder.decode(req.getHeader(HEADER_STRING));
        }

        if (Objects.nonNull(jwtUserData) && jwtUserDataValidator.validate(jwtUserData)) {
            userDetailsAuthenticator.authenticate(jwtUserData, req);
        }

        chain.doFilter(req, res);
    }
}
