package com.grocery.shop.security;

public class JwtConstants {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 10 * 60 * 60;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
