package com.grocery.shop.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import static com.grocery.shop.security.JwtConstants.TOKEN_PREFIX;

@RequiredArgsConstructor
@Component
public class JwtTokenValidator {
    private final JwtTokenUtil jwtTokenUtil;

    protected final Log logger = LogFactory.getLog(getClass());

    public boolean validate(String token) {
        if (token == null) {
            logger.debug("couldn't find bearer string, will ignore the token");
            return false;
        }


        if (!token.startsWith(TOKEN_PREFIX)) {
            logger.debug("bad token");
            return false;
        }

        String tokenWithoutPrefix = token.replace(TOKEN_PREFIX, "");

        try {
            if (jwtTokenUtil.isTokenExpired(tokenWithoutPrefix)) {
                logger.debug("the token is expired and not valid anymore");
                return false;
            }
        } catch (IllegalArgumentException e) {
            logger.error("an error occured during getting email from token", e);
            return false;
        } catch (SignatureException e) {
            logger.debug("the token broken", e);
            return false;
        } catch (MalformedJwtException e) {
            logger.debug("MalformedJwtException", e);
            return false;
        } catch (ExpiredJwtException e) {
            logger.debug("JWT token is expired", e);
            return false;
        }


        return true;
    }
}
