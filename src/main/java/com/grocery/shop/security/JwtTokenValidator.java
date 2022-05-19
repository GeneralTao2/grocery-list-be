package com.grocery.shop.security;

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

        if (jwtTokenUtil.isTokenExpired(tokenWithoutPrefix)) {
            logger.debug("the token is expired and not valid anymore");
            return false;
        }


        return true;
    }
}
