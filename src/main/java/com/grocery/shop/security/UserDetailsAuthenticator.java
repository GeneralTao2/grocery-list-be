package com.grocery.shop.security;

import com.grocery.shop.dto.JwtUserData;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class UserDetailsAuthenticator {
    private final UserDetailsService userDetailsService;

    protected final Log logger = LogFactory.getLog(getClass());

    void authenticate(JwtUserData jwtUserData, HttpServletRequest req) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUserData.getEmail());

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

        logger.debug("authenticated user " + userDetails.getUsername() + ", setting security context");

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
