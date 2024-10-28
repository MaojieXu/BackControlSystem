package com.maojie.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

/**
 * @Description: Utility class for generating and parsing JWT tokens with embedded claims.
 */
public class JwtUtil {

    private static final String KEY = "itheima"; // Secret key used for signing the JWT

    /**
     * @Description: Generates a JWT token with the specified claims and an expiration time.
     * @Param: claims - Map containing business data to be embedded in the token
     * @return: String - Generated JWT token
     */
    public static String genToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12)) // 12-hour expiration
                .sign(Algorithm.HMAC256(KEY));
    }

    /**
     * @Description: Parses a JWT token, verifies its validity, and retrieves the embedded claims.
     * @Param: token - JWT token to parse and verify
     * @return: Map<String, Object> - Map containing the extracted business data from the token
     */
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }
}
