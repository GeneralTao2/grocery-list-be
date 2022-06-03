package com.grocery.shop.security;

import com.grocery.shop.dto.JwtUserData;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.grocery.shop.security.JwtConstants.TOKEN_PREFIX;

@RequiredArgsConstructor
@Component
public class JwtUserDataDecoder {
    private final JwtTokenUtil jwtTokenUtil;

    protected final Log logger = LogFactory.getLog(getClass());

    public JwtUserData decode(String rawToken) {
        String email = null;
        List<SimpleGrantedAuthority> roles = null;

        String authToken = rawToken.replace(TOKEN_PREFIX, "");
        try {
            email = jwtTokenUtil.getUsernameFromToken(authToken);
            roles = jwtTokenUtil.getRoleFromToken(authToken);
        } catch (IllegalArgumentException e) {
            logger.error("an error occured during getting email from token", e);
        } catch (SignatureException e) {
            logger.debug("the token broken", e);
        } catch (MalformedJwtException e) {
            logger.debug("MalformedJwtException", e);
        }

        return new JwtUserData(email, roles);
    }
}
